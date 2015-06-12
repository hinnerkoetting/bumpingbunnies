package de.oetting.bumpingbunnies.model.game.world;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import de.oetting.bumpingbunnies.model.game.objects.Rect;
import de.oetting.bumpingbunnies.usecases.game.TestableGameObject;

public class SegmentTest {

	private Segment classUnderTest;

	@Test
	public void addSegment_completelyInSegment_containsObject() {
		classUnderTest = new Segment(new Rect(0, 0, 3, 10));
		classUnderTest.addObjects(Arrays.asList(new TestableGameObject(1, 1, 2, 2)));
		assertThat(classUnderTest.getObjectsInSegment(), hasSize(1));
	}

	@Test
	public void addSegment_partlyToLeftInSegment_containsObject() {
		classUnderTest = new Segment(new Rect(0, 0, 3, 3));
		classUnderTest.addObjects(Arrays.asList(new TestableGameObject(-1, 1, 2, 2)));
		assertThat(classUnderTest.getObjectsInSegment(), hasSize(1));
	}

	@Test
	public void addSegment_partlyToRightInSegment_containsObject() {
		classUnderTest = new Segment(new Rect(0, 0, 3, 3));
		classUnderTest.addObjects(Arrays.asList(new TestableGameObject(1, 1, 4, 2)));
		assertThat(classUnderTest.getObjectsInSegment(), hasSize(1));
	}

	@Test
	public void addSegment_toRightOutsideOfSegment_doesNotContainObject() {
		classUnderTest = new Segment(new Rect(0, 0, 3, 3));
		classUnderTest.addObjects(Arrays.asList(new TestableGameObject(4, 1, 5, 2)));
		assertThat(classUnderTest.getObjectsInSegment(), hasSize(0));
	}

	@Test
	public void addSegment_toLeftOutsideOfSegment_doesNotContainObject() {
		classUnderTest = new Segment(new Rect(0, 0, 3, 3));
		classUnderTest.addObjects(Arrays.asList(new TestableGameObject(-2, 1, -1, 2)));
		assertThat(classUnderTest.getObjectsInSegment(), hasSize(0));
	}

	@Test
	public void addSegment_completelyOutsideOfSegment_doesNotContainObject() {
		classUnderTest = new Segment(new Rect(0, 0, 10, 10));
		classUnderTest.addObjects(Arrays.asList(new TestableGameObject(-2, -2, -1, -1)));
		assertThat(classUnderTest.getObjectsInSegment(), hasSize(0));
	}

	@Test
	public void addSegment_upPartlyInSegment_containObject() {
		classUnderTest = new Segment(new Rect(0, 0, 3, 3));
		classUnderTest.addObjects(Arrays.asList(new TestableGameObject(1, 1, 3, 4)));
		assertThat(classUnderTest.getObjectsInSegment(), hasSize(1));
	}

	@Test
	public void addSegment_upOutsideOfSegment_doesNotContainObject() {
		classUnderTest = new Segment(new Rect(0, 0, 3, 3));
		classUnderTest.addObjects(Arrays.asList(new TestableGameObject(1, 4, 3, 5)));
		assertThat(classUnderTest.getObjectsInSegment(), hasSize(0));
	}

	@Test
	public void addSegment_downPartlyInSegment_containObject() {
		classUnderTest = new Segment(new Rect(0, 0, 3, 3));
		classUnderTest.addObjects(Arrays.asList(new TestableGameObject(1, -1, 2, 1)));
		assertThat(classUnderTest.getObjectsInSegment(), hasSize(1));
	}

	@Test
	public void addSegment_downOutsideOfSegment_doesNotContainObject() {
		classUnderTest = new Segment(new Rect(0, 0, 3, 3));
		classUnderTest.addObjects(Arrays.asList(new TestableGameObject(1, -2, 2, -1)));
		assertThat(classUnderTest.getObjectsInSegment(), hasSize(0));
	}

}
