package de.jumpnbump.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.List;

import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.Wall;

public interface WorldObjectsBuilder {

	abstract List<Player> createAllPlayers();

	abstract Collection<Wall> createAllWalls();

}