package natEst.nat.users;

import natEst.nat.exceptions.BadRequestException;
import natEst.nat.exceptions.NotFoundException;
import natEst.nat.exceptions.UnauthorizedException;
import natEst.nat.role.Role;
import natEst.nat.role.RoleSRV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRep;
    @Autowired
    RoleSRV roleSRV;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Page<User> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return userRep.findAll(pageable);
    }


    public User findById(UUID id) {
        return userRep.findById(UUID.fromString(String.valueOf(id))).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public UserResponseDTO save(UserDTO userDTO) throws IOException {
        if (userRep.existsByEmail(userDTO.email())) {
            throw new BadRequestException("email already exist");
        }
        Set<Role> roles = new HashSet<>();
        Role userRole = roleSRV.findByRole("USER");
        roles.add(userRole);

        User user = new User(
                userDTO.name(),
                userDTO.surname(),
                userDTO.email(),
                passwordEncoder.encode(userDTO.password()),
                passwordEncoder.encode(userDTO.confirmPassword()),
                roles
        );
        User savedUser = userRep.save(user);
        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getSurname(),
                savedUser.getEmail(),
                savedUser.getPassword(),
                savedUser.getConfirmPassword(),
                savedUser.getRoles()
        );

    }

    public User findByEmail(String email) {
        return userRep.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }
    public User findByIdAndUpdate(UUID id, UserDTO userDTO,User user){
        User found= findById(UUID.fromString(String.valueOf(id)));
        if (!user.getId().equals(found.getId())) throw new UnauthorizedException("User with wrong id");
        found.setName(userDTO.name());
        found.setSurname(userDTO.surname());
        found.setEmail(userDTO.email());

        return userRep.save(found);
    }
    public void deleteById(UUID id, User user) {
        User found = findById(id);
        if (!user.getId().equals(UUID.fromString(String.valueOf(id)))) throw new UnauthorizedException("User with wrong id");
        userRep.delete(found);
    }

    public void addRole(UUID id, String name) {
        Role role = roleSRV.findByRole(name);
        User foundUser = findById(id);
        foundUser.addRole(role);
        userRep.save(foundUser);
    }

    public void removeRole(UUID id, String name) {
        Role role = roleSRV.findByRole(name);
        User foundUser = findById(id);
        foundUser.removeRole(role);
        userRep.save(foundUser);
    }
}

