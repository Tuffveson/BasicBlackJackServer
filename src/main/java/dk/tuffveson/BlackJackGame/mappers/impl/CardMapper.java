package dk.tuffveson.BlackJackGame.mappers.impl;

import dk.tuffveson.BlackJackGame.domain.entities.CardEntity;
import dk.tuffveson.BlackJackGame.domain.dto.CardDto;
import dk.tuffveson.BlackJackGame.domain.dto.enums.CardValue;
import dk.tuffveson.BlackJackGame.domain.dto.enums.Suit;
import dk.tuffveson.BlackJackGame.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CardMapper implements Mapper<CardDto, CardEntity> {


    @Override
    public CardDto sourceToDestination(CardEntity cardEntity) {

        return CardDto.builder().value(CardValue.values()[cardEntity.getFaceValue()]).suit(Suit.values()[cardEntity.getSuit()]).build();
    }

    @Override
    public CardEntity destinationToSource(CardDto cardDto) {
        return CardEntity.builder().faceValue(cardDto.getValue().ordinal()).suit(cardDto.getSuit().ordinal()).build();
    }
}
