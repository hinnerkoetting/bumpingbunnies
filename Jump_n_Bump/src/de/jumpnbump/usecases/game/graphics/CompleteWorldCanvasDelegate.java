package de.jumpnbump.usecases.game.graphics;


public class CompleteWorldCanvasDelegate extends AbstractCanvasDelegate {

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	@Override
	public float transformX(double x) {
		return GameToAndroidTransformation.transformX(x, this.getWidth());
	}

	/**
	 * Using double as parameter to avoid buffer overflow
	 */
	@Override
	public float transformY(double y) {
		return GameToAndroidTransformation.transformY(y, this.getHeight());
	}

}
