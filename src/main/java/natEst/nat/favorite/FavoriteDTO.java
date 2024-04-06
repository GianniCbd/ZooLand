package natEst.nat.favorite;


import java.util.UUID;

public record FavoriteDTO(
        long animal,
        UUID user
) {
}