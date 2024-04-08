package natEst.nat.habitats;

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

@RestController
@RequestMapping("/habitats")
public class HabitatCTRL {
    @Autowired
    HabitatSRV habitatSRV;

    @GetMapping("/all")
    public Page<Habitat> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                               @RequestParam(defaultValue = "10") int pageSize,
                               @RequestParam(defaultValue = "id") String orderBy) {
        return habitatSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HabitatDTO> saveHabitat(@RequestBody HabitatDTO habitatDTO, @AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        HabitatDTO savedHabitat = habitatSRV.saveHabitat(habitatDTO, user);
        return new ResponseEntity<>(savedHabitat, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Habitat findById(@PathVariable Long id) {
        return this.habitatSRV.getHabitatById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Habitat updateHabitat(@PathVariable Long id, @RequestBody HabitatDTO habitatDTO) {
        return habitatSRV.updateHabitat(id,habitatDTO);
    }
    @PatchMapping("/upload/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadAvatar(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws IOException {
        return this.habitatSRV.UploadImage(id, image);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteHabitat(@PathVariable Long id) {
        habitatSRV.deleteHabitat(id);
    }
}
