package reactor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameAlreadyExists extends RuntimeException {
    public UsernameAlreadyExists() {
        super("Username already exists!");
    }
}
