package reactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.exceptions.ModelNotFoundException;
import reactor.models.Face;
import reactor.models.Group;
import reactor.repositories.GroupRepository;

@RestController
public class FaceController {

    @Autowired
    private GroupRepository groupRepository;

    @PostMapping(path = "service/faces/{id}")
    public ResponseEntity<?> create(@PathVariable("id") Integer groupId, @RequestBody Face face){
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ModelNotFoundException("group"));
        group.getFaceList().add(face);
        groupRepository.save(group);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
