package natEst.nat.role;

import natEst.nat.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/roles")
public class RoleCTRL {
    @Autowired
    RoleSRV roleSRV;

    @GetMapping
    public Page<Role> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                             @RequestParam(defaultValue = "10") int pageSize,
                             @RequestParam(defaultValue = "role") String orderBy) {
        return roleSRV.getAll(pageNumber, pageSize, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role save(@RequestBody @Validated RoleDTO roleDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.roleSRV.save(roleDTO);
    }

    @GetMapping("/{id}")
    public Role findById(@PathVariable UUID id) {
        return roleSRV.findById(id);
    }


    @GetMapping("/roleName/{name}")
    public Role getCurrentUser(@PathVariable String name) {
        return roleSRV.findByRole(name);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorById(@PathVariable UUID id) {
        roleSRV.deleteById(id);
    }
}