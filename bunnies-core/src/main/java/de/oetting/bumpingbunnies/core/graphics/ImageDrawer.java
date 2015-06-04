package de.oetting.bumpingbunnies.core.graphics;

import de.oetting.bumpingbunnies.core.game.graphics.CanvasAdapter;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithColor;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class ImageDrawer implements Drawable {

	private final ImageWrapper originalBbitmap;
	private final GameObject object;
	private final Paint paint;

	public ImageDrawer(ImageWrapper bitmap, GameObjectWithColor object) {
		this.originalBbitmap = bitmap;
		this.object = object;
		this.paint = new Paint();
		this.paint.setColor(object.getColor());
	}

	@Override
	public void draw(CanvasAdapter canvas) {
		canvas.drawImage(this.originalBbitmap, this.object.minX(), this.object.maxY(), this.paint);
	}

	@Override
	public boolean drawsPlayer(Bunny p) {
		return false;
	}

}
