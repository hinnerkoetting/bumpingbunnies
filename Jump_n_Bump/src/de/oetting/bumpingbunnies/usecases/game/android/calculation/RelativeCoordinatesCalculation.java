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
		return (int) (this.zoom * touchX + (getCurrentCenterX() - this.width
				/ 2 * this.zoom));
	}

	@Override
	public int getGameCoordinateY(float touchY) {
		return (int) (-(touchY - this.height / 2) * this.zoom + getCurrentCenterY());
	}

	@Override
	public int getScreenCoordinateX(long gameX) {
		int res = (int) (this.width
				/ 2
				+ (gameX - getCurrentCenterX()) / this.zoom);
		return res;
	}

	@Override
	public int getScreenCoordinateY(long gameY) {
		int res = (int) (this.height / 2
				- (((+gameY - getCurrentCenterY())) / this.zoom));
		return res;
	}

	@Override
	public boolean isClickOnUpperHalf(MotionEvent motionEvent) {
		return getGameCoordinateY(motionEvent.getY()) > getCurrentCenterY();
	}

	public long getCurrentCenterX() {
		return this.targetPlayer.getCurrentScreenX();
	}

	public long getCurrentCenterY() {
		return this.targetPlayer.getCurrentScreenY();
	}

}
