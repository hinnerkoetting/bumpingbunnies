package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Paint;

public class PaintConverter {

	public Paint convert(de.oetting.bumpingbunnies.core.graphics.Paint original) {
		Paint paint = new Paint(original.getColor());
		paint.setAlpha(original.getAlpha());
		paint.setTextSize(original.getTextSize());
		return paint;
	}
}
