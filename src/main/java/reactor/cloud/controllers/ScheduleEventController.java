package reactor.cloud.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import reactor.cloud.entities.ScheduleEvent;
import reactor.cloud.feign_clients.DeviceClient;
import reactor.cloud.repositories.ScheduleEventRepository;
import reactor.models.Group;
import reactor.models.User;
import reactor.repositories.GroupRepository;

import java.util.*;

@RestController
@RequestMapping("/api/cloud/schedule")
public class ScheduleEventController {

    private final ScheduleEventRepository scheduleEventRepository;
    private final DeviceClient deviceClient;

    @Autowired
    public ScheduleEventController(ScheduleEventRepository scheduleEventRepository, DeviceClient deviceClient) {
        this.scheduleEventRepository = scheduleEventRepository;
        this.deviceClient = deviceClient;
    }

    @PostMapping("/new")
    ResponseEntity create(@AuthenticationPrincipal User user, @RequestBody ScheduleEvent scheduleEvent){

        Map<String, Object> device = deviceClient.getDevice(scheduleEvent.getDeviceId());
        scheduleEvent.setDeviceName((String)device.get("name"));

        scheduleEventRepository.save(scheduleEvent);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/group/{id}")
    HashMap<String,List> getByGroup(@AuthenticationPrincipal User user, @PathVariable Integer id){
        Optional<List<ScheduleEvent>> optional = scheduleEventRepository.findByGroupId(id);
        HashMap<String, List> map = new HashMap<>();
        map.put("events",optional.orElse(null));
        return map;
    }

    @GetMapping("/device/{id}")
    HashMap<String, List> getByDevice(@AuthenticationPrincipal User user, @PathVariable String id){
        Optional<List<ScheduleEvent>> optional = scheduleEventRepository.findByDeviceId(id);
        HashMap<String, List> map = new HashMap<>();
        map.put("events",optional.orElse(null));
        return map;
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity delete(@PathVariable Integer id){
        ScheduleEvent scheduleEvent = scheduleEventRepository.findOne(id);
        if (scheduleEvent != null) scheduleEventRepository.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
