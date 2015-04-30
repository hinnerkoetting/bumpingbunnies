package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class BackgroundDrawer implements Drawable {

	private final ImageWrapper originalBbitmap;
	private final Paint paint;
	private final boolean draw;

	public BackgroundDrawer(ImageWrapper bitmap, boolean draw) {
		super();
		this.originalBbitmap = bitmap;
		this.paint = new Paint();
		paint.setColor(de.oetting.bumpingbunnies.model.color.Color.WHITE);
		this.draw = draw;
	}

	@Override
	public void draw(CanvasAdapter canvas) {
		if (this.draw) {
			canvas.drawImageDirect(this.originalBbitmap, 0, 0, this.paint);
		} else {
			canvas.drawColor(paint);
		}
	}

	@Override
	public boolean drawsPlayer(Bunny p) {
		return false;
	}
}
