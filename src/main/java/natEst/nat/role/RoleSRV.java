package natEst.nat.role;

import natEst.nat.exceptions.BadRequestException;
import natEst.nat.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleSRV {
    @Autowired
    RoleDAO roleDAO;

    public Page<Role> getAll(int pageNumber, int pageSize, String orderBy) {
        if (pageNumber > 20) pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orderBy));
        return roleDAO.findAll(pageable);
    }

    public Role findById(UUID id) {
        return roleDAO.findById(UUID.fromString(String.valueOf(id))).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public Role findByRole(String role) {
        return roleDAO.findByRole(role.toUpperCase()).orElseThrow(() -> new NotFoundException(role));
    }

    public Role save(RoleDTO roleDTO) {
        String upper = roleDTO.role().toUpperCase();
        if (roleDAO.existsByRole(upper)) throw new BadRequestException("role already exist");
        Role role = new Role(upper);
        return roleDAO.save(role);
    }

    public void deleteById(UUID id) {
        Role found = findById(id);
        roleDAO.delete(found);
    }
}
