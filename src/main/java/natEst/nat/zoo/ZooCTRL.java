package natEst.nat.zoo;

import natEst.nat.users.User;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/zoo")
public class ZooCTRL {

    @Autowired
    ZooSRV zooSRV;
    @GetMapping("/all")
    public List<Zoo> getAllZoos(@RequestParam(required = false, defaultValue = "id") String orderBy) {
        return zooSRV.getAll(orderBy);
    }
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ZooDTO> saveZoo(@RequestBody ZooDTO zooDTO, @AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        ZooDTO savedZoo = zooSRV.saveZoo(zooDTO, user);
        return new ResponseEntity<>(savedZoo, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public Zoo findById(@PathVariable Long id) {
        return this.zooSRV.getZooById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Zoo updateZoo(@PathVariable Long id, @RequestBody ZooDTO zooDTO) {
        return zooSRV.updateZoo(id,zooDTO);
    }


    @PatchMapping("/upload/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadAvatar(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws IOException {
        return this.zooSRV.UploadImage(id, image);
    }
}
