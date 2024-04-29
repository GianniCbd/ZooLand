package natEst.nat.animals;

import natEst.nat.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalCTRL {

    @Autowired
    AnimalSRV animalSRV;

    @GetMapping("/all")
    public Page<Animal> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                               @RequestParam(defaultValue = "100") int pageSize,
                               @RequestParam(defaultValue = "id") String orderBy) {
        return animalSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AnimalDTO> saveAnimal(@RequestBody AnimalDTO animalDTO, @AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        AnimalDTO savedAnimal = animalSRV.saveAnimal(animalDTO, user);
        return new ResponseEntity<>(savedAnimal, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Animal findById(@PathVariable Long id) {
        return this.animalSRV.getAnimalById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Animal updateAnimal(@PathVariable Long id, @RequestBody AnimalDTO animalDTO) {
        return animalSRV.updateAnimal(id,animalDTO);
    }

    @PatchMapping("/upload/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadAvatar(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws IOException {
        return this.animalSRV.UploadImage(id, image);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAnimal(@PathVariable Long id) {
        animalSRV.deleteAnimal(id);
    }

//-------------------------------------------------------------------
@GetMapping("/countByHabitat")
public List<Object[]> countAnimalsByHabitat() {
    return animalSRV.countAnimalsByHabitat();
}


}
