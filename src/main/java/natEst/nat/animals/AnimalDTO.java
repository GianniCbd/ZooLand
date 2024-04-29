package natEst.nat.animals;

import natEst.nat.habitats.Habitat;

public record AnimalDTO(
       String name,
       String species,
       int age,
       String gender,
       String favFood,
       String weight,
       String height,
       String image,
       String distribution,
       String reproduction,
       Habitat habitat

) {
}
