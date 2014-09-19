package de.oetting.bumpingbunnies.core.world;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.model.GameObjectWithImage;
import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;

public interface ObjectProvider {

	List<GameObjectWithImage> getAllObjects();

	List<Jumper> getAllJumper();

	List<Wall> getAllWalls();

	List<IcyWall> getAllIcyWalls();

	List<Player> getAllPlayer();

	List<Water> getAllWaters();
}
