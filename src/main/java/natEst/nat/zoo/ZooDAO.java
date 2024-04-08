package natEst.nat.zoo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZooDAO extends JpaRepository<Zoo,Long> {
}
