package reactor.cloud.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.cloud.entities.ScheduleEvent;
import reactor.cloud.repositories.ScheduleEventRepository;
import reactor.models.Group;
import reactor.repositories.GroupRepository;

import java.util.*;

@RestController
@RequestMapping("/api/cloud/schedule")
public class ScheduleEventController {

    private final ScheduleEventRepository scheduleEventRepository;

    @Autowired
    public ScheduleEventController(ScheduleEventRepository scheduleEventRepository) {
        this.scheduleEventRepository = scheduleEventRepository;
    }

    @PostMapping("/new")
    ResponseEntity create(@RequestBody ScheduleEvent scheduleEvent){
        scheduleEventRepository.save(scheduleEvent);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/group/{id}")
    HashMap<String,List> getByGroup(@PathVariable Integer id){
        Optional<List<ScheduleEvent>> optional = scheduleEventRepository.findByGroupId(id);
        HashMap<String, List> map = new HashMap<>();
        map.put("events",optional.orElse(null));
        return map;
    }

    @GetMapping("/device/{id}")
    HashMap<String, List> getByDevice(@PathVariable String id){
        Optional<List<ScheduleEvent>> optional = scheduleEventRepository.findByDeviceId(id);
        HashMap<String, List> map = new HashMap<>();
        map.put("events",optional.orElse(null));
        return map;
    }
}
