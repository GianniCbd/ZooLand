package natEst.nat.animals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalDAO extends JpaRepository<Animal,Long> {
    Animal findByName(String name);

}
