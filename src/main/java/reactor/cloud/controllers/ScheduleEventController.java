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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedule")
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
    List<ScheduleEvent> getByGroup(@PathVariable Integer id){
        Optional<List<ScheduleEvent>> optional = scheduleEventRepository.findByGroupId(id);
        return optional.orElse(null);
    }

    @GetMapping("/device/{id}")
    List<ScheduleEvent> getByDevice(@PathVariable Integer id){
        Optional<List<ScheduleEvent>> optional = scheduleEventRepository.findByDeviceId(id);
        return optional.orElse(null);
    }
}
