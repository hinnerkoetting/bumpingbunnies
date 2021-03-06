package de.jumpnbump.usecases.game.businesslogic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.world.ObjectProvider;
import de.oetting.bumpingbunnies.worldcreator.load.gameObjects.ObjectsFactory;

public class CollisionDetectionTest {

	private CollisionDetection detection;
	@Mock
	private ObjectProvider world;

	@Test
	public void collides_givenTwoDistantObjects_ShouldReturnFalse() {
		GameObject square1 = createObject(0, 0, 1, 1);

		GameObject square2 = createObject(2, 2, 3, 3);
		assertFalse(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenObjectsAtSamePosition_shouldReturnTrue() {
		GameObject square1 = createObject(0, 0, 1, 1);

		GameObject square2 = createObject(0, 0, 1, 1);
		assertTrue(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenOverlappingHorizontalRight_shouldReturnTrue() {
		GameObject square1 = createObject(0, 0, 2, 1);

		GameObject square2 = createObject(1, 0, 2, 1);
		assertTrue(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenOverlappingHorizontalLeft_shouldReturnTrue() {
		GameObject square1 = createObject(1, 0, 2, 1);

		GameObject square2 = createObject(0, 0, 2, 1);
		assertTrue(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenOverlappingVerticalTop_shouldReturnTrue() {
		GameObject square1 = createObject(0, 0, 2, 2);
		GameObject square2 = createObject(0, 1, 1, 3);
		assertTrue(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenTouchingVerticalTop_shouldReturnFalse() {
		GameObject square1 = createObject(0, 0, 1, 1);
		GameObject square2 = createObject(0, 1, 1, 2);
		assertFalse(this.detection.collides(square1, square2));
	}

	@Test
	public void touch_givenTouchingVerticalTop_shouldReturnTrue() {
		GameObject square1 = createObject(0, 0, 1, 1);
		GameObject square2 = createObject(0, 1, 1, 2);
		assertTrue(this.detection.touches(square1, square2));
	}

	@Test
	public void collides_givenOverlappingVerticalBottom_shouldReturnTrue() {
		GameObject square1 = createObject(0, 1, 1, 2);

		GameObject square2 = createObject(0, 0, 1, 2);
		assertTrue(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenJustNotTouchingObjects_shouldreturnFalse() {
		GameObject square1 = createObject(0, 0, 1, 1);

		GameObject square2 = createObject(2, 2, 3, 3);
		assertFalse(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenTouchingObjectsHorizontal_shouldreturnFalse() {
		GameObject square1 = createObject(0, 0, 1, 1);

		GameObject square2 = createObject(1, 0, 2, 2);
		assertFalse(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenTouchingObjectsVertical_shouldReturnFalse() {
		GameObject square1 = createObject(0, 0, 1, 1);
		GameObject square2 = createObject(0, 1, 1, 2);
		assertFalse(this.detection.collides(square1, square2));
	}

	@Test
	public void touches_givenTouchingObjectsVertical_shouldReturnFalse() {
		GameObject square1 = createObject(0, 0, 1, 1);
		GameObject square2 = createObject(0, 1, 1, 2);
		assertTrue(this.detection.touches(square1, square2));
	}

	@Test
	public void collides_givenTouchingObjectsVerticalAndHorizontal_shouldreturnFalse() {
		GameObject square1 = createObject(0, 0, 1, 1);

		GameObject square2 = createObject(1, 1, 2, 2);
		assertFalse(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenTwoObjectsAtSameHeightButFarWidth_shouldreturnFalse() {
		GameObject square1 = createObject(0, 0, 1, 1);

		GameObject square2 = createObject(2, 0, 3, 1);
		assertFalse(this.detection.collides(square1, square2));
	}

	private GameObject createObject(int x, int y, int maxX, int maxY) {
		return ObjectsFactory.createWall(x, y, maxX, maxY);
	}

	@Before
	public void beforeEveryTest() {
		MockitoAnnotations.initMocks(this);
		this.detection = new CollisionDetection(this.world);
	}
}
