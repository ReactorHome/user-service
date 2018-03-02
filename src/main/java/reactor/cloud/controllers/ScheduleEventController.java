package reactor.cloud.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.cloud.entities.ScheduleEvent;
import reactor.cloud.repositories.ScheduleEventRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedule")
public class ScheduleEventController {

    private final ScheduleEventRepository scheduleEventRepository;

    @Autowired
    public ScheduleEventController(ScheduleEventRepository scheduleEventRepository) {
        this.scheduleEventRepository = scheduleEventRepository;
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
    ResponseEntity createScheduleEvent(@RequestBody ScheduleEvent scheduleEvent){
        scheduleEventRepository.save(scheduleEvent);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get/group/{id}")
    ResponseEntity getByGroup(@PathVariable Integer id){
        Optional<List<ScheduleEvent>> optional = null;


        if(optional.isPresent()){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return new ResponseEntity<>(objectMapper.writeValueAsString(optional.get()), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Failed to convert result to JSON", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get/device/{id}")
    ResponseEntity getByDevice(@PathVariable Integer id){
        Optional<List<ScheduleEvent>> optional = scheduleEventRepository.findScheduleEventsByDeviceId(id);

        if(optional.isPresent()){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return new ResponseEntity<>(objectMapper.writeValueAsString(optional.get()), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Failed to convert result to JSON", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get/second/after/{second}")
    ResponseEntity getAfterMinute(@PathVariable Integer second){
        Optional<List<ScheduleEvent>> optional = scheduleEventRepository.findScheduleEventsByMinuteAfter(second);

        if(optional.isPresent()){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return new ResponseEntity<>(objectMapper.writeValueAsString(optional.get()), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Failed to convert result to JSON", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
