package dk.tuffveson.BlackJackGame.controllers;

import dk.tuffveson.BlackJackGame.domain.dto.EndStateDto;
import dk.tuffveson.BlackJackGame.domain.dto.GameDto;
import dk.tuffveson.BlackJackGame.mappers.impl.GameMapper;
import dk.tuffveson.BlackJackGame.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    GameMapper gameMapper;

    @PostMapping(value = "/newGame")
    public GameDto createNewGame(){
        return gameService.createNewGame();
    }

    @PostMapping(value = "/blackJackHit")
    public Optional<GameDto> hitCard(@RequestBody String sessionId) throws IOException {
        return gameService.playerDrawCardBySessionId(sessionId);
    }

    @PostMapping(value =  "/endGame")
    public Optional<EndStateDto> endGame(@RequestBody String sessionId) throws IOException {
        return gameService.resolveGameEnd(sessionId);
    }

    @PostMapping(value = "/dealCards")
    public Optional<GameDto> dealCards(@RequestBody String sessionId) throws IOException {
        return gameService.dealCards(sessionId);
    }

}
