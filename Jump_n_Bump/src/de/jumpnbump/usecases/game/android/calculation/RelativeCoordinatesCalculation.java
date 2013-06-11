package de.jumpnbump.usecases.game.android.calculation;

import de.jumpnbump.usecases.game.model.Player;

public class RelativeCoordinatesCalculation implements CoordinatesCalculation {

	private final Player targetPlayer;
	private int zoom;
	private int width;
	private int height;

	public RelativeCoordinatesCalculation(Player player) {
		this.targetPlayer = player;
	}

	public void updateCanvas(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	@Override
	public int getGameCoordinateX(float touchX) {
		return (this.targetPlayer.getCenterX() / this.zoom - this.width / 2);
	}

	@Override
	public int getGameCoordinateY(float touchY) {
		return (this.targetPlayer.getCenterY() / this.zoom - this.height / 2);
	}

	@Override
	public float getScreenCoordinateX(int gameX) {
		float res = (this.width / 2 + (float) ((gameX - this.targetPlayer
				.getCenterX()) / this.zoom));
		return res;
	}

	@Override
	public float getScreenCoordinateY(int gameY) {
		float res = (this.height / 2 - (((+gameY - this.targetPlayer
				.getCenterY())) / this.zoom));
		return res;
	}

}
