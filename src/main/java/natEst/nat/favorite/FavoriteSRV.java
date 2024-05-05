package natEst.nat.favorite;

import natEst.nat.animals.Animal;
import natEst.nat.animals.AnimalDAO;
import natEst.nat.exceptions.NotFoundException;
import natEst.nat.users.User;
import natEst.nat.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FavoriteSRV {
    @Autowired
    private FavoriteDAO favoriteDAO;
    @Autowired
    private UserRepository userDAO;
    @Autowired
    private AnimalDAO animalDAO;

    public Page<Favorite> getFavorites(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return favoriteDAO.findAll(pageable);
    }

    public Page<Favorite> getUserFavorites(UUID userId, int pageNumber, int size, String orderBy) {
        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        Page<Favorite> favorites = new PageImpl<>(user.getFavorites(), pageable, user.getFavorites().size());
        if (favorites.isEmpty()) {
            throw new NotFoundException("la lista preferiti è vuota");
        }
        return favorites;
    }

    public List<Favorite> getFavoritesByUserId(UUID userId) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        return favoriteDAO.findByUserId(userId);
    }

    public Favorite findById(long id) {
        return favoriteDAO.findById(id).orElseThrow(() -> new NotFoundException("il favorite con id: " + id + " non è stato trovato"));
    }

    public Favorite save(UUID userId, FavoriteDTO favoriteDTO) {
        Animal animal = animalDAO.findById(favoriteDTO.animalId()).orElseThrow(() -> new NotFoundException("animal non trovato con id: " + favoriteDTO.animalId()));
        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException("user non trovato con id: " + userId));
        Favorite favorite = new Favorite(animal, user);
        user.addFavorite(favorite);
        return favoriteDAO.save(favorite);
    }

    public void delete(UUID userId, long id) {
        User user = userDAO.findById(userId).orElseThrow(() -> new NotFoundException("user non trovato"));
        List<Favorite> favorites = user.getFavorites();
        Favorite favorite1 = favorites.stream().filter(f -> f.getId() == id).findFirst().get();
        favorites.remove(favorite1);
        favoriteDAO.delete(favorite1);
    }

    public void deleteByUser(UUID userId, long id) {
        Favorite favorite = favoriteDAO.findByUserIdAndAnimalId(userId, id);
        if (favorite != null) {
            favoriteDAO.delete(favorite);
        }
    }

    public Favorite findByAnimal(UUID userId, long id) {
        Favorite favorite = favoriteDAO.findByUserIdAndAnimalId(userId, id);
        if (favorite == null) {
            throw new NotFoundException("favorite non trovato");
        }
        return favorite;
    }
}
