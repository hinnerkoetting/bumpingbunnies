package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering.BitmapResizer;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class BackgroundDrawer implements Drawable {

	private final Bitmap originalBbitmap;
	private final Paint paint;
	private final BitmapResizer resizer;
	private Bitmap resizedbitmap;
	private final boolean draw;

	public BackgroundDrawer(Bitmap bitmap, BitmapResizer resizer, boolean draw) {
		super();
		this.originalBbitmap = bitmap;
		this.resizer = resizer;
		this.paint = new Paint();
		this.draw = draw;
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		if (this.draw) {
			canvas.drawImageDirect(this.resizedbitmap, 0, 0, this.paint);
		} else {
			canvas.drawColor(Color.WHITE);
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
