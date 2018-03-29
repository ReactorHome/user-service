package reactor.cloud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reactor.models.Face;

public interface FaceRepository extends JpaRepository<Face, Integer> {
}
