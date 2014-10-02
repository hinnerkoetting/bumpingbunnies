package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class BackgroundImageDrawer implements Drawable {

	private final ImageWrapper image;
	private final Paint paint;

	public BackgroundImageDrawer(ImageWrapper image) {
		this.image = image;
		paint = new Paint();
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		canvas.drawImageDirect(image, 0, 0, this.paint);
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return false;
	}

}
