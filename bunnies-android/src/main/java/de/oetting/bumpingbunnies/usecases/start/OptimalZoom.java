package de.oetting.bumpingbunnies.usecases.start;

import android.content.Context;
import android.util.DisplayMetrics;

public class OptimalZoom {

	public static int computeOptimalZoom(Context context) {
		if (deviceInches(context.getResources().getDisplayMetrics()) > 8) {
			return 3;
		}	else if (deviceInches(context.getResources().getDisplayMetrics()) > 6) {
			return 4;
		}
		int maxPixels = maxPixels(context); 
		if (maxPixels < 1000)
			return 6;
		else if (maxPixels < 1250) 
			return 5;
		return 4;
	}

	private static int maxPixels(Context context) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return Math.max(metrics.widthPixels, metrics.heightPixels);
	}

	public static int computeMaximumZoom(Context context) {
		return Math.max(computeZoomToFitTheScreen(context), computeOptimalZoom(context));
	}

	private static int computeZoomToFitTheScreen(Context context) {
		return (int) Math.round(30 / deviceInches(context.getResources().getDisplayMetrics()));
	}

	private static double deviceInches(DisplayMetrics displayMetrics) {
		int width = displayMetrics.widthPixels;
		int height = displayMetrics.heightPixels;
		int dens = displayMetrics.densityDpi;
		double wi = (double) width / (double) dens;
		double hi = (double) height / (double) dens;
		double x = Math.pow(wi, 2);
		double y = Math.pow(hi, 2);
		return Math.sqrt(x + y);
	}
}
