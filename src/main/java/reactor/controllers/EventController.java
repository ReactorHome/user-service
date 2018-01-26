package reactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.exceptions.ModelNotFoundException;
import reactor.models.Event;
import reactor.models.Group;
import reactor.models.User;
import reactor.repositories.GroupRepository;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("api/events/{id}")
    @PreAuthorize("isGroupMember(#groupId)")
    public List<Event> index(@AuthenticationPrincipal User user, @PathVariable("id") Integer groupId){
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ModelNotFoundException("group"));
        return group.getEvents();
    }

    @PostMapping("service/events/{id}")
    public ResponseEntity<?> create(@PathVariable("id") Integer groupId, @RequestBody Event event){
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ModelNotFoundException("group"));
        group.getEvents().add(event);
        groupRepository.save(group);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
