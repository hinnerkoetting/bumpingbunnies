package de.jumpnbump.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.List;

import de.jumpnbump.usecases.game.model.FixedWorldObject;
import de.jumpnbump.usecases.game.model.Player;

public interface WorldObjectsBuilder {

	abstract List<Player> createAllPlayers();

	abstract Collection<FixedWorldObject> createAllWalls();

}