package dk.tuffveson.BlackJackGame.services;

import dk.tuffveson.BlackJackGame.domain.entities.GameEntity;

public interface DealerAiService {
    GameEntity makeMove(GameEntity game);
}
