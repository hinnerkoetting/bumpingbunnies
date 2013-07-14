package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.media.MediaPlayer;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.factories.WallFactory;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.WorldProperties;

public class FirstWorldObjectsBuilder implements WorldObjectsBuilder {

	private final WorldProperties worldProperties = new WorldProperties();

	@Override
	public Collection<GameObject> createAllWalls(Context context) {
		MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.boing_test);
		List<GameObject> allWalls = new LinkedList<GameObject>();
		allWalls.add(WallFactory.createWall(
				(int) (0.1 * this.worldProperties.getWorldWidth()),
				(int) (0.15 * this.worldProperties.getWorldHeight()),
				(int) (0.5 * this.worldProperties.getWorldWidth()),
				(int) (0.20 * this.worldProperties.getWorldHeight())));

		allWalls.add(WallFactory.createWall(
				(int) (0.05 * this.worldProperties.getWorldWidth()),
				(int) (0.3 * this.worldProperties.getWorldHeight()),
				(int) (0.2 * this.worldProperties.getWorldWidth()),
				(int) (0.35 * this.worldProperties.getWorldHeight())));
		allWalls.add(WallFactory.createJumper(
				(int) (0.00 * this.worldProperties.getWorldWidth()),
				(int) (0.3 * this.worldProperties.getWorldHeight()),
				(int) (0.05 * this.worldProperties.getWorldWidth()),
				(int) (0.35 * this.worldProperties.getWorldHeight()), mediaPlayer));

		allWalls.add(WallFactory.createJumper(
				(int) (0.70 * this.worldProperties.getWorldWidth()),
				(int) (0.35 * this.worldProperties.getWorldHeight()),
				(int) (0.75 * this.worldProperties.getWorldWidth()),
				(int) (0.40 * this.worldProperties.getWorldHeight()), mediaPlayer));
		allWalls.add(WallFactory.createWall(
				(int) (0.75 * this.worldProperties.getWorldWidth()),
				(int) (0.35 * this.worldProperties.getWorldHeight()),
				(int) (0.85 * this.worldProperties.getWorldWidth()),
				(int) (0.40 * this.worldProperties.getWorldHeight())));

		allWalls.add(WallFactory.createIceWall(
				(int) (0.25 * this.worldProperties.getWorldWidth()),
				(int) (0.45 * this.worldProperties.getWorldHeight()),
				(int) (0.65 * this.worldProperties.getWorldWidth()),
				(int) (0.50 * this.worldProperties.getWorldHeight())));

		allWalls.add(WallFactory.createIceWall(
				(int) (0.40 * this.worldProperties.getWorldWidth()),
				(int) (0.65 * this.worldProperties.getWorldHeight()),
				(int) (0.68 * this.worldProperties.getWorldWidth()),
				(int) (0.70 * this.worldProperties.getWorldHeight())));

		allWalls.add(WallFactory.createWall(
				(int) (0.78 * this.worldProperties.getWorldWidth()),
				(int) (0.8 * this.worldProperties.getWorldHeight()),
				(1 * this.worldProperties.getWorldWidth()),
				(int) (0.85 * this.worldProperties.getWorldHeight())));

		allWalls.add(WallFactory.createWall(
				(int) (0.25 * this.worldProperties.getWorldWidth()),
				(int) (0.8 * this.worldProperties.getWorldHeight()),
				(int) (0.50 * this.worldProperties.getWorldWidth()),
				(int) (0.85 * this.worldProperties.getWorldHeight())));

		// allWalls.addAll(BuildBorderAroundWorldHelper.build());
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
