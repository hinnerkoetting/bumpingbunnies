package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;

public interface Animation {

	void draw(CanvasDelegate canvas, long left, long top, Paint paint);

}
