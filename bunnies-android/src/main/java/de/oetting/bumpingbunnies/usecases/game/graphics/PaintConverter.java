package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.content.Context;
import android.graphics.Paint;

public class PaintConverter {

	public Paint convert(de.oetting.bumpingbunnies.core.graphics.Paint original, Context contex) {
		Paint paint = new Paint();
		paint.setColor(original.getColor());
		paint.setTextSize(computeSize(contex, original.getTextSize()));
		return paint;
	}
	
	private float computeSize(Context context, float pixel) {
		float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
	    return pixel * scaledDensity;
	}
}
