package natEst.nat.animals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalDAO extends JpaRepository<Animal,Long> {
    Animal findByName(String name);

    @Query("SELECT a.habitat.name, COUNT(a) FROM Animal a GROUP BY a.habitat.name ORDER BY a.habitat.name")
    List<Object[]> countAnimalsByHabitat();
}
