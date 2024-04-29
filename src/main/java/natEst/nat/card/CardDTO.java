package natEst.nat.card;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CardDTO(
        @NotNull(message = "devi inserire il fullName")
        String fullName,
        @NotNull(message = "devi inserire l' amount")
        double amount,
        @NotEmpty(message = "devi inserire il numero della carta")
        @Size(min = 19, max = 19, message = "Il numero della carta deve essere di 16 numeri")
        String cardNumber,
        LocalDate expired,
        @NotEmpty(message = "devi inserire il cvv della carta")
        @Size(min = 3, max = 3, message = "Il CVV deve essere composto da 3 cifre")
        String cvv,
        CardType cardType
) {
        public String formattedCardNumber() {
                return cardNumber.replaceAll("(.{4})", "$1 ");
        }
}