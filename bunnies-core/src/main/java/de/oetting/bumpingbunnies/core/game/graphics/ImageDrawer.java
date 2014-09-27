package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class ImageDrawer implements Drawable {

	private final ImageWrapper image;
	private final Paint paint;

	public ImageDrawer(ImageWrapper image) {
		this.image = image;
		paint = new Paint();
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		canvas.drawImage(image, 0, 0, this.paint);
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return false;
	}

}