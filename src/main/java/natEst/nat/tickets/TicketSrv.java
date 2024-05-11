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

    public List<Ticket> getTicketsByUserId(UUID userId) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        return ticketDAO.findByUser_id(userId);
    }

    public Ticket findById(long id) {
        return ticketDAO.findById(id).orElseThrow(() -> new NotFoundException("il ticket con id: " + id + " non è stato trovato"));
    }

//    public Ticket saveTicketUser(UUID userId, TicketDTO ticketDTO) {
//        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException("user non trovato con id: " + userId));
//        Ticket ticket = new Ticket( ticketDTO.ticketType(), ticketDTO.price(),user);
//        user.addTicket(ticket);
//        return ticketDAO.save(ticket);
//    }

    public TicketDTO save(TicketDTO ticketDTO,User user) {
        User loadedUser = userDAO.findById(user.getId()).orElseThrow(() -> new NotFoundException("user not found"));
        Ticket ticket = new Ticket(ticketDTO.ticketType(), ticketDTO.price());
        ticketDAO.save(ticket);
        TicketDTO tic = new TicketDTO(ticket.getTicketType(), ticket.getPrice() );
        return tic;
    }

    public Ticket updateTicket (Long id, TicketDTO ticketDTO){
        Ticket found = ticketDAO.findById(id).orElseThrow(()-> new NotFoundException("Ticket not found with ID: " + id));
        found.setTicketType(ticketDTO.ticketType());
        found.setPrice(ticketDTO.price());
        return ticketDAO.save(found);
    }

    public void delete(Long id) {
        Ticket ticket = this.findById(id);
        ticketDAO.delete(ticket);
    }


}
