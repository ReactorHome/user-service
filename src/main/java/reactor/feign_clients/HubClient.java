package reactor.feign_clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "hub", url = "${reactor.device-service}")
public interface HubClient {

    @RequestMapping(method = RequestMethod.POST, path = "/api/hub")
    String registerHub(@RequestHeader("Authorization") String token, Map<String, Object> hub);
}
