package natEst.nat.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import natEst.nat.role.Role;

import java.util.Set;
import java.util.UUID;

@JsonIgnoreProperties({"password", "confirmPassword"})
public record UserResponseDTO(
        UUID id,
        String name,
        String surname,
        String email,
        String password,
        String confirmPassword,
        Set<Role> roles


) {
}