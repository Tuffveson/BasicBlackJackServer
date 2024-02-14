package dk.tuffveson.BlackJackGame.services;

import dk.tuffveson.BlackJackGame.domain.dto.CardDto;
import dk.tuffveson.BlackJackGame.domain.dto.enums.CardValue;
import dk.tuffveson.BlackJackGame.domain.dto.enums.Suit;
import dk.tuffveson.BlackJackGame.domain.entities.CardEntity;

import java.util.ArrayList;
import java.util.List;

public interface CardService {



    static int getValue(CardEntity card){
        Integer face = card.getFaceValue()+2;
        if (face==14) {
            face = 11;
        } else if (face>10) {
            face = 10;
        }
        return face;
    };

    static List<CardDto> fullDeckDto(){
        List<CardDto> cardDtoList = new ArrayList<>();
        for (CardValue cardValue : CardValue.values()) {
            for (Suit suit : Suit.values()){
                cardDtoList.add(new CardDto(cardValue,suit));
            }
        }
        return cardDtoList;
    }
    static List<CardEntity> fullDeckEntity(){
        List<CardEntity> cardEntityList = new ArrayList<>();
        for (CardValue cardValue : CardValue.values()) {
            for (Suit suit : Suit.values()){
                cardEntityList.add(new CardEntity(null,cardValue.ordinal(),suit.ordinal()));
            }
        }
        return cardEntityList;
    }

}
