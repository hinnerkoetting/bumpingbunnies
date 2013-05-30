package de.jumpnbump.usecases.game;

import java.util.List;

import de.jumpnbump.usecases.game.model.GameObject;
import de.jumpnbump.usecases.game.model.Player;

public interface ObjectProvider {

	public List<GameObject> getAllObjects();

	List<Player> getAllPlayer();
}
