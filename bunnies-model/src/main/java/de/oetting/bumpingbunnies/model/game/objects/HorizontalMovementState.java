package de.oetting.bumpingbunnies.model.game.objects;

public enum HorizontalMovementState {
	MOVING_LEFT("L"), MOVING_RIGHT("R"), NOT_MOVING_HORIZONTAL("N");
	private String encoding;

	private HorizontalMovementState(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public static HorizontalMovementState find(String encoding) {
		for (HorizontalMovementState status : values()) {
			if (status.getEncoding().equals(encoding))
				return status;
		}
		throw new IllegalArgumentException("Encoding does not exist");
	}
}