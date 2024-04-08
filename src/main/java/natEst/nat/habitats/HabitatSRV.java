package natEst.nat.habitats;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import natEst.nat.exceptions.NotFoundException;
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

@Service
public class HabitatSRV {
    @Autowired
    HabitatoDAO habitatDAO;
    @Autowired
    private Cloudinary cloudinaryUploader;

    @Autowired
    UserRepository userRepository;

    public Page<Habitat> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return habitatDAO.findAll(pageable);
    }
    public HabitatDTO saveHabitat(HabitatDTO habitatDTO, User user){
        User loadedUser = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        Habitat habitat = new Habitat(habitatDTO.name(), habitatDTO.type(), habitatDTO.capacity(), habitatDTO.terrainType(), habitatDTO.image());
        habitatDAO.save(habitat);
        HabitatDTO hb = new HabitatDTO(habitatDTO.name(), habitatDTO.type(), habitatDTO.capacity(), habitatDTO.terrainType(), habitatDTO.image());
        return  hb;
    }
    public Habitat getHabitatById(Long id) {
        return habitatDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Habitat updateHabitat(Long id, HabitatDTO habitatDTO){
        Habitat found = habitatDAO.findById(id).orElseThrow(() -> new NotFoundException("Habitat not found with ID: " + id));
        found.setName(habitatDTO.name());
        found.setType(habitatDTO.type());
        found.setCapacity(habitatDTO.capacity());
        found.setTerrainType(habitatDTO.terrainType());
        found.setImage(habitatDTO.image());
        return habitatDAO.save(found);
    }

    public String UploadImage(Long id, MultipartFile image) throws IOException {
        Habitat found = getHabitatById(id);
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setImage(url);
        return url;
    }

    public void deleteHabitat(Long id) {
        Habitat habitat = this.getHabitatById(id);
        habitatDAO.delete(habitat);
    }

}
