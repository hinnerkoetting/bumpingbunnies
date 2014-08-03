package de.oetting.bumpingbunnies.usecases.game.communication;

import java.util.Arrays;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ByteArrayStartMatcher extends BaseMatcher<byte[]> {

	private byte[] expected;

	public ByteArrayStartMatcher(String message) {
		super();
		this.expected = message.getBytes();
	}

	@Override
	public boolean matches(Object item) {
		byte[] bArr = (byte[]) item;
		byte[] shortenedArray = Arrays.copyOf(bArr, this.expected.length);
		return Arrays.equals(shortenedArray, this.expected);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(Arrays.toString(this.expected));
	}
}
