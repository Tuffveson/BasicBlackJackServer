package dk.tuffveson.BlackJackGame.domain.dto;

import dk.tuffveson.BlackJackGame.domain.dto.enums.CardValue;
import dk.tuffveson.BlackJackGame.domain.dto.enums.Suit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class CardDto {
    private CardValue value;
    private Suit suit;
}
