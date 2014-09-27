package de.oetting.bumpingbunnies.usecases.game.graphics;

import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class ImageDrawer implements Drawable {

	private final ImageWrapper originalBbitmap;
	private final GameObject object;
	private final Paint paint;

	public ImageDrawer(ImageWrapper bitmap, GameObject object) {
		super();
		this.originalBbitmap = bitmap;
		this.object = object;
		this.paint = new Paint();
		this.paint.setColor(object.getColor());
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		canvas.drawImage(this.originalBbitmap, this.object.minX(), this.object.maxY(), this.paint);
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return false;
	}

}
