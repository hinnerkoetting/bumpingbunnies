package de.jumpnbump.usecases.game;

import java.util.List;

import de.jumpnbump.usecases.game.model.GameObject;

public interface ObjectProvider {

	public List<GameObject> getAllObjects();
}
