package natEst.nat.card;

import natEst.nat.exceptions.BadRequestException;
import natEst.nat.exceptions.PaymentException;
import natEst.nat.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/card")
public class CardCTRL {
    @Autowired
    private CardSRV cardSrv;
    @GetMapping("/my-card")
    public Page<Card> getMyCard(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String orderBy
    ) {
        return this.cardSrv.getUserCard(currentAuthenticatedUser.getId(), page, size, orderBy);
    }


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Card> getAllCard(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy
    ) {
        return this.cardSrv.getAllCard(page, size, orderBy);
    }



    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Card findById(@PathVariable UUID id) {
        return this.cardSrv.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Card save(@RequestParam UUID userId, @RequestBody @Validated CardDTO cartaDTO, BindingResult validation) {
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.cardSrv.save(userId, cartaDTO);
    }


    @PostMapping("/{cardId}/payments")
    public void effettuaPagamento(@PathVariable UUID cardId, @RequestParam double amount) {
        try {
            cardSrv.makePayment(cardId, amount);
        } catch (PaymentException e) {
            throw new BadRequestException("Errore durante il pagamento: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        cardSrv.delete(id);
    }

}

