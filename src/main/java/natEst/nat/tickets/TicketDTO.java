package natEst.nat.tickets;

import java.time.LocalDateTime;

public record TicketDTO(

        String ticketType,
         Double price,
        LocalDateTime purchaseDateTime,
        LocalDateTime validFromDateTime,
        LocalDateTime validUntilDateTime,
        boolean used
) {
}
