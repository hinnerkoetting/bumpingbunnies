package de.oetting.bumpingbunnies.usecases.game;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface ObjectProvider {

	public List<GameObject> getAllObjects();

	List<Player> getAllPlayer();
}
