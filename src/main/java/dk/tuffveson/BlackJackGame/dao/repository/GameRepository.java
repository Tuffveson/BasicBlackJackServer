package dk.tuffveson.BlackJackGame.dao.repository;

import dk.tuffveson.BlackJackGame.domain.entities.GameEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, Integer> {
}
