package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Color;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class BackgroundDrawer implements Drawable {

	private final ImageWrapper originalBbitmap;
	private final Paint paint;
	private final ImageResizer resizer;
	private ImageWrapper resizedbitmap;
	private final boolean draw;

	public BackgroundDrawer(ImageWrapper bitmap, ImageResizer resizer, boolean draw) {
		super();
		this.originalBbitmap = bitmap;
		this.resizer = resizer;
		this.paint = new Paint();
		this.draw = draw;
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		if (this.draw) {
			canvas.drawImageDirect(this.originalBbitmap, 0, 0, this.paint);
		} else {
			canvas.drawColor(new Paint(Color.WHITE));
		}
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {
		int width = canvas.getOriginalWidth();
		int height = canvas.getOriginalHeight();
		this.resizedbitmap = this.resizer.resize(this.originalBbitmap, width, height);
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return false;
	}
}
