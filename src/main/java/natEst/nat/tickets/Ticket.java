package natEst.nat.tickets;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import natEst.nat.users.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String ticketType;
    private int price;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Ticket(String ticketType,int price){
        this.ticketType = ticketType;
        this.price=price;

    }
}