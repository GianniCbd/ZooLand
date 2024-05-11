package natEst.nat.cart;

import natEst.nat.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartCTRL {

    @Autowired
    CartSRV cartSRV;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Cart> getCart(@RequestParam(defaultValue = "id")String orderBy){
        return this.cartSRV.getCart(orderBy);
    }

    @GetMapping("/myCart")
    public List<Cart> getMyCart(@RequestParam UUID userId){
        return this.cartSRV.getCartByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cart save(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody CartDTO cartDTO) {
        return this.cartSRV.save(currentAuthenticatedUser.getId(), cartDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCart(@PathVariable Long id) {
        cartSRV.delete(id);
    }
}
