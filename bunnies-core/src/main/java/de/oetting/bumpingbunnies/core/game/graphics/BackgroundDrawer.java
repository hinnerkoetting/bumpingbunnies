package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class BackgroundDrawer implements Drawable {

	private final ImageWrapper originalBbitmap;
	private final Paint paint;

	public BackgroundDrawer(ImageWrapper bitmap) {
		super();
		this.originalBbitmap = bitmap;
		this.paint = new Paint();
		paint.setColor(de.oetting.bumpingbunnies.model.color.Color.WHITE);
	}

	@Override
	public void draw(CanvasAdapter canvas) {
		canvas.drawImageDirect(this.originalBbitmap, 0, 0, this.paint);
	}

	@Override
	public boolean drawsPlayer(Bunny p) {
		return false;
	}
}
