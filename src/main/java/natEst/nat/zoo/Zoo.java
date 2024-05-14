package natEst.nat.zoo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String city;
    private String address;
    private String postCode;
    private int cellNumber;
    private String email;
    private  String image;


    @OneToMany(mappedBy = "zoo")
    @JsonIgnore
    private List<Animal> listaAnimali;

    @OneToMany(mappedBy = "zoo")
    @JsonIgnore
    private List<Habitat> listaHabitat;

    public Zoo(String name, String city, String address, String postCode, int cellNumber, String email, String image) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.postCode = postCode;
        this.cellNumber = cellNumber;
        this.email = email;
        this.image = image;
    }
}
