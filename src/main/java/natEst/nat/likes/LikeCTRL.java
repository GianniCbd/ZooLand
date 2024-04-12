package natEst.nat.likes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/likes")
public class LikeCTRL {
    @Autowired
    LikeSRV likeSRV;

    @PostMapping
    public Like save(@RequestParam long animalId, @RequestBody LikeDTO likesDTO) {
        return this.likeSRV.addLike(animalId, likesDTO);
    }

    @DeleteMapping("/delete/{title}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDeleteMyLike(@RequestParam UUID userId, @PathVariable String name, @RequestParam long id) {
        this.likeSRV.findAndDeleteMyLike(userId, name, id);
    }

    @GetMapping("/user/{likeId}")
    public Like getMyLike(@RequestParam UUID userId, @PathVariable long likeId) {
        return this.likeSRV.findByUser(userId, likeId);
    }
}
