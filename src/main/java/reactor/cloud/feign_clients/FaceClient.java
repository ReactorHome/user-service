package reactor.cloud.feign_clients;

import feign.Headers;
import feign.Param;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import feign.codec.Encoder;

import java.io.File;

@FeignClient(value = "face", url = "http://0.0.0.0:5000")
public interface FaceClient {

    @PostMapping(path = "upload")
    @Headers("Content-Type: image/jpeg")
    ResponseEntity upload(/*@RequestHeader("Authorization") String token, */@Param("image") File image);


}

