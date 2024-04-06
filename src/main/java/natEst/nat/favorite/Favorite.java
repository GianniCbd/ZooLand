package natEst.nat.favorite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import natEst.nat.animals.Animal;
import natEst.nat.users.User;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "mangaId")
    private Animal animal;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Favorite(Animal animal, User user) {
        this.animal = animal;
        this.user = user;
    }
}