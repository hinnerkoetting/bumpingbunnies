package de.jumpnbump.usecases.game.businesslogic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.jumpnbump.usecases.game.factories.WallFactory;
import de.jumpnbump.usecases.game.model.GameObject;

public class CollisionDetectionTest {

	private CollisionDetection detection;

	@Test
	public void collides_givenTwoDistantObjects_ShouldReturnFalse() {
		GameObject square1 = createObject(0, 0, 1, 1);

		GameObject square2 = createObject(2, 2, 1, 1);
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
		GameObject square1 = createObject(0, 0, 1, 1);

		GameObject square2 = createObject(0.5, 0, 1, 1);
		assertTrue(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenOverlappingHorizontalLeft_shouldReturnTrue() {
		GameObject square1 = createObject(0.5, 0, 1, 1);

		GameObject square2 = createObject(0, 0, 1, 1);
		assertTrue(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenOverlappingVerticalTop_shouldReturnTrue() {
		GameObject square1 = createObject(0.0, 0, 1, 1);

		GameObject square2 = createObject(0, 0.5, 1, 1);
		assertTrue(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenOverlappingVerticalBottom_shouldReturnTrue() {
		GameObject square1 = createObject(0.0, 0.5, 1, 1);

		GameObject square2 = createObject(0, 0.0, 1, 1);
		assertTrue(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenJustNotTouchingObjects_shouldreturnFalse() {
		GameObject square1 = createObject(0.0, 0.0, 1, 1);

		GameObject square2 = createObject(1.0001, 1.00001, 1, 1);
		assertFalse(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenTouchingObjectsHorizontal_shouldreturnTrue() {
		GameObject square1 = createObject(0.0, 0.0, 1, 1);

		GameObject square2 = createObject(1, 0, 1, 1);
		assertTrue(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenTouchingObjectsVertical_shouldreturnTrue() {
		GameObject square1 = createObject(0.0, 0.0, 1, 1);

		GameObject square2 = createObject(0, 1, 1, 1);
		assertTrue(this.detection.collides(square1, square2));
	}

	@Test
	public void collides_givenTouchingObjectsVerticalAndHorizontal_shouldreturnTrue() {
		GameObject square1 = createObject(0.0, 0.0, 1, 1);

		GameObject square2 = createObject(1, 1, 1, 1);
		assertTrue(this.detection.collides(square1, square2));
	}

	private GameObject createObject(double x, double y, double maxX, double maxY) {
		return WallFactory.createWall(x, y, maxX, maxY);
	}

	@Before
	public void beforeEveryTest() {
		this.detection = new CollisionDetection(null);
	}
}
