package de.oetting.bumpingbunnies.core.network;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class BytePerSecondMeasurerTest {

	private BytePerSecondMeasurer classUnderTest;
	private long currentTime = 0;

	@Test
	public void getBytesPerSecond_messageWasAddedInCurrentInerval_isZero() {
		givenMessageWasAdded("a");
		assertThat(classUnderTest.getBytesPerSecond(currentTime), is(0));
	}

	@Test
	public void getBytesPerSecond_twoSecondsArePassedSinceLastCall_isZero() {
		givenMessageWasAdded("a");
		givenOneSecondIsPassed();
		classUnderTest.getBytesPerSecond(currentTime);
		givenOneSecondIsPassed();
		assertThat(classUnderTest.getBytesPerSecond(currentTime), is(0));
	}

	@Test
	public void getBytesPerSecond_messageWasAddedHalfASecondAgo_isMessageSzie() {
		currentTime += 500;
		givenMessageWasAdded("a");
		givenOneSecondIsPassed();
		assertThat(classUnderTest.getBytesPerSecond(currentTime), is(1));
	}

	private void givenOneSecondIsPassed() {
		currentTime += TimeUnit.SECONDS.toMillis(1);
	}

	private void givenMessageWasAdded(String string) {
		classUnderTest.newMessage(string, currentTime);
	}

	@Before
	public void beforeEveryTest() {
		classUnderTest = new BytePerSecondMeasurer(currentTime);
	}

}
