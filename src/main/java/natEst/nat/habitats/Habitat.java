package natEst.nat.habitats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import natEst.nat.animals.Animal;
import natEst.nat.zoo.Zoo;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Habitat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private int capacity;
    private String terrainType;
    private String image;

    @JsonIgnore
    @OneToMany(mappedBy = "habitat")
    private List<Animal> listaAnimali;

    @ManyToOne
    private Zoo zoo;

    public Habitat(String name, String type, int capacity, String terrainType, String image) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.terrainType = terrainType;
        this.image = image;
    }
}
