package de.oetting.bumpingbunnies.model.game.world;

import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;

public interface ObjectProvider {

	List<GameObjectWithImage> getAllObjects();

	List<Jumper> getAllJumper();

	List<Wall> getAllWalls();

	List<IcyWall> getAllIcyWalls();

	List<Bunny> getAllConnectedBunnies();

	List<Water> getAllWaters();
}
