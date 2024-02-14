package dk.tuffveson.BlackJackGame.services.impl;

import dk.tuffveson.BlackJackGame.domain.dto.CardDto;
import dk.tuffveson.BlackJackGame.domain.entities.CardEntity;
import dk.tuffveson.BlackJackGame.domain.entities.GameEntity;
import dk.tuffveson.BlackJackGame.mappers.impl.CardMapper;
import dk.tuffveson.BlackJackGame.services.CardService;
import dk.tuffveson.BlackJackGame.services.HandAndDeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Collections.singletonList;

@Component
public class HandAndDeckServiceImpl implements HandAndDeckService {
    Random randomGenerator = new Random();
    @Autowired
    CardMapper cardMapper;
    @Override
    public int getScoreFromEntity(List<CardEntity> hand) {
        int score=0;
        int aceCount=0;
        for(CardEntity card: hand){
            int value = CardService.getValue(card);
            if(value==11){
                aceCount++;
            }
            else score=score+value;
        }
        for (int i = 0; i < aceCount; i++) {
            if(21-score>=10+aceCount){
                score=score+10;
            }
        }
        score=score+aceCount;
        return score;
    }
    //This is a helper method that allows us to write the primary draw card more generally
    private List<CardEntity> getHandByTarget(GameEntity game,int target){
        return switch (target) {
            case 0 -> game.getPlayerHand();
            case 1 -> (game.getDealHandHidden() == null) ? new ArrayList<>() : singletonList(game.getDealHandHidden());
            case 2 -> game.getDealerHandShown();
            default -> null;
        };
    }
    //This is a helper method that allows us to write the primary draw card more generally, we have kept the target setup definition
    private GameEntity setHandByTarget(GameEntity game,int target, List<CardEntity> hand){
        return switch (target) {
            case 0 -> {
                game.setPlayerHand(hand);
                yield game;
            }
            case 1 -> {
                game.setDealHandHidden(hand.getFirst());
                yield game;
            }
            case 2 -> {
                game.setDealerHandShown(hand);
                yield game;
            }
            default -> game;
        };
    }

    //0 is player, 1 is dealer but kept hidden and 2 is dealer which is shown

    @Override
    public GameEntity drawCard(GameEntity game, int target) {

        //remove random card from deck we need to ensure the list becomes an arraylist so that we can remove and add to it
        List<CardEntity> deck = new ArrayList<>(game.getDeck());
        int removeIndex = randomGenerator.nextInt(deck.size());
        CardEntity cardDrawn = deck.remove(removeIndex);
        game.setDeck(deck);
        //if the hand we draw to has no cards we need to instantiate the hand
        List<CardEntity> originalHand = getHandByTarget(game,target);
        List<CardEntity> newHand;
        if (originalHand != null){
            newHand = new ArrayList<>(originalHand);
        }
        else {
            newHand = new ArrayList<>();
        }
        //Add card to the target hand
        newHand.add(cardDrawn);
        game = setHandByTarget(game,target,newHand);
        return game;
    }

    @Override
    public int getHardScoreFromEntity(List<CardEntity> hand) {
        int score=0;
        for(CardEntity card: hand){
            score=score+CardService.getValue(card);
        }
        return score;
    }

    @Override
    public boolean containsAce(List<CardEntity> hand) {
        for(CardEntity card: hand){
            if(CardService.getValue(card)==11){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getScoreFromDto(List<CardDto> hand) {
        return getScoreFromEntity(hand.stream().map(cardDto -> cardMapper.destinationToSource(cardDto)).toList());
    }
}
