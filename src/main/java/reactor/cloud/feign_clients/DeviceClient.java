package reactor.cloud.feign_clients;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "device", url = "localhost:9080/device")
public interface DeviceClient {

    @GetMapping(path = "api/{id}/device/")
    ObjectNode getDevice(@RequestHeader("Authorization") String token, @RequestParam("id") String deviceId);

    @PatchMapping(path = "api/{id}/light/{lightId}")
    ResponseEntity updateLight(/*@RequestHeader("Authorization") String token, */@RequestParam("id") Integer id, @RequestParam("lightId") String lightId);

    @PatchMapping(path = "api/{id}/outlet/{outletId}")
    ResponseEntity updateOutlet(/*@RequestHeader("Authorization") String token, */@RequestParam("id") Integer id, @RequestParam("outletId") String outletId);

    @PatchMapping(path = "api/{id}/thermostat/{thermostatId}")
    ResponseEntity updateThermostat(/*@RequestHeader("Authorization") String token, */@RequestParam("id") Integer id, @RequestParam("thermostatId") String thermostatId);
}