package dk.tuffveson.BlackJackGame.services.impl;

import dk.tuffveson.BlackJackGame.domain.entities.CardEntity;
import dk.tuffveson.BlackJackGame.domain.entities.GameEntity;
import dk.tuffveson.BlackJackGame.services.DealerAiService;
import dk.tuffveson.BlackJackGame.services.HandAndDeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DealerServiceImp implements DealerAiService {

    @Autowired
    HandAndDeckService handAndDeckService;


    private boolean shouldDrawCardEvaluator(List<CardEntity> hand){
        boolean handScoreIsLessThan17 = handAndDeckService.getScoreFromEntity(hand) < 17;
        boolean hardHandScoreIsLessThan18 = handAndDeckService.getHardScoreFromEntity(hand) < 18;
        boolean handContainsAce = handAndDeckService.containsAce(hand);

        if (handScoreIsLessThan17){
            return true;
        }
        else if (hardHandScoreIsLessThan18 && handContainsAce) {
            return true;
        }
        else return false;
    }

    @Override
    public GameEntity makeMove(GameEntity game) {
        List<CardEntity> hand = game.getDealerHandShown();
        while(shouldDrawCardEvaluator(hand)){
            game = handAndDeckService.dealerDrawCardShown(game);
            hand = game.getDealerHandShown();
        }
        return game;
    }
}
