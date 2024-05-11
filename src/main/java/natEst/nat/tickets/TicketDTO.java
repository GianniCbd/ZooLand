package natEst.nat.tickets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record TicketDTO(

        @NotBlank(message = "Il tipo di biglietto non può essere vuoto") String ticketType,
        @Positive(message = "Il prezzo deve essere un valore positivo") int price


      ) {
}
