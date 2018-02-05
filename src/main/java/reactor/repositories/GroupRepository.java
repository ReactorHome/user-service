package reactor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import reactor.models.Account;
import reactor.models.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, Integer> {
    Optional<Group> findById(Integer id);
    List<Group> findByOwnerIs(Account owner);
    List<Group> findByAccountListIs(Account account);
}
