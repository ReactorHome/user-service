package reactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.exceptions.UsernameAlreadyExists;
import reactor.models.Account;
import reactor.models.Group;
import reactor.models.User;
import reactor.repositories.AccountRepository;
import reactor.repositories.GroupRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class AccountController {

    private AccountRepository accountRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody Account account){
        if(accountRepository.findByUsername(account.getUsername()).isPresent()){
            throw new UsernameAlreadyExists();
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("me/groups")
    public List<Group> groups(@AuthenticationPrincipal User user){
        List<Group> groups = new ArrayList<>(groupRepository.findByOwnerIs(user.account));
        groups.addAll(groupRepository.findByAccountListIs(user.account));

        return groups;
    }

    @GetMapping("me")
    public User me(@AuthenticationPrincipal User user){
        return user;
    }
}
