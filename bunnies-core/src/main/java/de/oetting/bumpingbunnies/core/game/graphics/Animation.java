package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;

public interface Animation {

	void draw(CanvasAdapter canvas, long left, long top, Paint paint);

	int getWidth(CanvasAdapter canvas);

	int getHeight(CanvasAdapter canvas);

}
