package de.oetting.bumpingbunnies.usecases.start;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class OptimalZoomTest  {

	@Test
	public void optimalZoom_enoughPixels_returnsMinZoom() {
		Context context = mock(Context.class);
		givenWidthAndHeigthIs(3000, context);
		int optimalZoom = OptimalZoom.computeOptimalZoom(context);
		assertEquals(4, optimalZoom);
	}
	
	@Test
	public void optimalZoom_halfOfPixels_returnsHigherZoom() {
		Context context = mock(Context.class);
		givenWidthAndHeigthIs(1500, context);
		int optimalZoom = OptimalZoom.computeOptimalZoom(context);
		assertEquals(8, optimalZoom);
	}

	private void givenWidthAndHeigthIs(int i, Context context) {
		Resources resources = mock(Resources.class);
		when(context.getResources()).thenReturn(resources);
		DisplayMetrics metric = mock(DisplayMetrics.class);
		metric.widthPixels = i;
		metric.heightPixels = i;
		when(resources.getDisplayMetrics()).thenReturn(metric);
	}
}
