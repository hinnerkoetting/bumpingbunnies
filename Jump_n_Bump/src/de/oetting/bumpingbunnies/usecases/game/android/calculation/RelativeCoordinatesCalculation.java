package de.oetting.bumpingbunnies.usecases.game.android.calculation;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class RelativeCoordinatesCalculation implements CoordinatesCalculation {

	private final Player targetPlayer;
	private int zoom;
	private int width;
	private int height;

	public RelativeCoordinatesCalculation(Player player) {
		this.targetPlayer = player;
	}

	@Override
	public void updateCanvas(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	@Override
	public int getGameCoordinateX(float touchX) {
		return (int) (this.zoom * touchX + (this.targetPlayer.getCenterX() - this.width
				/ 2 * this.zoom));
	}

	@Override
	public int getGameCoordinateY(float touchY) {
		return (int) (-(touchY - this.height / 2) * this.zoom + this.targetPlayer
				.getCenterY());
	}

	@Override
	public float getScreenCoordinateX(int gameX) {
		float res = this.width
				/ 2
				+ (float) ((gameX - this.targetPlayer.getCenterX()) / this.zoom);
		return res;
	}

	@Override
	public float getScreenCoordinateY(int gameY) {
		float res = this.height / 2
				- (((+gameY - this.targetPlayer.getCenterY())) / this.zoom);
		return res;
	}

	@Override
	public boolean isClickOnUpperHalf(MotionEvent motionEvent) {
		return getGameCoordinateY(motionEvent.getY()) > this.targetPlayer
				.getCenterY();
	}

}
