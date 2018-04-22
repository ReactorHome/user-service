package reactor.services;

import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.TokenUtil;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import com.turo.pushy.apns.util.concurrent.PushNotificationResponseListener;
import io.netty.util.concurrent.Future;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import reactor.models.Account;
import reactor.models.NotificationId;
import reactor.repositories.AccountRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class NotificationService implements DisposableBean {

    private ApnsClient client;
    final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();

    AccountRepository accountRepository;
    ResourceLoader resourceLoader;

    @Autowired
    public NotificationService(AccountRepository accountRepository, ResourceLoader resourceLoader) {
        this.accountRepository = accountRepository;
        this.resourceLoader = resourceLoader;
        Resource cert = resourceLoader.getResource("classpath:ReactorHomeAPNSDev.p12");
        try {
            this.client = new ApnsClientBuilder()
                    .setApnsServer(ApnsClientBuilder.DEVELOPMENT_APNS_HOST)
                    .setClientCredentials(cert.getFile(), "")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendNotification(String token, String message){
        payloadBuilder.setAlertBody(message);

        final String payload = payloadBuilder.buildWithDefaultMaximumLength();
        final String sanitizedToken = TokenUtil.sanitizeTokenString(token);

        SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(sanitizedToken, "com.MyReactorHome", payload);
        final PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>>
                sendNotificationFuture = client.sendNotification(pushNotification);

        sendNotificationFuture.addListener((PushNotificationResponseListener<SimpleApnsPushNotification>) future -> {
            // When using a listener, callers should check for a failure to send a
            // notification by checking whether the future itself was successful
            // since an exception will not be thrown.
            if (future.isSuccess()) {
                final PushNotificationResponse<SimpleApnsPushNotification> pushNotificationResponse =
                        future.getNow();
                String sentToken = pushNotificationResponse.getPushNotification().getToken();
                if(pushNotificationResponse.isAccepted()){
                    System.out.println("Notification sent to " + sentToken);
                }else{
                    System.out.println("Notification failed to send to " + sentToken + " for " + pushNotificationResponse.getRejectionReason());
                    Optional<Account> accountOptioanl = accountRepository.findByNotificationIdListContains(sentToken);
                    if(accountOptioanl.isPresent()){
                        Account account = accountOptioanl.get();
                        List<NotificationId> ids = account.getNotificationIdList()
                                .stream()
                                .filter((notification) -> notification.getNotificationAddress().equals(sentToken))
                                .collect(Collectors.toList());
                        account.getNotificationIdList().removeAll(ids);
                        accountRepository.save(account);
                    }
                }
                // Handle the push notification response as before from here.
            } else {
                // Something went wrong when trying to send the notification to the
                // APNs gateway. We can find the exception that caused the failure
                // by getting future.cause().
                future.cause().printStackTrace();
            }
        });
    }


    @Override
    public void destroy() throws Exception {
        final Future<Void> closeFuture = client.close();
        closeFuture.await();
    }
}
