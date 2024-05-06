package natEst.nat.tickets;

import natEst.nat.exceptions.NotFoundException;
import natEst.nat.users.User;
import natEst.nat.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketSrv {

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private UserRepository userDAO;


    public Page<Ticket> getTicket(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return ticketDAO.findAll(pageable);
    }

    public Page<Ticket> getUserTickets(UUID userId, int pageNumber, int size, String orderBy) {
        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        Page<Ticket> tickets = new PageImpl<>(user.getTickets(), pageable, user.getTickets().size());
        if (tickets.isEmpty()) {
            throw new NotFoundException("la lista tickets è vuota");
        }
        return tickets;
    }

    public List<Ticket> getFTicketsByUserId(UUID userId) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        return ticketDAO.findByUser_id(userId);
    }

    public Ticket findById(long id) {
        return ticketDAO.findById(id).orElseThrow(() -> new NotFoundException("il ticket con id: " + id + " non è stato trovato"));
    }

    public Ticket save(UUID userId, TicketDTO ticketDTO) {
        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException("user non trovato con id: " + userId));
        Ticket ticket = new Ticket( ticketDTO.ticketType(), ticketDTO.price(), ticketDTO.purchaseDateTime(),ticketDTO.validFromDateTime(),ticketDTO.validUntilDateTime(),user);
        user.addTicket(ticket);
        return ticketDAO.save(ticket);
    }

    public Ticket updateTicket (Long id, TicketDTO ticketDTO){
        Ticket found = ticketDAO.findById(id).orElseThrow(()-> new NotFoundException("Ticket not found with ID: " + id));
        found.setTicketType(ticketDTO.ticketType());
        found.setPrice(ticketDTO.price());
        return ticketDAO.save(found);
    }

    public void delete(UUID userId, long id) {
        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException("user non trovato"));
        List<Ticket> tickets = user.getTickets();
        Ticket ticket1 = tickets.stream().filter(t -> t.getId() == id).findFirst().get();
        tickets.remove(ticket1);
        ticketDAO.delete(ticket1);
    }

    public void deleteByUser(UUID userId, long id) {
        Ticket ticket = ticketDAO.findByUserAndTicketId(userId, id);
        if (ticket != null) {
            ticketDAO.delete(ticket);
        }
    }
}
