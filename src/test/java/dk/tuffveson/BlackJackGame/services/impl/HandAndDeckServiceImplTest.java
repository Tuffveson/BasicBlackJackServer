package dk.tuffveson.BlackJackGame.services.impl;

import dk.tuffveson.BlackJackGame.domain.dto.CardDto;
import dk.tuffveson.BlackJackGame.domain.entities.CardEntity;
import dk.tuffveson.BlackJackGame.mappers.impl.CardMapper;
import dk.tuffveson.BlackJackGame.services.HandAndDeckService;
import dk.tuffveson.BlackJackGame.testUtil.TestDataUtilGameDataGen;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class HandAndDeckServiceImplTest {

    @Autowired
    HandAndDeckService handAndDeckService;

    @Autowired
    CardMapper cardMapper;

    @Test
    void getScoreFromDto() {
        List<CardDto> hand = TestDataUtilGameDataGen.gameTestHandDtoValue21With3Cards();
        assertEquals(21, handAndDeckService.getScoreFromDto(hand));

        List<CardDto> hand2 = TestDataUtilGameDataGen.gameTestHandDtoValue5With2Cards();
        assertEquals(5, handAndDeckService.getScoreFromDto(hand2));

        List<CardDto> hand3 = TestDataUtilGameDataGen.gameTestHandDtoValue12With2CardsDoubleAce();
        assertEquals(12, handAndDeckService.getScoreFromDto(hand3));

        List<CardDto> hand4 = TestDataUtilGameDataGen.gameTestHandDtoValue21With3CardsWithAce();
        assertEquals(21, handAndDeckService.getScoreFromDto(hand4));

        List<CardDto> hand5 = TestDataUtilGameDataGen.gameTestHandDtoValue21With3Cards();
        assertEquals(21, handAndDeckService.getScoreFromDto(hand5));
    }

    @Test
    void getScoreFromEntity() {
        List<CardEntity> hand = TestDataUtilGameDataGen.gameTestHandDtoValue21With3Cards().stream().map(cardDto -> cardMapper.destinationToSource(cardDto)).toList();
        assertEquals(21, handAndDeckService.getScoreFromEntity(hand));

        List<CardEntity> hand2 = TestDataUtilGameDataGen.gameTestHandDtoValue5With2Cards().stream().map(cardDto -> cardMapper.destinationToSource(cardDto)).toList();;
        assertEquals(5, handAndDeckService.getScoreFromEntity(hand2));

        List<CardEntity> hand3 = TestDataUtilGameDataGen.gameTestHandDtoValue12With2CardsDoubleAce().stream().map(cardDto -> cardMapper.destinationToSource(cardDto)).toList();;
        assertEquals(12, handAndDeckService.getScoreFromEntity(hand3));

        List<CardEntity> hand4 = TestDataUtilGameDataGen.gameTestHandDtoValue21With3CardsWithAce().stream().map(cardDto -> cardMapper.destinationToSource(cardDto)).toList();;
        assertEquals(21, handAndDeckService.getScoreFromEntity(hand4));

        List<CardEntity> hand5 = TestDataUtilGameDataGen.gameTestHandDtoValue21With3Cards().stream().map(cardDto -> cardMapper.destinationToSource(cardDto)).toList();;
        assertEquals(21, handAndDeckService.getScoreFromEntity(hand5));
    }
}