package reactor.cloud.controllers;

import com.sun.org.apache.xpath.internal.operations.Mult;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.cloud.feign_clients.FaceClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/cloud/face")
public class FaceServiceController {

    private final FaceClient faceClient;

    @Autowired
    public FaceServiceController(FaceClient faceClient) {
        this.faceClient = faceClient;
    }

    @PostMapping("/test")
    @Headers("Content-Type: multipart/form-data")
    String create(@RequestParam("image") MultipartFile image) throws IOException {
        System.out.println("\n\nREEEEEEEEEEEEEEEEEEEEEEEEEE\n\n");

        String fileName = UUID.randomUUID().toString() + image.getOriginalFilename();

        Files.write(Paths.get("faces/" + fileName), image.getBytes());

        File file = new File("faces/" + fileName);

        return faceClient.upload(image);
    }

}
