package de.jumpnbump.usecases.game.model.worldfactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import de.jumpnbump.usecases.game.factories.PlayerFactory;
import de.jumpnbump.usecases.game.factories.WallFactory;
import de.jumpnbump.usecases.game.model.FixedWorldObject;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.SpawnPoint;

public class FirstWorldObjectsBuilder implements WorldObjectsBuilder {

	@Override
	public List<Player> createAllPlayers(int number) {
		List<Player> allPlayers = new LinkedList<Player>();
		for (int i = 0; i < number; i++) {
			allPlayers.add(PlayerFactory.createPlayer(i));
		}
		return allPlayers;
	}

	@Override
	public Collection<FixedWorldObject> createAllWalls(Context context) {
		List<FixedWorldObject> allWalls = new LinkedList<FixedWorldObject>();
		allWalls.add(WallFactory.createWall(
				(int) (0.1 * ModelConstants.MAX_VALUE),
				(int) (0.15 * ModelConstants.MAX_VALUE),
				(int) (0.5 * ModelConstants.MAX_VALUE),
				(int) (0.20 * ModelConstants.MAX_VALUE)));

		allWalls.add(WallFactory.createWall(
				(int) (0.0 * ModelConstants.MAX_VALUE),
				(int) (0.3 * ModelConstants.MAX_VALUE),
				(int) (0.2 * ModelConstants.MAX_VALUE),
				(int) (0.35 * ModelConstants.MAX_VALUE)));

		allWalls.add(WallFactory.createWall(
				(int) (0.70 * ModelConstants.MAX_VALUE),
				(int) (0.35 * ModelConstants.MAX_VALUE),
				(int) (0.85 * ModelConstants.MAX_VALUE),
				(int) (0.40 * ModelConstants.MAX_VALUE)));
		allWalls.add(WallFactory.createIceWall(
				(int) (0.3 * ModelConstants.MAX_VALUE),
				(int) (0.40 * ModelConstants.MAX_VALUE),
				(int) (0.55 * ModelConstants.MAX_VALUE),
				(int) (0.45 * ModelConstants.MAX_VALUE)));

		allWalls.add(WallFactory.createIceWall(
				(int) (0.45 * ModelConstants.MAX_VALUE),
				(int) (0.6 * ModelConstants.MAX_VALUE),
				(int) (0.70 * ModelConstants.MAX_VALUE),
				(int) (0.65 * ModelConstants.MAX_VALUE)));

		allWalls.add(WallFactory.createWall(
				(int) (0.75 * ModelConstants.MAX_VALUE),
				(int) (0.8 * ModelConstants.MAX_VALUE),
				(1 * ModelConstants.MAX_VALUE),
				(int) (0.85 * ModelConstants.MAX_VALUE)));
		allWalls.add(WallFactory.createWall(
				(int) (0.25 * ModelConstants.MAX_VALUE),
				(int) (0.8 * ModelConstants.MAX_VALUE),
				(int) (0.50 * ModelConstants.MAX_VALUE),
				(int) (0.85 * ModelConstants.MAX_VALUE)));

		allWalls.addAll(BuildBorderAroundWorldHelper.build());
		return allWalls;
	}

	@Override
	public List<SpawnPoint> createSpawnPoints() {
		List<SpawnPoint> list = new ArrayList<SpawnPoint>(10);
		float x = 0.0f;
		for (int i = 0; i < 8; i++) {
			x += 0.1f;
			list.add(new SpawnPoint((int) (x * ModelConstants.MAX_VALUE),
					ModelConstants.MAX_VALUE));

		}
		return list;
	}
}
