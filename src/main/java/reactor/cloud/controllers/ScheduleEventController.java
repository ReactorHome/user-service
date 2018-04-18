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
    private final GroupRepository groupRepository;

    @Autowired
    public ScheduleEventController(ScheduleEventRepository scheduleEventRepository, GroupRepository groupRepository) {
        this.scheduleEventRepository = scheduleEventRepository;
        this.groupRepository = groupRepository;
    }

    @GetMapping("/now")
    ResponseEntity getNow(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        System.out.println("day:\t" + dayOfWeek);
        System.out.println("hour:\t" + hour);
        System.out.println("min:\t" + minute);
        System.out.println("sec:\t" + second);

        return new ResponseEntity(HttpStatus.OK);
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
