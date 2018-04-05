package reactor.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import reactor.exceptions.ModelNotFoundException;
import reactor.feign_clients.HubClient;
import reactor.models.Account;
import reactor.models.Group;
import reactor.models.User;
import reactor.repositories.AccountRepository;
import reactor.repositories.GroupRepository;

import java.util.HashMap;

@RestController
public class GroupsController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private HubClient hubClient;

    @GetMapping(path = "api/groups/{id}")
    @PreAuthorize("isGroupMember(#groupId)")
    public Group details(@AuthenticationPrincipal User user, @PathVariable("id") Integer groupId){
        return groupRepository.findById(groupId).orElseThrow(() -> new ModelNotFoundException("group"));
    }

    @PostMapping(path = "api/groups/{id}/users")
    @PreAuthorize("isGroupOwner(#groupId)")
    public ResponseEntity<?> addUserToGroup(@AuthenticationPrincipal User user, @PathVariable("id") Integer groupId, @RequestBody Account account){
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ModelNotFoundException("group"));
        account = accountRepository.findByUsername(account.getUsername()).orElseThrow(() -> new ModelNotFoundException("account"));
        group.getAccountList().add(account);
        groupRepository.save(group);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(path = "api/groups")
    public Group createGroup(@AuthenticationPrincipal User user, @RequestBody JsonNode root, OAuth2Authentication auth) {
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        String token = details.getTokenValue();
        token = "Bearer " + token;

        Group group = new Group();
        group.setOwner(user.account);
        group.setName(root.get("name").asText());
        groupRepository.save(group);

        String hubPhysicalId = root.get("hub_hardware_id").asText();

        HashMap<String, Object> hub = new HashMap<>();
        hub.put("hardware_id", hubPhysicalId);
        hub.put("group_id", group.getId());

        String hubId = hubClient.registerHub(token, hub);
        group.setHubId(hubId);
        groupRepository.save(group);

        return group;
    }

}
