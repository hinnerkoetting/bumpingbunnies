package de.oetting.bumpingbunnies.core.network;

import java.util.concurrent.TimeUnit;

public class BytePerSecondMeasurer {

	private int bytesAccumulated = 0;
	private int bytesPerSecond = 0;
	private long startOfLastInterval;

	public BytePerSecondMeasurer(long startOfLastInterval) {
		this.startOfLastInterval = startOfLastInterval;
	}

	public void newMessage(String message, long currentTime) {
		checkForNewInterval(currentTime);
		bytesAccumulated += message.getBytes().length;
	}

	private void checkForNewInterval(long currentTime) {
		if (startOfLastInterval <= currentTime - TimeUnit.SECONDS.toMillis(1)) {
			startOfLastInterval = currentTime;
			bytesPerSecond = bytesAccumulated;
			bytesAccumulated = 0;
		}
	}

	public int getBytesPerSecond(long currentTime) {
		checkForNewInterval(currentTime);
		return bytesPerSecond;
	}

}
