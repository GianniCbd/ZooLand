package natEst.nat.animals;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import natEst.nat.likes.Like;
import natEst.nat.zoo.Zoo;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String species;
    private int age;
    private String gender;
    private String favFood;
    private String weight;
    private String height;
    private String image;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Like> like;


    @ManyToOne
    private Zoo zoo;

    public Animal(String name, String species, int age, String gender, String favFood, String weight, String height,String image) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.gender = gender;
        this.favFood = favFood;
        this.weight = weight;
        this.height = height;
        this.image = image;
        this.like = new ArrayList<>();
    }

    public void addLike(Like like) {
        if (!this.like.contains(like)) {
            this.like.add(like);
        }
    }

    public void removeLike(Like like) {
        this.like.remove(like);
    }
}
