package dk.tuffveson.BlackJackGame.controllers;

import dk.tuffveson.BlackJackGame.domain.dto.GameDto;
import dk.tuffveson.BlackJackGame.services.GameService;
import dk.tuffveson.BlackJackGame.testUtil.TestDataUtilGameDataGen;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {
    @MockBean
    GameService gameServiceMock;
    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
        //TODO:TEST CONTEXT
    }

    @Test
    void setNewGameTest() throws Exception {

        GameDto gameEntityTestInstanceA = TestDataUtilGameDataGen.gameTestInstanceA();
        Mockito.when(gameServiceMock.createNewGame()).thenReturn(gameEntityTestInstanceA);
        mvc.perform(MockMvcRequestBuilders
                .post("/newGame").accept(MediaType.APPLICATION_JSON))
                                    .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.sessionId").exists());
    }


}
