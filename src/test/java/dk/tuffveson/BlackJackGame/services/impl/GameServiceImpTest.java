package dk.tuffveson.BlackJackGame.services.impl;

import dk.tuffveson.BlackJackGame.dao.repository.GameRepository;
import dk.tuffveson.BlackJackGame.domain.dto.CardDto;
import dk.tuffveson.BlackJackGame.domain.dto.GameDto;
import dk.tuffveson.BlackJackGame.domain.entities.GameEntity;
import dk.tuffveson.BlackJackGame.mappers.impl.GameMapper;
import dk.tuffveson.BlackJackGame.services.GameService;
import dk.tuffveson.BlackJackGame.services.HandAndDeckService;
import org.apache.commons.collections4.ListUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static dk.tuffveson.BlackJackGame.testUtil.TestDataUtilGameDataGen.gameTestInstanceA;
import static dk.tuffveson.BlackJackGame.testUtil.TestDataUtilGameDataGen.gameTestInstanceNoId;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class GameServiceImpTest {

    @MockBean
    GameRepository gameRepository;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    HandAndDeckService handAndDeckService;

    @Autowired
    GameService gameService;

    @Test
    void testCreateNewGameReturnsNewGame(){
        GameDto gameDto = gameTestInstanceNoId();
        GameEntity gameEntity = gameMapper.destinationToSource(gameDto);
        GameEntity gameEntityWithID = gameMapper.destinationToSource(gameDto);
        gameEntityWithID.setId(1);
        gameDto.setSessionId(1);
        Mockito.when(gameRepository.save(gameEntity)).thenReturn(gameEntityWithID);


        assertEquals(gameService.createNewGame(),gameDto);


    }

    @Test
    void testDrawCardFirstAndSecondCardFromFreshDeckToPlayerHand(){
        GameDto gameDto = gameTestInstanceA(); //Test instance A has a full deck and id 1
        GameEntity gameEntity = gameMapper.destinationToSource(gameDto);

        //We would need to lookup that the game exists so we mock the lookup
        Mockito.when(gameRepository.findById(gameEntity.getId())).thenReturn(Optional.of(gameEntity));

        GameEntity gameWithCardDrawnEntity = handAndDeckService.playerDrawCard(gameEntity);
        List<CardDto> drawnOut;

        // we called the draw from deck, so the deck should be one smaller
        drawnOut = ListUtils.subtract(gameDto.getDeck(), gameMapper.sourceToDestination(gameWithCardDrawnEntity).getDeck());
        assertEquals(drawnOut.size(),1);

        // we drew to the player hand so it should be one bigger

        //Our test has a null check in case the hand was not instantiated
        int originalSize = (gameDto.getPlayerHand() == null) ? 0 : gameDto.getPlayerHand().size();

        assertEquals(originalSize+1,gameWithCardDrawnEntity.getPlayerHand().size());

        GameEntity gameWithCardDrawnTwice = handAndDeckService.playerDrawCard(gameWithCardDrawnEntity);
        List<CardDto> drawnOutSecond;

        // we called the draw from deck, so the deck should be one smaller

        drawnOutSecond = ListUtils.subtract(gameDto.getDeck(),gameMapper.sourceToDestination(gameWithCardDrawnTwice).getDeck());
        assertEquals(2,drawnOutSecond.size());


        assertEquals(originalSize+2,gameWithCardDrawnTwice.getPlayerHand().size());

    }

    @Test
    void testDrawCardFirstAndSecondCardFromFreshDeckToDealerHand(){
        GameDto gameDto = gameTestInstanceA(); //Test instance A has a full deck and id 1
        GameEntity gameEntity = gameMapper.destinationToSource(gameDto);

        //We would need to lookup that the game exists so we mock the lookup

        //We might want to save something, a successful save would just echo the input, but there might be some updated value like an id that we want to catch
        GameEntity gameWithCardDrawn = handAndDeckService.dealerDrawCardShown(gameEntity);
        List<CardDto> drawnOut;


        // we called the draw from deck, so the deck should be one smaller
        drawnOut = ListUtils.subtract(gameDto.getDeck(), gameMapper.sourceToDestination(gameWithCardDrawn).getDeck());
        assertEquals(drawnOut.size(),1);


        // we drew to the Dealer hand so it should be one bigger

        //Our test has a null check in case the hand was not instantiated
        int originalSize = (gameDto.getDealerHandShown() == null) ? 0 : gameDto.getDealerHandShown().size();
        assertEquals(originalSize+1,gameMapper.sourceToDestination(gameWithCardDrawn).getDealerHandShown().size());

        //The game in the database was updated to the game with a drawn card
        GameEntity gameWithCardDrawnTwice = handAndDeckService.dealerDrawCardShown(gameWithCardDrawn);
        List<CardDto> drawnOutSecond;

        // we called the draw from deck, so the deck should be one smaller
        drawnOutSecond = ListUtils.subtract(gameDto.getDeck(),gameMapper.sourceToDestination(gameWithCardDrawnTwice).getDeck());
        assertEquals(2,drawnOutSecond.size());

        assertEquals(originalSize+2,gameWithCardDrawnTwice.getDealerHandShown().size());

    }

}