package natEst.nat.favorite;

import natEst.nat.exceptions.UnauthorizedException;
import natEst.nat.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favorites")
public class FavoriteCTRL {

    @Autowired
    FavoriteSRV favoriteSRV;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Favorite> getAllFavorites(@RequestParam UUID userId,@RequestParam(defaultValue = "0")int page,
                                          @RequestParam(defaultValue = "10")int size,
                                          @RequestParam(defaultValue = "id")String orderBy
    ){
        return this.favoriteSRV.getUserFavorites(userId,page,size,orderBy);
    }

    @GetMapping("/myFavorite")
    public List<Favorite> getMyFavorite(@RequestParam UUID userId){
        return this.favoriteSRV.getFavoritesByUserId(userId);
    }

    @GetMapping("/animal/{animalId}")
    public Favorite getMyFavorite(@RequestParam UUID userId, @PathVariable long animalId) {
        return this.favoriteSRV.findByAnimal(userId, animalId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Favorite findById(@PathVariable long id) {
        return this.favoriteSRV.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Favorite save(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody FavoriteDTO favoriteDTO) {
        return this.favoriteSRV.save(currentAuthenticatedUser.getId(), favoriteDTO);
    }

    @DeleteMapping("/delete/{animalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestParam UUID userId, @PathVariable long animalId) {
        if (!currentAuthenticatedUser.getId().equals(userId)) {
            throw new UnauthorizedException("Non hai i permessi per eliminare questo preferito.");
        }
        this.favoriteSRV.deleteByUser(userId, animalId);
    }
}
