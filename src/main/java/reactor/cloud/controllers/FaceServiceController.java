package reactor.cloud.controllers;

import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.cloud.FaceUtility;
import reactor.cloud.feign_clients.FaceClient;
import reactor.cloud.repositories.FaceRepository;
import reactor.models.Face;
import reactor.repositories.GroupRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api/cloud/face")
public class FaceServiceController {

    private final FaceClient faceClient;
    private final FaceRepository faceRepository;
    private final GroupRepository groupRepository;


    @Autowired
    public FaceServiceController(FaceClient faceClient, FaceRepository faceRepository, GroupRepository groupRepository) {
        this.faceClient = faceClient;
        this.faceRepository = faceRepository;
        this.groupRepository = groupRepository;
    }

    @PostMapping("/classify")
    @Headers("Content-Type: multipart/form-data")
    HashMap classify(@RequestParam("image") MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID().toString() + image.getOriginalFilename();

        Files.write(Paths.get("faces/" + fileName), image.getBytes());

        String raw = faceClient.upload(image);
        double[] faceData = FaceUtility.rawToArray(raw);

        HashMap<String, String> response = new HashMap<>();
        response.put("data", raw);
        response.put("safe", Boolean.toString(FaceUtility.isSafe(raw, faceRepository.findAll())));

        return response;
    }

    @PostMapping("/save")
    ResponseEntity save(@RequestBody Face face) {
        faceRepository.save(face);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/count")
    ResponseEntity count() {
        return new ResponseEntity(faceRepository.findAll().size(), HttpStatus.OK);
    }

    @PostMapping("/delete")
    ResponseEntity delete() {
        faceRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }
}
