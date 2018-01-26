package reactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.models.NotificationId;
import reactor.models.User;
import reactor.repositories.AccountRepository;

@RestController
public class NotificationController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping(path = "api/notifications/enroll")
    public ResponseEntity<?> enroll(@AuthenticationPrincipal User user, @RequestBody NotificationId notificationId){
        user.account.getNotificationIdList().add(notificationId);
        accountRepository.save(user.account);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
