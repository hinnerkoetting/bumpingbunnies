package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Bitmap;
import android.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.graphics.bitmapAltering.BitmapResizer;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;

public class ImageDrawer implements Drawable {

	private final Bitmap originalBbitmap;
	private final GameObject object;
	private final Paint paint;
	private final BitmapResizer resizer;
	private Bitmap resizedbitmap;

	public ImageDrawer(Bitmap bitmap, GameObject object, BitmapResizer resizer) {
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
		int width = (int) (canvas.transformX(this.object.maxX()) - canvas
				.transformX(this.object.minX()));
		int height = (int) (canvas.transformX(this.object.maxY()) - canvas
				.transformX(this.object.minY()));
		this.resizedbitmap = this.resizer.resize(this.originalBbitmap, width, height);
	}

}
