package dk.tuffveson.BlackJackGame.mappers.impl;

import dk.tuffveson.BlackJackGame.domain.dto.CardDto;
import dk.tuffveson.BlackJackGame.domain.dto.enums.CardValue;
import dk.tuffveson.BlackJackGame.domain.dto.enums.Suit;
import dk.tuffveson.BlackJackGame.domain.entities.CardEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CardMapperTest {
    @Autowired
    CardMapper cardMapper;

    @Test
    void sourceToDestination() {

    }

    @Test
    void destinationToSource() {
        assertEquals(new CardEntity(null,3,0),cardMapper.destinationToSource(new CardDto(CardValue.Five, Suit.Clubs)));
    }
}