package de.oetting.bumpingbunnies.usecases.game.graphics;

import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.Image;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class ImageDrawer implements Drawable {

	private final Image originalBbitmap;
	private final GameObject object;
	private final Paint paint;
	private final ImageResizer resizer;
	private Image resizedbitmap;

	public ImageDrawer(Image bitmap, GameObject object, ImageResizer resizer) {
		super();
		this.originalBbitmap = bitmap;
		this.object = object;
		this.resizer = resizer;
		this.paint = new Paint();
		this.paint.setColor(object.getColor());
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		canvas.drawImage(this.resizedbitmap, this.object.minX(), this.object.maxY(), this.paint);
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {
		int width = (int) (canvas.transformX(this.object.maxX()) - canvas.transformX(this.object.minX()));
		int height = (int) (canvas.transformX(this.object.maxY()) - canvas.transformX(this.object.minY()));
		this.resizedbitmap = this.resizer.resize(this.originalBbitmap, width, height);
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return false;
	}

}
