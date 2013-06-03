package de.jumpnbump.usecases.game.graphics;

import de.jumpnbump.usecases.game.model.Player;

public class PlayerDrawer implements Drawable {

	private final Player player;
	private Animation runningAnimation;
	private Animation runningLeftAnimation;

	public PlayerDrawer(Player player, Animation runningAnimation,
			Animation runningLeftAnimation) {
		this.player = player;
		this.runningAnimation = runningAnimation;
		this.runningLeftAnimation = runningLeftAnimation;
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		if (this.player.movementX() > 0) {
			this.runningAnimation.draw(canvas, this.player.minX(),
					this.player.maxY(), null);
		} else {
			this.runningLeftAnimation.draw(canvas, this.player.minX(),
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
