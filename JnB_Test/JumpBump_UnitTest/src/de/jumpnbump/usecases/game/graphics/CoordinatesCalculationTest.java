package de.jumpnbump.usecases.game.graphics;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.RelativeCoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CameraPositionCalculation;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class CoordinatesCalculationTest {

	private CameraPositionCalculation camPosition;
	@Mock
	private Paint paint;
	private CoordinatesCalculation fixture;

	@Test
	public void screenCoordinate0x0_givenPlayerIsAtPosition0x0_shouldReturnCenterOfScreen() {
		givenPlayerAtPosition(0, 0);
		givenZoom(1);
		float pixelX = this.fixture.getScreenCoordinateX(0);
		float pixelY = this.fixture.getScreenCoordinateY(0);
		assertThat(pixelX, equalTo(500.0f));
		assertThat(pixelY, equalTo(500.0f));
		// Y-Koordinate ist im Vergleich zu Android gespiegelt
	}

	@Test
	public void screenCoordinate500x500_givenPlayerIsAtPosition_500x500_shouldReturnCenterOfScreen() {
		givenPlayerAtPosition(500, 500);
		givenZoom(1);
		float pixelX = this.fixture.getScreenCoordinateX(500);
		float pixelY = this.fixture.getScreenCoordinateY(500);
		assertThat(pixelX, equalTo(500.0f));
		assertThat(pixelY, equalTo(500.0f));
	}

	@Test
	public void screenCoordinate0x0_givenPlayerIsAtPosition_500x500_shouldReturnBottomLeftOfScreen() {
		givenPlayerAtPosition(500, 500);
		givenZoom(1);
		float pixelX = this.fixture.getScreenCoordinateX(0);
		float pixelY = this.fixture.getScreenCoordinateY(0);
		assertThat(pixelX, equalTo(0.0f));
		assertThat(pixelY, equalTo(1000.0f));
	}

	//
	@Test
	public void screenCoordinate0x0_givenPlayerIsAtPosition0x0AndZoomIs2_shouldReturntCenterOfScreen() {
		givenPlayerAtPosition(0, 0);
		givenZoom(2);
		float pixelX = this.fixture.getScreenCoordinateX(0);
		float pixelY = this.fixture.getScreenCoordinateY(0);
		assertThat(pixelX, equalTo(500.0f));
		assertThat(pixelY, equalTo(500.0f));
	}

	@Test
	public void screenCoordinate0x0_givenPlayerIsAtPosition1000x1000AndZoomIsHalf_shouldReturnBottomLeftOfScreen() {
		givenPlayerAtPosition(1000, 1000);
		givenZoom(2);
		float pixelX = this.fixture.getScreenCoordinateX(0);
		float pixelY = this.fixture.getScreenCoordinateY(0);
		assertThat(pixelX, equalTo(0.0f));
		assertThat(pixelY, equalTo(1000.0f));
	}

	private void givenZoom(int zoom) {
		this.fixture.setZoom(zoom);
	}

	private void givenPlayerAtPosition(int x, int y) {
		this.camPosition.setCurrentScreenX(x);
		this.camPosition.setCurrentScreenY(y);
	}

	@Before
	public void beforeEveryTest() {
		MockitoAnnotations.initMocks(this);
		this.camPosition = new CameraPositionCalculation(new Player(-1, "", 1));
		this.fixture = new RelativeCoordinatesCalculation(this.camPosition);
		this.fixture.updateCanvas(1000, 1000);
	}
}
