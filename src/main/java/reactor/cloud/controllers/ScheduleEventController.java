package reactor.cloud.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    ResponseEntity create(@AuthenticationPrincipal User user, @RequestBody ScheduleEvent scheduleEvent, OAuth2Authentication auth){
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        String token = details.getTokenValue();
        token = "Bearer " + token;

        ObjectNode device = deviceClient.getDevice(token, scheduleEvent.getDeviceId());
        scheduleEvent.setDeviceName(device.get("name").asText());

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
