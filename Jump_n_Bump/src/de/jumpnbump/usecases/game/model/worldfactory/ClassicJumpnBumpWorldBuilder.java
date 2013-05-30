package de.jumpnbump.usecases.game.model.worldfactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.jumpnbump.usecases.game.factories.PlayerFactory;
import de.jumpnbump.usecases.game.factories.WallFactory;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.Wall;

public class ClassicJumpnBumpWorldBuilder implements WorldObjectsBuilder {

	@Override
	public List<Player> createAllPlayers() {
		List<Player> allPlayers = new LinkedList<Player>();
		Player p1 = PlayerFactory.createPlayer1();
		allPlayers.add(p1);

		Player p2 = PlayerFactory.createPlayer2();

		allPlayers.add(p2);
		return allPlayers;
	}

	@Override
	public Collection<Wall> createAllWalls() {
		Collection<Wall> allWalls = new ArrayList<Wall>();
		addLeftWall(allWalls);
		addRightWall(allWalls);
		addLowestRow(allWalls);
		addSecondRow(allWalls);
		addThirdRow(allWalls);
		addFourthRow(allWalls);
		addFifthRow(allWalls);
		// allWalls.add(WallFactory.createWall(0, 0.2, 0.4, 0.1));
		// allWalls.add(WallFactory.createWall(0.4, 0.2, 0.3, 0.05));
		// allWalls.add(WallFactory.createWall(0.7, 0.2, 0.2, 0.05));
		// allWalls.add(WallFactory.createWall(0.9, 0.2, 0.3, 0.1));
		return allWalls;
	}

	private void addLeftWall(Collection<Wall> allWalls) {
		allWalls.add(convenienceBuildWall(0, 10, 5, 80));
	}

	private void addRightWall(Collection<Wall> allWalls) {
		allWalls.add(convenienceBuildWall(105, 0, 110, 65));
	}

	private void addLowestRow(Collection<Wall> allWalls) {
		allWalls.add(convenienceBuildWall(5, 10, 10, 15));
		allWalls.add(convenienceBuildWall(40, 0, 50, 10));
		allWalls.add(convenienceBuildWall(50, 0, 75, 5));
		allWalls.add(convenienceBuildWall(75, 0, 100, 10));
		allWalls.add(convenienceBuildWall(100, 0, 105, 15));
	}

	private void addSecondRow(Collection<Wall> allWalls) {
		allWalls.add(convenienceBuildWall(10, 20, 30, 25));
		allWalls.add(convenienceBuildWall(50, 20, 95, 25));
		allWalls.add(convenienceBuildWall(55, 25, 75, 30));
		allWalls.add(convenienceBuildWall(60, 30, 70, 35));
	}

	private void addThirdRow(Collection<Wall> allWalls) {
		allWalls.add(convenienceBuildWall(5, 35, 15, 40));
		allWalls.add(convenienceBuildWall(25, 35, 40, 40));
		allWalls.add(convenienceBuildWall(70, 45, 90, 50));
		allWalls.add(convenienceBuildWall(95, 35, 105, 40));
		allWalls.add(convenienceBuildWall(100, 40, 105, 45));
	}

	private void addFourthRow(Collection<Wall> allWalls) {
		allWalls.add(convenienceBuildWall(5, 55, 10, 60));
		allWalls.add(convenienceBuildWall(5, 50, 15, 55));

		allWalls.add(convenienceBuildWall(30, 50, 60, 55));
		allWalls.add(convenienceBuildWall(50, 55, 65, 60));
		allWalls.add(convenienceBuildWall(55, 60, 75, 65));
		allWalls.add(convenienceBuildWall(60, 65, 70, 70));
		allWalls.add(convenienceBuildWall(60, 70, 65, 75));
	}

	private void addFifthRow(Collection<Wall> allWalls) {
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
		return WallFactory.createWall(x * ModelConstants.MAX_VALUE / 110, y
				* ModelConstants.MAX_VALUE / 80, maxX
				* ModelConstants.MAX_VALUE / 110, maxY
				* ModelConstants.MAX_VALUE / 80);
	}

}
