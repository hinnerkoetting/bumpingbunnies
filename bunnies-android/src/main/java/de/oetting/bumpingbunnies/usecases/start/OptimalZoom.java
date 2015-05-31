package de.oetting.bumpingbunnies.usecases.start;

import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import android.content.Context;
import android.util.DisplayMetrics;

public class OptimalZoom {

	public static int computeOptimalZoom(Context context) {
		int appropriateZoom = computeZoomToFitTheScreen(context);
		return Math.min(Math.max(4, appropriateZoom), 10);
	}

	public static int computeMaximumZoom(Context context) {
		return Math.max(computeZoomToFitTheScreen(context), computeOptimalZoom(context));
	}

	private static int computeZoomToFitTheScreen(Context context) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		int minPixel = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
		int appropriateZoom = ModelConstants.STANDARD_WORLD_SIZE / ModelConstants.ZOOM_MULTIPLIER / minPixel;
		return appropriateZoom;
	}
}
