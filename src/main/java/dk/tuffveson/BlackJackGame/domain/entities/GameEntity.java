package dk.tuffveson.BlackJackGame.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade= CascadeType.ALL)
    private List<CardEntity> deck;

    @OneToMany(cascade= CascadeType.ALL)
    private List<CardEntity> playerHand;

    @OneToOne(cascade= CascadeType.ALL)
    private CardEntity dealHandHidden;

    @OneToMany(cascade= CascadeType.ALL)
    private List<CardEntity> dealerHandShown;

    private Boolean ongoing;

}
