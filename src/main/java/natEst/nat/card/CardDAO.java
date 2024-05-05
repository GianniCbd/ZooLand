package natEst.nat.card;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardDAO extends JpaRepository<Card, UUID> {
    Page<Card> findAllByUserId(UUID userId, Pageable pageable);


}
