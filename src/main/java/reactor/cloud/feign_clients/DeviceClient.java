package reactor.cloud.feign_clients;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "device", url = "https://api.myreactorhome.com/devices")
public interface DeviceClient {

    @GetMapping(path = "api/hub/device/{id}")
    ObjectNode getDevice(/*@RequestHeader("Authorization") String token, */@RequestParam("id") String deviceId);

    @PatchMapping(path = "api/{id}/light/{lightId}")
    void updateLight(/*@RequestHeader("Authorization") String token, */@RequestParam("id") Integer id, @RequestParam("lightId") String lightId);

    @PatchMapping(path = "api/{id}/outlet/{outletId}")
    void updateOutlet(/*@RequestHeader("Authorization") String token, */@RequestParam("id") Integer id, @RequestParam("outletId") String outletId);

    @PatchMapping(path = "api/{id}/thermostat/{thermostatId}")
    void updateThermostat(/*@RequestHeader("Authorization") String token, */@RequestParam("id") Integer id, @RequestParam("thermostatId") String thermostatId);
}