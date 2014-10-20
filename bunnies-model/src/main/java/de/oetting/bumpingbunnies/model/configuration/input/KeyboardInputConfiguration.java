package de.oetting.bumpingbunnies.model.configuration.input;

public class KeyboardInputConfiguration implements InputConfiguration {

	private final String keyLeft;
	private final String keyUp;
	private final String keyRight;

	public KeyboardInputConfiguration(String keyLeft, String keyUp, String keyRight) {
		this.keyLeft = keyLeft;
		this.keyUp = keyUp;
		this.keyRight = keyRight;
	}

	public String getKeyLeft() {
		return keyLeft;
	}

	public String getKeyUp() {
		return keyUp;
	}

	public String getKeyRight() {
		return keyRight;
	}

}
