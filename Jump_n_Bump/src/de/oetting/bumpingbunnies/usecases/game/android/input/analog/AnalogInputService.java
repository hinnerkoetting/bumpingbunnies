package de.oetting.bumpingbunnies.usecases.game.android.input.analog;

import de.oetting.bumpingbunnies.usecases.game.android.input.AbstractControlledMovement;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;

public class AnalogInputService extends AbstractControlledMovement {

	private boolean isTouchUp;
	private boolean isTouchDown;
	private boolean isTouchLeft;
	private boolean isTouchRight;

	public AnalogInputService(PlayerMovementController playerMovement) {
		super(playerMovement);
	}

	@Override
	public void executeUserInput() {
		if (this.isTouchDown) {
			moveDown();
		}
		if (this.isTouchUp) {
			moveUp();
		}
		if (this.isTouchLeft) {
			moveLeft();
		}
		if (this.isTouchRight) {
			moveRight();
		}
	}

	public void reset() {
		this.isTouchUp = false;
		this.isTouchDown = false;
		this.isTouchRight = false;
		this.isTouchLeft = false;
	}

	public void onTouchUpperHalf() {
		this.isTouchUp = true;
		this.isTouchDown = false;
	}

	public void onTouchLowerHalf() {
		this.isTouchUp = false;
		this.isTouchDown = true;
	}

	public void onTouchRightHalf() {
		this.isTouchRight = true;
		this.isTouchLeft = false;
	}

	public void onTouchLeftHalf() {
		this.isTouchRight = false;
		this.isTouchLeft = true;
	}

	@Override
	public void destroy() {

	}

}
