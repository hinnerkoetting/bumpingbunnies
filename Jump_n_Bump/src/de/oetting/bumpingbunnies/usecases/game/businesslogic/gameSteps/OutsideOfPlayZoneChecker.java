package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class OutsideOfPlayZoneChecker {

	public static boolean outsideOfGameZone(Player player) {
		return (player.getCenterY() < -ModelConstants.MAX_VALUE * 0.1);
	}
}
