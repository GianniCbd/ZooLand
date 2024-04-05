package natEst.nat.zoo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import natEst.nat.animals.Animal;
import natEst.nat.habitats.Habitat;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Zoo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "zoo")
    private List<Animal> listaAnimali;

    @OneToMany(mappedBy = "zoo")
    private List<Habitat> listaHabitat;


}
