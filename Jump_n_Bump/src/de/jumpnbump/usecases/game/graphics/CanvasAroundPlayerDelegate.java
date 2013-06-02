package de.jumpnbump.usecases.game.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.Player;

public class CanvasAroundPlayerDelegate implements CanvasDelegate {

	private static final MyLog LOGGER = Logger
			.getLogger(CanvasAroundPlayerDelegate.class);
	private Canvas canvas;
	private final Player targetPlayer;
	private double zoom;
	private int width;
	private int height;

	public CanvasAroundPlayerDelegate(Player targetPlayer) {
		this.targetPlayer = targetPlayer;
		this.zoom = 1;
	}

	@Override
	public void updateDelegate(Canvas canvas) {
		this.canvas = canvas;
		this.width = this.canvas.getWidth();
		this.height = this.canvas.getHeight();

	}

	@Override
	public void drawColor(int color) {
		this.canvas.drawColor(color);
	}

	@Override
	public void drawLine(int startX, int startY, int stopX, int stopY,
			Paint paint) {
		this.canvas.drawLine(transformX(startX), transformY(startY),
				transformX(stopX), transformY(stopY), paint);
	}

	@Override
	public void drawText(String text, int x, int y, Paint paint) {
		this.canvas.drawText(text, transformX(x), transformY(y), paint);
	}

	@Override
	public void drawRect(int left, int top, int right, int bottom, Paint paint) {
		this.canvas.drawRect(transformX(left), transformY(top),
				transformX(right), transformY(bottom), paint);
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	private float transformX(double x) {
		float res = this.width / 2
				+ (float) ((x - this.targetPlayer.getCenterX()) / this.zoom);
		return res;
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	private float transformY(double y) {
		float res = this.height / 2
				- (float) (((+y - this.targetPlayer.getCenterY())) / this.zoom);
		return res;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

}
