package natEst.nat.card;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import natEst.nat.users.User;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Card {
    @Id
    @GeneratedValue
    private UUID id;
    private String fullName;
    private double amount;
    private String cardNumber;
    private LocalDate expired;
    private String cvv;
    private LocalDate paymentDate;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Card(String fullName,double amount, String cardNumber, LocalDate expired, String cvv,CardType cardType, User user) {
        this.fullName = fullName;
        this.amount = amount;
        this.cardNumber = cardNumber;
        this.expired = expired;
        this.cvv = cvv;
        this.cardType = cardType;
        this.user = user;
    }
}


