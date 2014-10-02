package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Paint;

public class PaintConverter {

	public Paint convert(de.oetting.bumpingbunnies.core.graphics.Paint original) {
		Paint paint = new Paint();
		paint.setColor(original.getColor());
		paint.setTextSize(original.getTextSize());
		return paint;
	}
}
