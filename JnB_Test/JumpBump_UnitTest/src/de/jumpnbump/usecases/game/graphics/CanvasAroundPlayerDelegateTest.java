package de.jumpnbump.usecases.game.graphics;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.graphics.Canvas;
import android.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.graphics.CanvasDelegateImpl;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class CanvasAroundPlayerDelegateTest {

	private CanvasDelegateImpl canvasDelegate;
	private Player player;
	@Mock
	private Canvas canvas;
	@Mock
	private Paint paint;
	@Mock
	private CoordinatesCalculation calculations;

	@Test
	public void drawTextAtGame0x0_givenPlayerIsAtPosition0x0_shouldBeDrawnAtCenterOfScreen() {
		givenPlayerAtPosition(0, 0);
		givenZoom(1);
		this.canvasDelegate.drawText("test", 0, 0, this.paint);
		verify(this.canvas).drawText("test", 500, 500, this.paint);
		// Y-Koordinate ist im Vergleich zu Android gespiegelt
	}

	@Test
	public void drawTextAtGame500x500_givenPlayerIsAtPosition_500x500_shouldBeDrawnAtCenterOfScreen() {
		givenPlayerAtPosition(500, 500);
		givenZoom(1);
		this.canvasDelegate.drawText("test", 500, 500, this.paint);
		verify(this.canvas).drawText(eq("test"),
				AdditionalMatchers.eq(500, 0.05f),
				AdditionalMatchers.eq(500, 0.05f), eq(this.paint));
	}

	@Test
	public void drawTextAtGame0x_givenPlayerIsAtPosition_500x500_shouldBeDrawnAtBottomLeftOfScreen() {
		givenPlayerAtPosition(500, 500);
		givenZoom(1);
		this.canvasDelegate.drawText("test", 0, 0, this.paint);
		verify(this.canvas).drawText(eq("test"),
				AdditionalMatchers.eq(0, 0.05f),
				AdditionalMatchers.eq(1000, 0.05f), eq(this.paint));
	}

	@Test
	public void drawTextAtGame0x0_givenPlayerIsAtPosition0x0AndZoomIs2_shouldBeDrawnAtCenterOfScreen() {
		givenPlayerAtPosition(0, 0);
		givenZoom(2);
		this.canvasDelegate.drawText("test", 0, 0, this.paint);
		verify(this.canvas).drawText("test", 500, 500, this.paint);
		// Y-Koordinate ist im Vergleich zu Android gespiegelt
	}

	@Test
	public void drawTextAtGame0x0_givenPlayerIsAtPosition250x250AndZoomIsHalf_shouldBeDrawnAtBottomLeftOfScreen() {
		givenPlayerAtPosition(250, 250);
		givenZoom(2);
		this.canvasDelegate.drawText("test", 0, 0, this.paint);
		verify(this.canvas).drawText(eq("test"),
				AdditionalMatchers.eq(0, 0.05f),
				AdditionalMatchers.eq(125, 0.05f), eq(this.paint));
	}

	private void givenZoom(int zoom) {
		this.calculations.setZoom(zoom);
	}

	private void givenPlayerAtPosition(int x, int y) {
		this.player.setCenterX(x);
		this.player.setCenterY(y);
	}

	@Before
	public void beforeEveryTest() {
		MockitoAnnotations.initMocks(this);
		this.player = new Player(-1, "", 1);
		this.canvasDelegate = new CanvasDelegateImpl(this.calculations);
		init1000x1000Canvas();
		this.canvasDelegate.updateDelegate(this.canvas);
	}

	private void init1000x1000Canvas() {
		when(this.canvas.getWidth()).thenReturn(1000);
		when(this.canvas.getHeight()).thenReturn(1000);
	}
}
