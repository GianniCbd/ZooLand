package natEst.nat.habitats;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import natEst.nat.zoo.Zoo;

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
