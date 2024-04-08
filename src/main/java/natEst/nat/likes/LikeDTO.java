package natEst.nat.likes;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record LikeDTO(
        @NotEmpty(message = "è necessario l'id dello user")
        UUID userId
) {
}
