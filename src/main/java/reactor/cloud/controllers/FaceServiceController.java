package reactor.cloud.controllers;

import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.cloud.FaceUtility;
import reactor.cloud.feign_clients.FaceClient;
import reactor.cloud.repositories.FaceRepository;
import reactor.models.Alert;
import reactor.models.Face;
import reactor.models.Group;
import reactor.repositories.AlertRepository;
import reactor.repositories.GroupRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/cloud/face")
public class FaceServiceController {

    private final FaceClient faceClient;
    private final FaceRepository faceRepository;
    private final GroupRepository groupRepository;
    private final AlertRepository alertRepository;


    @Autowired
    public FaceServiceController(FaceClient faceClient, FaceRepository faceRepository, GroupRepository groupRepository, AlertRepository alertRepository) {
        this.faceClient = faceClient;
        this.faceRepository = faceRepository;
        this.groupRepository = groupRepository;
        this.alertRepository = alertRepository;
    }

    @PostMapping("/classify")
    @Headers("Content-Type: multipart/form-data")
    ResponseEntity classify(@RequestParam("image") MultipartFile image) throws IOException {
        int groupNum = 1;
        Optional<Group> groupOptional = groupRepository.findById(groupNum);
        if(!groupOptional.isPresent()) {
            return new ResponseEntity(HttpStatus.PAYLOAD_TOO_LARGE);
        }

        Group group = groupOptional.get();

        String fileName = UUID.randomUUID().toString() + image.getOriginalFilename();

        Files.write(Paths.get("faces/" + fileName), image.getBytes());

        try {
            String raw = faceClient.upload(image);
            double[] faceData = FaceUtility.rawToArray(raw);

            String data = "";

            Face matchFace = FaceUtility.findSimilar(faceData, group.getFaceList());
            boolean isSafe = FaceUtility.isSafe(raw, group.getFaceList());
            if (matchFace != null) {
                if (matchFace.isSafe()) data = "ALERT: ";
                else data = "WARNING: ";
                data += matchFace.getName() + " likely detected!";
            } else if (isSafe) {
                data = "ALERT: Safe visitor detected.";
            } else {
                data = "WARNING: Unsafe visitor detected.";
            }


            Alert alert = new Alert(0, data, raw, fileName, System.currentTimeMillis(), null);
            group.getAlerts().add(alert);
            groupRepository.save(group);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            String data = "WARNING: A visitor was detected but an error occurred";
            Alert alert = new Alert(0, data, null, null, 0, null);
            group.getAlerts().add(alert);
            groupRepository.save(group);

            return new ResponseEntity(HttpStatus.PAYLOAD_TOO_LARGE);
        }
    }

    @PostMapping("/save")
    ResponseEntity save(@RequestBody Face face) {
        int groupNum = 1;
        Optional<Group> groupOptional = groupRepository.findById(groupNum);
        if(!groupOptional.isPresent()) {
            return new ResponseEntity(HttpStatus.PAYLOAD_TOO_LARGE);
        }

        Group group = groupOptional.get();

        group.getFaceList().add(face);
        groupRepository.save(group);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/image/{filename:.+}")
    FileSystemResource image(@PathVariable String filename) throws IOException {
        File image = new File("faces/" + filename);

        return new FileSystemResource(image);
    }
}
