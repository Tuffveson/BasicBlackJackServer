package dk.tuffveson.BlackJackGame.services;

import dk.tuffveson.BlackJackGame.domain.dto.CardDto;
import dk.tuffveson.BlackJackGame.domain.entities.CardEntity;
import dk.tuffveson.BlackJackGame.domain.entities.GameEntity;

import java.util.List;

public interface HandAndDeckService {
    int getScoreFromDto(List<CardDto> hand);

    int getScoreFromEntity(List<CardEntity> hand);

    int getHardScoreFromEntity(List<CardEntity> hand);

    boolean containsAce(List<CardEntity> hand);

    GameEntity drawCard(GameEntity game, int targetHand);

    default GameEntity playerDrawCard(GameEntity game){
        return drawCard(game,0);
    };
    default GameEntity dealerDrawCardHidden(GameEntity game){
        return drawCard(game,1);
    };
    default GameEntity dealerDrawCardShown(GameEntity game){
        return drawCard(game,2);
    };
}
