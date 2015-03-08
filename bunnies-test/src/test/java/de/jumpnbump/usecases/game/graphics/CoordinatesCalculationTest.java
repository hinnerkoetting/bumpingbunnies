package de.jumpnbump.usecases.game.graphics;

import static de.oetting.bumpingbunnies.core.game.TestPlayerFactory.createOpponentPlayer;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import de.oetting.bumpingbunnies.core.game.CameraPositionCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.RelativeCoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.world.WorldProperties;

public class CoordinatesCalculationTest {

	private CameraPositionCalculation camPosition;
	private CoordinatesCalculation fixture;
	private WorldProperties properties = new WorldProperties();

	@Test
	public void screenCoordinate0x0_givenPlayerIsAtPosition0x0_shouldReturnBottomLeftOfScreen() {
		givenPlayerAtPosition(0, 0);
		givenZoom(1);
		float pixelX = this.fixture.getScreenCoordinateX(0);
		float pixelY = this.fixture.getScreenCoordinateY(0);
		assertThat(pixelX, equalTo(0.0f));
		assertThat(pixelY, equalTo(1000.0f));
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
	public void screenCoordinate0x0_givenPlayerIsAtPosition0x0AndZoomIs2_shouldReturnBottomLeftOfScreen() {
		givenPlayerAtPosition(0, 0);
		givenZoom(2);
		float pixelX = this.fixture.getScreenCoordinateX(0);
		float pixelY = this.fixture.getScreenCoordinateY(0);
		assertThat(pixelX, equalTo(0.0f));
		assertThat(pixelY, equalTo(1000.0f));
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
		this.camPosition = new CameraPositionCalculation(createOpponentPlayer(), 1);
		this.fixture = new RelativeCoordinatesCalculation(this.camPosition, properties);
		this.fixture.updateCanvas(1000, 1000);
	}
}
