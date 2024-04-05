package natEst.nat.auth;

import natEst.nat.exceptions.UnauthorizedException;
import natEst.nat.security.JWTTools;
import natEst.nat.users.User;
import natEst.nat.users.UserLoginDTO;
import natEst.nat.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userSRV;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;
    public String authenticateUserAndGenerateToken(UserLoginDTO payload) {
        User user = userSRV.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Wrong credentials!");
        }

    }


}
