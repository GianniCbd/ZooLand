package natEst.nat.score;

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
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private int score;

    @ManyToOne
    private User user;

    public Score(int score, User user) {
        this.score = score;
        this.user = user;
    }


}
