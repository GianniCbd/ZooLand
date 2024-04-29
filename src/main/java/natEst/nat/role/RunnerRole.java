package natEst.nat.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerRole implements CommandLineRunner {
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    RoleSRV roleSRV;

    @Override
    public void run(String... args) throws Exception {

        if (!roleDAO.existsByRole("USER")) {
            roleSRV.save(new RoleDTO("USER"));
        }

        if (!roleDAO.existsByRole("MANAGER")) {
            roleSRV.save(new RoleDTO("MANAGER"));
        }

        if (!roleDAO.existsByRole("ADMIN")) {
            roleSRV.save(new RoleDTO("ADMIN"));
        }

    }


}
