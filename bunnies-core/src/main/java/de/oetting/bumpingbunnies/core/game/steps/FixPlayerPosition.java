package de.oetting.bumpingbunnies.core.game.steps;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.SingleCollisionDetection;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;

/**
 * Because player states can be send over network it sometimes comes to
 * collision between players and they could move through each other. To fix this
 * if a collision is detected we move each player backwards until no collision
 * exists anymore.
 *
 */
public class FixPlayerPosition implements PlayerJoinListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(FixPlayerPosition.class);

	private final List<Player> players = new CopyOnWriteArrayList<Player>();

	private final CollisionDetection collisionDetection;

	public FixPlayerPosition(CollisionDetection collisionDetection) {
		this.collisionDetection = collisionDetection;
	}

	public void movePlayerBackwards() {
		for (Player player1 : players)
			for (Player player2 : players)
				if (player1 != player2 && collides(player1, player2))
					fixCollision(player1, player2);
	}

	private boolean collides(Player player1, Player player2) {
		return SingleCollisionDetection.collides(player1, player2);
	}

	private void fixCollision(Player player1, Player player2) {
		LOGGER.info("Collision between players %s and %s. Fixing this by moving them backwards.", player1.getName(),
				player2.getName());
		int maxTries = 10; // limit tries to avoid endless loop which might
							// happen otherwise if players move at the same
							// speed
		// can this cause problems because a player might be moved into a wall?
		while (collides(player1, player2) && maxTries-- > 0) {
			tryToFixCollisionOneIteration(player1, player2);
		}
	}

	private void tryToFixCollisionOneIteration(Player player1, Player player2) {
		player1.moveBackwards();
		player2.moveBackwards();
		moveForwardsIfPlayerCollidesWithAnyFixedObject(player1);
		moveForwardsIfPlayerCollidesWithAnyFixedObject(player2);
	}

	private boolean collidesWithFixedObject(Player player) {
		return collisionDetection.collidesWithAnyFixedObjec(player);
	}

	private void moveForwardsIfPlayerCollidesWithAnyFixedObject(Player player) {
		if (collidesWithFixedObject(player)) {
			player.moveNextStep();
		}
	}

	@Override
	public void newEvent(Player object) {
		players.add(object);
	}

	@Override
	public void removeEvent(Player object) {
		players.remove(object);
	}
}
