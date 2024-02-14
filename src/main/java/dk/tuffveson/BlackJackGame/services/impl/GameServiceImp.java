package dk.tuffveson.BlackJackGame.services.impl;

import dk.tuffveson.BlackJackGame.dao.repository.GameRepository;
import dk.tuffveson.BlackJackGame.domain.dto.CardDto;
import dk.tuffveson.BlackJackGame.domain.dto.EndStateDto;
import dk.tuffveson.BlackJackGame.domain.dto.GameDto;
import dk.tuffveson.BlackJackGame.domain.entities.CardEntity;
import dk.tuffveson.BlackJackGame.domain.entities.GameEntity;
import dk.tuffveson.BlackJackGame.mappers.impl.GameMapper;
import dk.tuffveson.BlackJackGame.services.CardService;
import dk.tuffveson.BlackJackGame.services.DealerAiService;
import dk.tuffveson.BlackJackGame.services.GameService;
import dk.tuffveson.BlackJackGame.services.HandAndDeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GameServiceImp implements GameService {

    @Autowired
    GameMapper gameMapper;
    @Autowired
    GameRepository gameRepository;

    @Autowired
    DealerAiService dealerAiService;

    @Autowired
    HandAndDeckService handAndDeckService;



    @Override
    public GameDto createNewGame() {
        List<CardDto> deck = CardService.fullDeckDto();
        GameDto gameDtoIn = GameDto.builder().deck(deck).ongoing(false).build();
        GameEntity gameEntity = gameMapper.destinationToSource(gameDtoIn);
        return gameMapper.sourceToDestination(gameRepository.save(gameEntity));
    }




    @Override
    public Optional<GameDto> dealCards(String sessionId) {
        Optional<GameEntity> gameEntityOptionality = gameRepository.findById(Integer.valueOf(sessionId));
        if(gameEntityOptionality.isEmpty()){
            return Optional.empty();
        }
        GameEntity gameEntity = gameEntityOptionality.get();

        if(gameEntity.getOngoing()){
            return Optional.empty();
        }
        gameEntity.setOngoing(true);
        gameEntity = handAndDeckService.playerDrawCard(gameEntity);
        gameEntity = handAndDeckService.playerDrawCard(gameEntity);
        gameEntity = handAndDeckService.dealerDrawCardHidden(gameEntity);
        gameEntity = handAndDeckService.dealerDrawCardShown(gameEntity);
        GameEntity newGameState =gameRepository.save(gameEntity);
        return Optional.of(gameMapper.sourceToDestination(newGameState));
    }
    public GameEntity revealDealerCard(GameEntity game) {
        CardEntity hidenCard = game.getDealHandHidden();
        List<CardEntity> shownCards = new ArrayList<>(game.getDealerHandShown());
        shownCards.add(hidenCard);
        game.setDealHandHidden(null);
        game.setDealerHandShown(shownCards);
        return game;
    }

    @Override
    public Optional<EndStateDto> resolveGameEnd(String sessionId) {
        Optional<GameEntity> gameEntityOptionality = gameRepository.findById(Integer.valueOf(sessionId));
        if(gameEntityOptionality.isEmpty()){
            return Optional.empty();
        }
        GameEntity game = gameEntityOptionality.get();
        game = revealDealerCard(game);
        game = dealerAiService.makeMove(game);
        GameDto gameDto = gameMapper.sourceToDestination(game);
        int playerScore = handAndDeckService.getScoreFromDto(gameDto.getPlayerHand());
        int dealerScore = handAndDeckService.getScoreFromDto(gameDto.getDealerHandShown());

        boolean winner = (playerScore > dealerScore || dealerScore > 21) && playerScore <= 21;

        return Optional.of(EndStateDto.builder().dealerScore(dealerScore).playerScore(playerScore).playerHand(gameDto.getPlayerHand()).dealerHand(gameDto.getDealerHandShown()).winner(winner).build());
    }


    @Override
    public Optional<GameDto> reshuffleDeck(String sessionId) {
        Optional<GameEntity> gameEntityOptionality = gameRepository.findById(Integer.valueOf(sessionId));
        if(gameEntityOptionality.isEmpty()){
            return Optional.empty();
        }
        List<CardEntity> deck = CardService.fullDeckEntity();
        GameEntity gameEntity = gameEntityOptionality.get();
        gameEntity.setDealHandHidden(null);
        gameEntity.setPlayerHand(null);
        gameEntity.setDealerHandShown(null);
        gameEntity.setDeck(deck);
        return Optional.of(gameMapper.sourceToDestination(gameEntity));
    }

    @Override
    public Optional<GameDto> playerDrawCardBySessionId(String sessionId) {
        Optional<GameEntity> gameEntityOptionality = gameRepository.findById(Integer.valueOf(sessionId));
        if(gameEntityOptionality.isEmpty()){
            return Optional.empty();
        }
        GameEntity game = gameEntityOptionality.get();
        GameEntity newGameSaved = gameRepository.save(handAndDeckService.playerDrawCard(game));
        return Optional.of(gameMapper.sourceToDestination(newGameSaved));
    }
}
