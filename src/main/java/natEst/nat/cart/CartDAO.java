package natEst.nat.cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartDAO extends JpaRepository<Cart,Long> {

    Page<Cart> findAllByUserId(UUID userId, Pageable pageable);

    List<Cart> findByUserId(UUID userId);


}
