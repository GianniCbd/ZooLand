package natEst.nat.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleDAO extends JpaRepository<Role, UUID> {

    boolean existsByRole(String role);

    Optional<Role> findByRole(String role);



}