package natEst.nat.users;

import natEst.nat.passwordMatches.PasswordMatches;

@PasswordMatches
public record UserDTO(
        String name,
        String surname,
        String email,
        String password,
        String confirmPassword
) {
}
