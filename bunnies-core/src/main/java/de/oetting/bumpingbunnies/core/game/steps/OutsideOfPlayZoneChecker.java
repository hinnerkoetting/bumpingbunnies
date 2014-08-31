package de.oetting.bumpingbunnies.core.game.steps;

import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class OutsideOfPlayZoneChecker {

	public static boolean outsideOfGameZone(Player player) {
		return (player.getCenterY() < -ModelConstants.STANDARD_WORLD_SIZE * 0.1);
	}
}
