package dk.tuffveson.BlackJackGame.mappers.impl;

import dk.tuffveson.BlackJackGame.domain.dto.CardDto;
import dk.tuffveson.BlackJackGame.domain.dto.GameDto;
import dk.tuffveson.BlackJackGame.domain.entities.CardEntity;
import dk.tuffveson.BlackJackGame.domain.entities.GameEntity;
import dk.tuffveson.BlackJackGame.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameMapper implements Mapper<GameDto, GameEntity> {

    @Autowired
    CardMapper cardMapper;

    @Override
    public GameDto sourceToDestination(GameEntity gameEntity) {
        List<CardEntity> deckEntity = gameEntity.getDeck();
        List<CardEntity> playerHandEntity = gameEntity.getPlayerHand();
        List<CardEntity> dealerHandEntity = gameEntity.getDealerHandShown();
        GameDto.GameDtoBuilder gameDToBuilder = GameDto.builder();

        //we just transfer the data from one list to another but we have to check that the value is not null, also we forget the hidden dealer hand, as that is not for the presentation layer to know

        if (deckEntity != null){
            gameDToBuilder.deck(deckEntity.stream().map(cardEntity -> cardMapper.sourceToDestination(cardEntity)).toList());
        }
        if (playerHandEntity != null){
            gameDToBuilder.playerHand(playerHandEntity.stream().map(cardEntity -> cardMapper.sourceToDestination(cardEntity)).toList());
        }
        if (dealerHandEntity != null){
            gameDToBuilder.dealerHandShown(dealerHandEntity.stream().map(cardEntity -> cardMapper.sourceToDestination(cardEntity)).toList());
        }
        return gameDToBuilder.sessionId(gameEntity.getId()).ongoing(gameEntity.getOngoing()).build();
    }
    @Override
    public GameEntity destinationToSource(GameDto gameDto) {
        // NOTE that in this mapping the dealer hand hidden will always become null, this is because the dealer hand hidden is not to ever be presented to a player directly and therefore not in our presentation layer
        List<CardDto> deckDto = gameDto.getDeck();
        List<CardDto> playerHandDto = gameDto.getPlayerHand();
        List<CardDto> dealerHandShownDto =  gameDto.getDealerHandShown();

        GameEntity.GameEntityBuilder gameEntityBuilder = GameEntity.builder();

        if (deckDto != null){
            gameEntityBuilder.deck(deckDto.stream().map(cardDto -> cardMapper.destinationToSource(cardDto)).toList());
        }
        if (playerHandDto != null){
            gameEntityBuilder.playerHand(playerHandDto.stream().map(cardDto -> cardMapper.destinationToSource(cardDto)).toList());
        }
        if (dealerHandShownDto != null){
            gameEntityBuilder.dealerHandShown(dealerHandShownDto.stream().map(cardDto -> cardMapper.destinationToSource(cardDto)).toList());
        }
        return gameEntityBuilder.id(gameDto.getSessionId()).ongoing(gameDto.getOngoing()).build();
    }
}
