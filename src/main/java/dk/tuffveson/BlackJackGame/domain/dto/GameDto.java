package dk.tuffveson.BlackJackGame.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class GameDto {
    private List<CardDto> deck;
    private List<CardDto> playerHand;
    private List<CardDto> dealerHandShown;
    private Integer sessionId;
    private Boolean ongoing;
}
