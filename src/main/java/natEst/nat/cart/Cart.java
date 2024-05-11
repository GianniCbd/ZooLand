package natEst.nat.cart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import natEst.nat.tickets.Ticket;
import natEst.nat.users.User;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private Ticket ticket;
    @ManyToOne
    private User user;



    public Cart(Ticket ticket, User user) {

        this.ticket = ticket;
        this.user = user;
    }


}
