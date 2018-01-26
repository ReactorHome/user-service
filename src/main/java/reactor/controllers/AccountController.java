package reactor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.exceptions.UsernameAlreadyExists;
import reactor.models.Account;
import reactor.repositories.AccountRepository;

@RestController
@RequestMapping(path = "api/users")
public class AccountController {

    private AccountRepository accountRepository;

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
}
