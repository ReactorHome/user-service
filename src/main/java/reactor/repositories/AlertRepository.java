package reactor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reactor.models.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}
