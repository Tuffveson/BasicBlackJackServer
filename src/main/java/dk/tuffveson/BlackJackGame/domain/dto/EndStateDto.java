package dk.tuffveson.BlackJackGame.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class EndStateDto {
    int playerScore;
    int dealerScore;
    private List<CardDto> dealerHand;
    private List<CardDto> playerHand;
    private Boolean winner;
}
