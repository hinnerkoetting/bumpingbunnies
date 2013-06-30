package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.media.MediaPlayer;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.factories.PlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.WallFactory;
import de.oetting.bumpingbunnies.usecases.game.model.FixedWorldObject;
import de.oetting.bumpingbunnies.usecases.game.model.Jumper;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;

public class ClassicWorldBuilder implements WorldObjectsBuilder {

	@Override
	public List<Player> createAllPlayers(int number) {
		return PlayerFactory.createAllPlayers(number);
	}

	@Override
	public Collection<FixedWorldObject> createAllWalls(Context context) {
		Collection<FixedWorldObject> allWalls = new ArrayList<FixedWorldObject>();
		addLeftWall(allWalls);
		addRightWall(allWalls);
		addLowestRow(allWalls, context);
		addSecondRow(allWalls);
		addThirdRow(allWalls);
		addFourthRow(allWalls);
		addFifthRow(allWalls);
		allWalls.addAll(BuildBorderAroundWorldHelper.build());
		// allWalls.add(WallFactory.createWall(0, 0.2, 0.4, 0.1));
		// allWalls.add(WallFactory.createWall(0.4, 0.2, 0.3, 0.05));
		// allWalls.add(WallFactory.createWall(0.7, 0.2, 0.2, 0.05));
		// allWalls.add(WallFactory.createWall(0.9, 0.2, 0.3, 0.1));
		return allWalls;
	}

	private void addLeftWall(Collection<FixedWorldObject> allWalls) {
		allWalls.add(convenienceBuildWall(0, 10, 5, 80));
	}

	private void addRightWall(Collection<FixedWorldObject> allWalls) {
		allWalls.add(convenienceBuildWall(105, 0, 110, 65));
	}

	private void addLowestRow(Collection<FixedWorldObject> allWalls,
			Context context) {
		allWalls.add(convenienceBuildWall(5, 10, 10, 15));
		MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.boing_test);
		allWalls.add(convenienceBuildJumper(40, 0, 45, 10, mediaPlayer));
		// allWalls.add(convenienceBuildWall(40, 0, 50, 10));
		allWalls.add(convenienceBuildWall(45, 0, 50, 10));
		allWalls.add(convenienceBuildWall(50, 0, 75, 5));
		allWalls.add(convenienceBuildWall(75, 0, 80, 10));
		allWalls.add(convenienceBuildIcyWall(80, 0, 95, 10));
		allWalls.add(convenienceBuildWall(95, 0, 100, 10));
		allWalls.add(convenienceBuildWall(100, 0, 105, 15));
	}

	private void addSecondRow(Collection<FixedWorldObject> allWalls) {
		allWalls.add(convenienceBuildWall(10, 20, 30, 25));
		allWalls.add(convenienceBuildIcyWall(50, 20, 55, 25));
		allWalls.add(convenienceBuildWall(55, 20, 95, 25));
		allWalls.add(convenienceBuildIcyWall(55, 25, 60, 30));
		allWalls.add(convenienceBuildWall(60, 25, 75, 30));
		allWalls.add(convenienceBuildIcyWall(60, 30, 65, 35));
		allWalls.add(convenienceBuildWall(65, 30, 70, 35));
	}

	private void addThirdRow(Collection<FixedWorldObject> allWalls) {
		allWalls.add(convenienceBuildWall(5, 35, 15, 40));
		allWalls.add(convenienceBuildWall(25, 35, 40, 40));
		allWalls.add(convenienceBuildWall(70, 45, 90, 50));
		allWalls.add(convenienceBuildWall(95, 35, 105, 40));
		allWalls.add(convenienceBuildWall(100, 40, 105, 45));
	}

	private void addFourthRow(Collection<FixedWorldObject> allWalls) {
		allWalls.add(convenienceBuildWall(5, 55, 10, 60));
		allWalls.add(convenienceBuildWall(5, 50, 15, 55));

		allWalls.add(convenienceBuildWall(30, 50, 60, 55));
		allWalls.add(convenienceBuildWall(50, 55, 65, 60));
		allWalls.add(convenienceBuildWall(55, 60, 75, 65));
		allWalls.add(convenienceBuildWall(60, 65, 70, 70));
		allWalls.add(convenienceBuildWall(60, 70, 65, 75));
	}

	private void addFifthRow(Collection<FixedWorldObject> allWalls) {
		allWalls.add(convenienceBuildWall(5, 75, 15, 80));
		allWalls.add(convenienceBuildWall(20, 65, 40, 70));
		allWalls.add(convenienceBuildWall(85, 70, 95, 75));
		allWalls.add(convenienceBuildWall(100, 60, 105, 65));
	}

	/**
	 * Coordinates from x = 0.. 110, y = 0..80 for easier building from original
	 * jjumpn bump
	 */
	private Wall convenienceBuildWall(int x, int y, int maxX, int maxY) {
		return WallFactory.createWall(
				(int) (x * ModelConstants.MAX_VALUE / 110.0), (int) (y
						* ModelConstants.MAX_VALUE / 80.0), (int) (maxX
						* ModelConstants.MAX_VALUE / 110.0), (int) (maxY
						* ModelConstants.MAX_VALUE / 80.0));
	}

	private Wall convenienceBuildIcyWall(int x, int y, int maxX, int maxY) {
		return WallFactory.createIceWall(
				(int) (x * ModelConstants.MAX_VALUE / 110.0), (int) (y
						* ModelConstants.MAX_VALUE / 80.0), (int) (maxX
						* ModelConstants.MAX_VALUE / 110.0), (int) (maxY
						* ModelConstants.MAX_VALUE / 80.0));
	}

	private Jumper convenienceBuildJumper(int x, int y, int maxX, int maxY,
			MediaPlayer mediaPlayer) {
		return WallFactory.createJumper(
				(int) (x * ModelConstants.MAX_VALUE / 110.0), (int) (y
						* ModelConstants.MAX_VALUE / 80.0), (int) (maxX
						* ModelConstants.MAX_VALUE / 110.0), (int) (maxY
						* ModelConstants.MAX_VALUE / 80.0), mediaPlayer);
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

	private SpawnPoint convenicenceCreateSpawnPoint(int x, int y) {
		return new SpawnPoint((int) (x * ModelConstants.MAX_VALUE / 110.0),
				(int) (y * ModelConstants.MAX_VALUE / 80.0));
	}

}
