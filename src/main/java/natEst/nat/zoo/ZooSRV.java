package natEst.nat.zoo;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import natEst.nat.exceptions.NotFoundException;
import natEst.nat.users.User;
import natEst.nat.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ZooSRV {
    @Autowired
    ZooDAO zooDAO;
    @Autowired
    private Cloudinary cloudinaryUploader;
    @Autowired
    UserRepository userRepository;

    public List<Zoo> getAll(String orderBy) {
        return zooDAO.findAll(Sort.by(orderBy));
    }
    public ZooDTO saveZoo(ZooDTO zooDTO, User user){
        User loadedUser = userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        Zoo zoo = new Zoo(zooDTO.name(), zooDTO.email(), zooDTO.image());
        zooDAO.save(zoo);
        return new ZooDTO(zooDTO.name(),zooDTO.email(), zooDTO.image());
    }

    public Zoo getZooById(Long id) {
        return zooDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Zoo updateZoo(Long id, ZooDTO zooDTO){
        Zoo found = zooDAO.findById(id).orElseThrow(() -> new NotFoundException("Habitat not found with ID: " + id));
        found.setName(zooDTO.name());
        found.setEmail(zooDTO.email());
        found.setImage(zooDTO.image());
        return zooDAO.save(found);
    }

    public String UploadImage(Long id, MultipartFile image) throws IOException {
        Zoo found = getZooById(id);
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setImage(url);
        return url;
    }


    public void deleteZoo(Long id) {
        Zoo zoo = this.getZooById(id);
        zooDAO.delete(zoo);
    }
}
