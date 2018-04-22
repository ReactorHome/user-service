package reactor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reactor.models.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNotificationIdListContains(String notification);
    Optional<Account> findByUsername(String username);
    Optional<Account> findById(Long id);
}
