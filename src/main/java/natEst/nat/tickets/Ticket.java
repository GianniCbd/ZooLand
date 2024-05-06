package natEst.nat.tickets;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import natEst.nat.users.User;

import java.time.LocalDateTime;

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
    private double price;
    private LocalDateTime purchaseDateTime;
    private LocalDateTime validFromDateTime;
    private LocalDateTime validUntilDateTime;
    private boolean used;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Ticket(String ticketType, double price, LocalDateTime purchaseDateTime,
                  LocalDateTime validFromDateTime, LocalDateTime validUntilDateTime,User user) {

        this.ticketType = ticketType;
        this.price = price;
        this.purchaseDateTime = purchaseDateTime;
        this.validFromDateTime = validFromDateTime;
        this.validUntilDateTime = validUntilDateTime;
        this.used = false;
        this.user = user;
    }

}