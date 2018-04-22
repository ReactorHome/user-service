package reactor.cloud.feign_clients;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "device", url = "localhost:8080")
public interface DeviceClient {

    @GetMapping(path = "service/{id}/device/")
    Map<String, Object> getDevice(@PathVariable("id") String deviceId);

    @PutMapping(path = "service/{id}/light/{lightId}")
    ResponseEntity updateLight(@PathVariable("id") Integer id, @PathVariable("lightId") String lightId, @RequestBody Map<String, Object> params);

    @PutMapping(path = "service/{id}/outlet/{outletId}")
    ResponseEntity updateOutlet(@PathVariable("id") Integer id, @PathVariable("outletId") String outletId, @RequestBody Map<String, Object> params);

    @PutMapping(path = "service/{id}/thermostat/{thermostatId}")
    ResponseEntity updateThermostat(@PathVariable("id") Integer id, @PathVariable("thermostatId") String thermostatId, @RequestBody Map<String, Object> params);
}