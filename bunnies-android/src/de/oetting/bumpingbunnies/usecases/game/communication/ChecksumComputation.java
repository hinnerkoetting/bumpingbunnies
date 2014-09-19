package de.oetting.bumpingbunnies.usecases.game.communication;

import java.security.MessageDigest;
import java.util.Arrays;

public class ChecksumComputation {

	private MessageDigest digest;

	public ChecksumComputation() {
		try {
			this.digest = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new AlgorithmDoesNotExist();
		}
	}

	public byte[] compute(String message) {
		return this.digest.digest(message.getBytes());
	}

	public boolean validate(String message, byte[] checksum) {
		byte[] shouldBeChecksum = this.digest.digest(message.getBytes());
		return Arrays.equals(shouldBeChecksum, checksum);
	}

	public static class AlgorithmDoesNotExist extends RuntimeException {
	}

}
