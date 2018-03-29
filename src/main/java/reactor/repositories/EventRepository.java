package reactor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reactor.models.Event;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

}