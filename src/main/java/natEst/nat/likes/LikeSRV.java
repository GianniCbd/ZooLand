package natEst.nat.likes;

import natEst.nat.animals.Animal;
import natEst.nat.animals.AnimalDAO;
import natEst.nat.exceptions.BadRequestException;
import natEst.nat.exceptions.NotFoundException;
import natEst.nat.exceptions.UnauthorizedException;
import natEst.nat.users.User;
import natEst.nat.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class LikeService {
    @Autowired
    private LikeDAO likesDAO;
    @Autowired
    private AnimalDAO animalDAO;
    @Autowired
    private UserService userDAO;

    public Like findById(long id) {
        return likesDAO.findById(id).orElseThrow(() -> new NotFoundException("il like con id: " + id + " non è stato trovato"));
    }

    public Like addLike(long animalId, LikeDTO likeDTO) {
        Animal animal = animalDAO.findById(animalId)
                .orElseThrow(() -> new NotFoundException("animale non trovato"));

        User user = userDAO.findById(likeDTO.userId())
                .orElseThrow(() -> new NotFoundException("user non trovato"));

        boolean userAlreadyLiked = animal.getLike().stream().anyMatch(like -> like.getUser().equals(user));
        if (userAlreadyLiked) {
            throw new BadRequestException("L'utente ha già messo un like su questo animale.");
        }

        Like like = new Like(user);
        likesDAO.save(like);
        animal.addLike(like);
        animalDAO.save(animal);
        return like;
    }

    public void findAndDelete(long id, String title) {
        Like likes = this.findById(id);
        Animal animal = animalDAO.findByName(title);
        if (animal != null) {
            animal.removeLike(likes);
            animalDAO.save(animal);
        }
        this.likesDAO.delete(likes);
    }

    public void findAndDeleteMyLike(UUID userId, String name, long id) {
        Like like = this.findById(id);
        if (!like.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("Non sei autorizzato a eliminare questo like.");
        }
        Animal animal = animalDAO.findByName(name);
        if (animal != null) {
            animal.removeLike(like);
            animalDAO.save(animal);
        }
        this.likesDAO.delete(like);
    }

    public Like findByUser(UUID userId, long id) {
        Like like = likesDAO.findByUserIdAndId(userId, id);
        if (like == null) {
            throw new NotFoundException("favorite non trovato");
        }
        return like;
    }
}