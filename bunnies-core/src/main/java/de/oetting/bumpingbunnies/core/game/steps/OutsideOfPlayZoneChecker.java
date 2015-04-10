package de.oetting.bumpingbunnies.core.game.steps;

import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class OutsideOfPlayZoneChecker {

	public static boolean outsideOfGameZone(Bunny player) {
		return player.getCenterY() < -ModelConstants.STANDARD_WORLD_SIZE * 0.1;
	}
}
