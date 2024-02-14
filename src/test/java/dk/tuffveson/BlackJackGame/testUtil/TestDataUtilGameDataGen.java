package dk.tuffveson.BlackJackGame.testUtil;

import dk.tuffveson.BlackJackGame.domain.dto.CardDto;
import dk.tuffveson.BlackJackGame.domain.dto.GameDto;
import dk.tuffveson.BlackJackGame.domain.dto.enums.CardValue;
import dk.tuffveson.BlackJackGame.domain.dto.enums.Suit;

import java.util.ArrayList;
import java.util.List;

public class TestDataUtilGameDataGen {


    public static List<CardDto> fullDeck(){
        List<CardDto> cardDtoList = new ArrayList<>();
        for (CardValue cardValue : CardValue.values()) {
            for (Suit suit : Suit.values()){
                cardDtoList.add(new CardDto(cardValue,suit));
            }
        }
        return cardDtoList;
    }



    public static GameDto gameTestInstanceA(){
        List<CardDto> deck = fullDeck();
        return GameDto.builder().deck(deck).sessionId(1).ongoing(false).build();
    }

    public static GameDto gameTestInstanceNoId(){
        List<CardDto> deck = fullDeck();
        return GameDto.builder().deck(deck).ongoing(false).build();
    }

    public static List<CardDto> gameTestHandDtoValue21With3Cards(){
        List<CardDto> cardDtosList = new ArrayList<>();
        cardDtosList.add(new CardDto(CardValue.Five,Suit.Clubs));
        cardDtosList.add(new CardDto(CardValue.Ten,Suit.Clubs));
        cardDtosList.add(new CardDto(CardValue.Six,Suit.Clubs));
        return cardDtosList;
    }

    public static List<CardDto> gameTestHandDtoValue5With2Cards(){
        List<CardDto> cardDtosList = new ArrayList<>();
        cardDtosList.add(new CardDto(CardValue.Two,Suit.Clubs));
        cardDtosList.add(new CardDto(CardValue.Three,Suit.Clubs));
        return cardDtosList;
    }

    public static List<CardDto> gameTestHandDtoValue21With3CardsWithAce(){
        List<CardDto> cardDtosList = new ArrayList<>();
        cardDtosList.add(new CardDto(CardValue.Five,Suit.Clubs));
        cardDtosList.add(new CardDto(CardValue.Five,Suit.Diamonds));
        cardDtosList.add(new CardDto(CardValue.Ace,Suit.Clubs));
        return cardDtosList;
    }

    public static List<CardDto> gameTestHandDtoValue12With2CardsDoubleAce(){
        List<CardDto> cardDtosList = new ArrayList<>();
        cardDtosList.add(new CardDto(CardValue.Ace,Suit.Diamonds));
        cardDtosList.add(new CardDto(CardValue.Ace,Suit.Clubs));
        return cardDtosList;
    }

    public static List<CardDto> gameTestHandDtoValue21With3CardsDoubleAce(){
        List<CardDto> cardDtosList = new ArrayList<>();
        cardDtosList.add(new CardDto(CardValue.Ace,Suit.Diamonds));
        cardDtosList.add(new CardDto(CardValue.Ace,Suit.Clubs));
        cardDtosList.add(new CardDto(CardValue.Nine,Suit.Diamonds));
        return cardDtosList;
    }

    public static GameDto gameTestInstanceBNoSessionAssigned(){
        List<CardDto> deck = fullDeck();
        return GameDto.builder().deck(deck).ongoing(false).build();
    }

}
