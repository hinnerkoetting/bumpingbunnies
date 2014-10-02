package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class BackgroundDrawer implements Drawable {

	private final ImageWrapper originalBbitmap;
	private final Paint paint;
	private final boolean draw;

	public BackgroundDrawer(ImageWrapper bitmap, boolean draw) {
		super();
		this.originalBbitmap = bitmap;
		this.paint = new Paint();
		paint.setColor(de.oetting.bumpingbunnies.color.Color.WHITE);
		this.draw = draw;
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		if (this.draw) {
			canvas.drawImageDirect(this.originalBbitmap, 0, 0, this.paint);
		} else {
			canvas.drawColor(paint);
		}
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return false;
	}
}
