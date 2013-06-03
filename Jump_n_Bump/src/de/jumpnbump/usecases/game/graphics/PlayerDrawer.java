package de.jumpnbump.usecases.game.graphics;

import de.jumpnbump.usecases.game.model.Player;

public class PlayerDrawer implements Drawable {

	private final Player player;
	private Animation runningAnimation;
	private Animation runningLeftAnimation;
	private Animation lastRunningAnimation;

	public PlayerDrawer(Player player, Animation runningAnimation,
			Animation runningLeftAnimation) {
		this.player = player;
		this.runningAnimation = runningAnimation;
		this.runningLeftAnimation = runningLeftAnimation;
		this.lastRunningAnimation = runningAnimation;
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		if (this.player.movementX() > 0) {
			this.runningAnimation.draw(canvas, this.player.minX(),
					this.player.maxY(), null);
			this.lastRunningAnimation = this.runningAnimation;
		} else if (this.player.movementX() < 0) {
			this.runningLeftAnimation.draw(canvas, this.player.minX(),
					this.player.maxY(), null);
			this.lastRunningAnimation = this.runningLeftAnimation;
		} else {

			this.lastRunningAnimation.draw(canvas, this.player.minX(),
					this.player.maxY(), null);
		}
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {
		int width = (int) (canvas.transformX(this.player.maxX()) - canvas
				.transformX(this.player.minX()));
		int height = (int) (canvas.transformX(this.player.maxX()) - canvas
				.transformX(this.player.minX()));
		this.runningAnimation.updateGraphics(canvas, width, height);
		this.runningLeftAnimation.updateGraphics(canvas, width, height);

	}

}
