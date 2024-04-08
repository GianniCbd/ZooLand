package natEst.nat.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LikeDAO extends JpaRepository<Like, Long> {
    Like findByUserIdAndId(UUID userId, Long id);

}
