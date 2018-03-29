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
import reactor.repositories.EventRepository;
import reactor.repositories.GroupRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("api/events/{id}")
    @PreAuthorize("isGroupMember(#groupId)")
    public List<Event> index(@AuthenticationPrincipal User user, @PathVariable("id") Integer groupId){
        Event event = new Event(LocalDateTime.now(), "32422ajgrngadgsnflngs;ladadgsnl");
        eventRepository.save(event);
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ModelNotFoundException("group"));
        group.getEvents().add(event);
        return group.getEvents();
    }

    @PostMapping("service/events/{id}/{device}")
    public ResponseEntity<?> create(@PathVariable("id") Integer groupId, @PathVariable("device") String device){
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ModelNotFoundException("group"));
        Event event = new Event(LocalDateTime.now(), device);
        group.getEvents().add(event);
        groupRepository.save(group);

        return new ResponseEntity(HttpStatus.CREATED);
    }

}
