package de.jumpnbump.usecases.game.graphics;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.Player;

public class CanvasAroundPlayerDelegate extends AbstractCanvasDelegate {

	private static final MyLog LOGGER = Logger
			.getLogger(CanvasAroundPlayerDelegate.class);

	private final Player targetPlayer;
	private double zoom;

	public CanvasAroundPlayerDelegate(Player targetPlayer) {
		this.targetPlayer = targetPlayer;
		this.zoom = 1;
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	@Override
	public float transformX(double x) {
		float res = this.getWidth() / 2
				+ (float) ((x - this.targetPlayer.getCenterX()) / this.zoom);
		return res;
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	@Override
	public float transformY(double y) {
		float res = this.getHeight() / 2
				- (float) (((+y - this.targetPlayer.getCenterY())) / this.zoom);
		return res;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

}
