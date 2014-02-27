package de.oetting.bumpingbunnies.usecases.game.communication;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.oetting.bumpingbunnies.tests.UnitTest;

@Category(UnitTest.class)
public class ChecksumComputationTest {

	private ChecksumComputation fixture;

	@Test
	public void compute_givenSameString_shouldReturnSameValue() {
		byte[] checksum1 = this.fixture.compute("input");
		byte[] checksum2 = this.fixture.compute("input");
		assertThat(checksum1, is(equalTo(checksum2)));
	}

	@Test
	public void compute_givenDifferentString_shouldReturnDifferentValue() {
		byte[] checksum1 = this.fixture.compute("input1");
		byte[] checksum2 = this.fixture.compute("input2");
		assertThat(checksum1, is(not(equalTo(checksum2))));
	}

	@Test
	public void validate_givenCorrectChecksum_shouldReturnTrue() {
		byte[] checksum = this.fixture.compute("input");
		boolean isValid = this.fixture.validate("input", checksum);
		assertTrue(isValid);
	}

	@Test
	public void validate_givenWrongChecksum_shouldReturnFalse() {
		byte[] checksum = this.fixture.compute("wrong-input");
		boolean isValid = this.fixture.validate("input", checksum);
		assertFalse(isValid);
	}

	@Before
	public void beforeEveryTest() {
		this.fixture = new ChecksumComputation();
	}

}
