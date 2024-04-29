package natEst.nat.animals;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import natEst.nat.exceptions.BadRequestException;
import natEst.nat.exceptions.NotFoundException;
import natEst.nat.habitats.Habitat;
import natEst.nat.habitats.HabitatoDAO;
import natEst.nat.users.User;
import natEst.nat.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnimalSRV {

    @Autowired
    AnimalDAO animalDAO;
    @Autowired
    private Cloudinary cloudinaryUploader;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private HabitatoDAO habitatoDAO;

    public Page<Animal> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return animalDAO.findAll(pageable);
    }

    public AnimalDTO saveAnimal(AnimalDTO animalDTO, User user) {
        Habitat habitat = habitatoDAO.findById(animalDTO.habitat().getId())
                .orElseThrow(() -> new BadRequestException("Invalid habitat Id"));

        User loadedUser = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        Animal animal = new Animal(animalDTO.name(), animalDTO.species(), animalDTO.age(), animalDTO.gender(), animalDTO.favFood(), animalDTO.weight(), animalDTO.height(), animalDTO.image(), animalDTO.distribution(), animalDTO.reproduction(), habitat);
        animalDAO.save(animal);
        AnimalDTO an = new AnimalDTO(animal.getName(), animalDTO.species(), animal.getAge(), animalDTO.gender(), animalDTO.favFood(), animal.getWeight(), animal.getHeight(), animal.getImage(), animalDTO.distribution(), animalDTO.reproduction(), habitat);
        return an;
    }
    public Animal getAnimalById(Long id) {
        return animalDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public Animal updateAnimal(Long id, AnimalDTO animalDTO) {
        Animal found = animalDAO.findById(id).orElseThrow(() -> new NotFoundException("Animal not found with ID: " + id));


        found.setName(animalDTO.name());
        found.setSpecies(animalDTO.species());
        found.setAge(animalDTO.age());
        found.setGender(animalDTO.gender());
        found.setFavFood(animalDTO.favFood());
        found.setWeight(animalDTO.weight());
        found.setHeight(animalDTO.height());
        found.setImage(animalDTO.image());
        found.setDistribution(animalDTO.distribution());
        found.setReproduction(animalDTO.reproduction());

        return animalDAO.save(found);
    }

    public String UploadImage(Long id, MultipartFile image) throws IOException {
        Animal found = getAnimalById(id);
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setImage(url);
        return url;
    }

    public void deleteAnimal(Long id) {
        Animal animal = this.getAnimalById(id);
        animalDAO.delete(animal);
    }

//--------------------------------------------------

    public List<Object[]> countAnimalsByHabitat() {
        return animalDAO.countAnimalsByHabitat();
    }
    public Map<String, List<Animal>> getAnimalsGroupedByHabitatName() {
        List<Animal> allAnimals = animalDAO.findAll();
        Map<String, List<Animal>> animalsByHabitat = new HashMap<>();
        for (Animal animal : allAnimals) {
            String habitatName = animal.getHabitat().getName();
            animalsByHabitat.computeIfAbsent(habitatName, k -> new ArrayList<>()).add(animal);
        }
        return animalsByHabitat;
    }
}


