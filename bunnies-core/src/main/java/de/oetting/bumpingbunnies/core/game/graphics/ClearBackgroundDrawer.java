package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.color.Color;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class ClearBackgroundDrawer implements Drawable {

	private final Paint paint = new Paint(Color.LT_BLUE);

	@Override
	public void draw(CanvasAdapter canvas) {
		canvas.drawRectAbsoluteScreen(0, 0, canvas.getOriginalWidth(), canvas.getOriginalHeight(), paint);
	}

	@Override
	public boolean drawsPlayer(Bunny p) {
		return false;
	}

}
