package de.oetting.bumpingbunnies.usecases.game.graphics;

import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.graphics.Paint;

public interface Animation {

	public abstract void updateGraphics(CanvasDelegate canvas, int width, int height);

	public abstract void draw(CanvasDelegate canvas, long left, long top, Paint paint);

}
