package de.oetting.bumpingbunnies.model.game.world;

import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.GameObject;
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

	List<? extends GameObject> getCandidateForCollisionObjects(Bunny bunny);
	
	List<? extends GameObject> getCandidateForCollisionObjects(int segment);

	List<Jumper> getCandidateForCollisionJumper(int segment);

	List<Wall> getCandidateForCollisionWalls(int segment);

	List<IcyWall> getCandidateForCollisionIcyWalls(int segment);

	List<Water> getCandidateForCollisionWater(int segment);
	
	//Index of segment that bunny belongs to. -1 if segment does not exist
	int getSegmentThatBunnyBelongsTo(Bunny bunny);
}
