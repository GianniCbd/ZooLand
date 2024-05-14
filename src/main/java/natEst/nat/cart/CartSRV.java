package natEst.nat.cart;

import natEst.nat.exceptions.NotFoundException;
import natEst.nat.tickets.Ticket;
import natEst.nat.tickets.TicketDAO;
import natEst.nat.users.User;
import natEst.nat.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartSRV {

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private UserRepository userDAO;
    @Autowired
    private TicketDAO ticketDAO;



    public List<Cart> getCart(String orderBy) {
        return cartDAO.findAll(Sort.by(orderBy));
    }

    public List<Cart> getCartByUserId(UUID userId) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        return cartDAO.findByUserId(userId);
    }

    public Cart findById(long id) {
        return cartDAO.findById(id).orElseThrow(() -> new NotFoundException("il carrello con id: " + id + " non è stato trovato"));
    }

    public Cart save(UUID userId, CartDTO cartDTO) {
        Ticket ticket = ticketDAO.findById(cartDTO.ticketId()).orElseThrow(() -> new NotFoundException("ticket non trovato con id: " + cartDTO.ticketId()));
        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException("user non trovato con id: " + userId));
        Cart cart = new Cart( ticket,user);
        user.addTicket(ticket);
        return cartDAO.save(cart);
    }

    public int countTicketsInCart(UUID userId) {
        Long count = cartDAO.countByUserId(userId);
        return count != null ? count.intValue() : 0; // Gestisce anche il caso di null
    }

    public void delete(Long id) {
        Cart cart = this.findById(id);
        cartDAO.delete(cart);
    }





}
