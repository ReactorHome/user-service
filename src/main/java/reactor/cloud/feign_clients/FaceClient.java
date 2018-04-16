package reactor.cloud.feign_clients;

import feign.Body;
import feign.Headers;
import feign.Logger;
import feign.Param;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import feign.codec.Encoder;

import java.io.File;

@FeignClient(value = "face", url = "https://api.myreactorhome.com/face", configuration = MultipartSupportConfig.class)
public interface FaceClient {

    @PostMapping(value = "upload", consumes = "multipart/form-data")
    @Headers("Content-Type: multipart/form-data")
    String upload(/*@RequestHeader("Authorization") String token, */@Param("image") MultipartFile image);

    @Configuration
    public class FaceConfiguration {
        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }
    }
}

