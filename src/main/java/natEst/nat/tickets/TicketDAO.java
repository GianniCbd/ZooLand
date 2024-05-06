package natEst.nat.tickets;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketDAO extends JpaRepository<Ticket,Long> {

    Page<Ticket> findAllUserById(UUID userId, Pageable pageable);

    List<Ticket>findByUser_id(UUID userId);

    @Query("SELECT t FROM Ticket t WHERE t.user = :user AND t.id = :ticketId")
    Ticket findByUserAndTicketId(@Param("user") UUID user, @Param("ticketId") Long ticketId);


}
