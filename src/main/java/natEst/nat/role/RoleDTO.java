package natEst.nat.role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RoleDTO(
        @NotEmpty(message = "Role is required!")
        @Size(min = 4, max = 20, message = "Title must be 4 to 20 characters long ")
        String role
) {

}