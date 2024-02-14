package dk.tuffveson.BlackJackGame.services;

import dk.tuffveson.BlackJackGame.domain.dto.EndStateDto;
import dk.tuffveson.BlackJackGame.domain.dto.GameDto;

import java.util.Optional;

public interface GameService {

    GameDto createNewGame();
//For target 0 is player, 1 is dealer but kept hidden and 2 is dealer which is shown

    Optional<GameDto> dealCards(String sessionId);

    Optional<EndStateDto> resolveGameEnd(String sessionId);

    Optional<GameDto> reshuffleDeck(String sessionId);

    Optional<GameDto> playerDrawCardBySessionId(String sessionId);
}
