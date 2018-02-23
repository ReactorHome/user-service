package reactor.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.exceptions.ModelNotFoundException;
import reactor.models.Account;
import reactor.models.Group;
import reactor.models.User;
import reactor.repositories.AccountRepository;
import reactor.repositories.GroupRepository;

import java.io.IOException;
import java.util.Optional;

@RestController
public class GroupsController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AccountRepository accountRepository;

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
    public Group createGroup(@AuthenticationPrincipal User user, @RequestBody JsonNode root) {

        Group group = new Group();
        group.setOwner(user.account);
        group.setName(root.get("name").asText());
        String hubPhysicalId = root.get("hubPhysicalId").asText();
        //Feign client call to register hub
        String hubId = "123213";
        group.setHubId(hubId);

        groupRepository.save(group);
        return group;
    }

}
