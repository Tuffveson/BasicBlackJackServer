package dk.tuffveson.BlackJackGame.services;

import dk.tuffveson.BlackJackGame.domain.dto.CardDto;
import dk.tuffveson.BlackJackGame.domain.dto.enums.CardValue;
import dk.tuffveson.BlackJackGame.domain.dto.enums.Suit;
import dk.tuffveson.BlackJackGame.mappers.impl.CardMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CardServiceTest {
    @Autowired
    CardMapper cardMapper;

    @Test
    void getValue() {
        assertEquals(2,CardService.getValue(cardMapper.destinationToSource( new CardDto(CardValue.Two, Suit.Clubs))));
        assertEquals(11,CardService.getValue(cardMapper.destinationToSource( new CardDto(CardValue.Ace, Suit.Clubs))));
        assertEquals(10,CardService.getValue(cardMapper.destinationToSource( new CardDto(CardValue.King, Suit.Clubs))));
        assertEquals(10,CardService.getValue(cardMapper.destinationToSource( new CardDto(CardValue.Jack, Suit.Clubs))));
        assertEquals(10,CardService.getValue(cardMapper.destinationToSource( new CardDto(CardValue.Ten, Suit.Clubs))));
        assertEquals(9,CardService.getValue(cardMapper.destinationToSource( new CardDto(CardValue.Nine, Suit.Clubs))));
    }
}