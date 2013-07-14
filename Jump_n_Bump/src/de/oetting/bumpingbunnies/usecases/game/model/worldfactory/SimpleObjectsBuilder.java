package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.factories.WallFactory;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.WorldProperties;

public class SimpleObjectsBuilder implements WorldObjectsBuilder {

	private final WorldProperties worldProperties = new WorldProperties();

	@Override
	public Collection<GameObject> createAllWalls(Context context) {
		List<GameObject> allWalls = new LinkedList<GameObject>();
		allWalls.add(WallFactory.createWall(0,
				(int) (0.25 * this.worldProperties.getWorldHeight()),
				(this.worldProperties.getWorldWidth()),
				(int) (0.30 * this.worldProperties.getWorldHeight())));

		allWalls.add(WallFactory.createIceWall(
				(int) (0.1 * this.worldProperties.getWorldWidth()),
				(int) (0.40 * this.worldProperties.getWorldHeight()),
				(int) (this.worldProperties.getWorldWidth() * 0.4),
				(int) (0.45 * this.worldProperties.getWorldHeight())));
		allWalls.add(WallFactory.createIceWall(
				(int) (0.60 * this.worldProperties.getWorldWidth()),
				(int) (0.40 * this.worldProperties.getWorldHeight()),
				(int) (0.9 * this.worldProperties.getWorldWidth()),
				(int) (0.45 * this.worldProperties.getWorldHeight())));

		allWalls.addAll(BuildBorderAroundWorldHelper.build(new WorldProperties()));
		return allWalls;
	}

	@Override
	public List<SpawnPoint> createSpawnPoints() {
		List<SpawnPoint> list = new ArrayList<SpawnPoint>(10);
		float x = 0.0f;
		for (int i = 0; i < 8; i++) {
			x += 0.1f;
			list.add(new SpawnPoint((int) (x * this.worldProperties.getWorldWidth()),
					this.worldProperties.getWorldHeight()));

		}
		return list;
	}
}
