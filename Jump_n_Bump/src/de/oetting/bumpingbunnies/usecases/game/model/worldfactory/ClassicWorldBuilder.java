package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.media.MediaPlayer;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.factories.WallFactory;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;
import de.oetting.bumpingbunnies.usecases.game.model.WorldProperties;

public class ClassicWorldBuilder implements WorldObjectsBuilder {

	private final WorldProperties worldProperties = new WorldProperties();

	@Override
	public Collection<GameObject> createAllWalls(Context context) {
		Collection<GameObject> allWalls = new ArrayList<GameObject>();
		addLeftWall(allWalls);
		addRightWall(allWalls);
		addLowestRow(allWalls, context);
		addSecondRow(allWalls);
		addThirdRow(allWalls);
		addFourthRow(allWalls);
		addFifthRow(allWalls);
		allWalls.addAll(BuildBorderAroundWorldHelper.build(new WorldProperties()));
		// allWalls.add(WallFactory.createWall(0, 0.2, 0.4, 0.1));
		// allWalls.add(WallFactory.createWall(0.4, 0.2, 0.3, 0.05));
		// allWalls.add(WallFactory.createWall(0.7, 0.2, 0.2, 0.05));
		// allWalls.add(WallFactory.createWall(0.9, 0.2, 0.3, 0.1));
		return allWalls;
	}

	private void addLeftWall(Collection<GameObject> allWalls) {
		allWalls.add(convenienceBuildWall(0, 10, 5, 80));
	}

	private void addRightWall(Collection<GameObject> allWalls) {
		allWalls.add(convenienceBuildWall(105, 0, 110, 65));
	}

	private void addLowestRow(Collection<GameObject> allWalls, Context context) {
		allWalls.add(convenienceBuildWall(5, 10, 10, 15));
		MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.boing_test);
		allWalls.add(convenienceBuildWater(0, 0, 40, 10));
		allWalls.add(convenienceBuildJumper(40, 0, 45, 10, mediaPlayer));
		// allWalls.add(convenienceBuildWall(40, 0, 50, 10));
		allWalls.add(convenienceBuildWall(45, 0, 50, 10));
		allWalls.add(convenienceBuildWall(50, 0, 75, 5));
		allWalls.add(convenienceBuildWall(75, 0, 80, 10));
		allWalls.add(convenienceBuildIcyWall(80, 0, 95, 10));
		allWalls.add(convenienceBuildWall(95, 0, 100, 10));
		allWalls.add(convenienceBuildWall(100, 0, 105, 15));
	}

	private void addSecondRow(Collection<GameObject> allWalls) {
		allWalls.add(convenienceBuildWall(10, 20, 30, 25));
		allWalls.add(convenienceBuildIcyWall(50, 20, 55, 25));
		allWalls.add(convenienceBuildWall(55, 20, 95, 25));
		allWalls.add(convenienceBuildIcyWall(55, 25, 60, 30));
		allWalls.add(convenienceBuildWall(60, 25, 75, 30));
		allWalls.add(convenienceBuildIcyWall(60, 30, 65, 35));
		allWalls.add(convenienceBuildWall(65, 30, 70, 35));
	}

	private void addThirdRow(Collection<GameObject> allWalls) {
		allWalls.add(convenienceBuildWall(5, 35, 15, 40));
		allWalls.add(convenienceBuildWall(25, 35, 40, 40));
		allWalls.add(convenienceBuildWall(70, 45, 90, 50));
		allWalls.add(convenienceBuildWall(95, 35, 105, 40));
		allWalls.add(convenienceBuildWall(100, 40, 105, 45));
	}

	private void addFourthRow(Collection<GameObject> allWalls) {
		allWalls.add(convenienceBuildWall(5, 55, 10, 60));
		allWalls.add(convenienceBuildWall(5, 50, 15, 55));

		allWalls.add(convenienceBuildWall(30, 50, 60, 55));
		allWalls.add(convenienceBuildWall(50, 55, 65, 60));
		allWalls.add(convenienceBuildWall(55, 60, 75, 65));
		allWalls.add(convenienceBuildWall(60, 65, 70, 70));
		allWalls.add(convenienceBuildWall(60, 70, 65, 75));
	}

	private void addFifthRow(Collection<GameObject> allWalls) {
		allWalls.add(convenienceBuildWall(5, 75, 15, 80));
		allWalls.add(convenienceBuildWall(20, 65, 40, 70));
		allWalls.add(convenienceBuildWall(85, 70, 95, 75));
		allWalls.add(convenienceBuildWall(100, 60, 105, 65));
	}

	/**
	 * Coordinates from x = 0.. 110, y = 0..80 for easier building from original
	 * jjumpn bump
	 */
	private Wall convenienceBuildWall(long x, long y, long maxX, long maxY) {
		return WallFactory.createWall(
				(long) (x * this.worldProperties.getWorldWidth() / 110.0), (long) (y
						* this.worldProperties.getWorldHeight() / 80.0), (long) (maxX
						* this.worldProperties.getWorldWidth() / 110.0), (long) (maxY
						* this.worldProperties.getWorldHeight() / 80.0));
	}

	private Wall convenienceBuildIcyWall(long x, long y, long maxX, long maxY) {
		return WallFactory.createIceWall(
				(long) (x * this.worldProperties.getWorldWidth() / 110.0), (long) (y
						* this.worldProperties.getWorldHeight() / 80.0), (long) (maxX
						* this.worldProperties.getWorldWidth() / 110.0), (long) (maxY
						* this.worldProperties.getWorldHeight() / 80.0));
	}

	private Water convenienceBuildWater(long x, long y, long maxX, long maxY) {
		return WallFactory.createWater(
				(long) (x * this.worldProperties.getWorldWidth() / 110.0), (long) (y
						* this.worldProperties.getWorldHeight() / 80.0), (long) (maxX
						* this.worldProperties.getWorldWidth() / 110.0), (long) (maxY
						* this.worldProperties.getWorldHeight() / 80.0));
	}

	private Jumper convenienceBuildJumper(long x, long y, long maxX, long maxY,
			MediaPlayer mediaPlayer) {
		return WallFactory.createJumper(
				(long) (x * this.worldProperties.getWorldWidth() / 110.0), (long) (y
						* this.worldProperties.getWorldHeight() / 80.0), (long) (maxX
						* this.worldProperties.getWorldWidth() / 110.0), (long) (maxY
						* this.worldProperties.getWorldHeight() / 80.0), mediaPlayer);
	}

	@Override
	public List<SpawnPoint> createSpawnPoints() {
		return Arrays.asList(convenicenceCreateSpawnPoint(10, 45),
				convenicenceCreateSpawnPoint(80, 35),
				convenicenceCreateSpawnPoint(20, 80),
				convenicenceCreateSpawnPoint(60, 80),
				convenicenceCreateSpawnPoint(80, 15),
				convenicenceCreateSpawnPoint(15, 20),
				convenicenceCreateSpawnPoint(70, 40),
				convenicenceCreateSpawnPoint(90, 80));
	}

	private SpawnPoint convenicenceCreateSpawnPoint(long x, long y) {
		return new SpawnPoint((long) (x * this.worldProperties.getWorldWidth() / 110.0),
				(long) (y * this.worldProperties.getWorldHeight() / 80.0));
	}

}
