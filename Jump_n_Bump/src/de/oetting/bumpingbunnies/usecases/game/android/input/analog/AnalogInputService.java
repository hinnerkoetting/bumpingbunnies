package de.oetting.bumpingbunnies.usecases.game.android.input.analog;

import de.oetting.bumpingbunnies.usecases.game.android.input.AbstractControlledMovement;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovement;

public class AnalogInputService extends AbstractControlledMovement {

	public AnalogInputService(PlayerMovement playerMovement) {
		super(playerMovement);
	}

	@Override
	public void reset() {
	}

	public void onTouchUpperHalf() {
		getPlayerMovement().tryMoveUp();
	}

	public void onTouchLowerHalf() {
		getPlayerMovement().tryMoveDown();
	}

	public void onTouchRightHalf() {
		getPlayerMovement().tryMoveRight();
	}

	public void onTouchLeftHalf() {
		getPlayerMovement().tryMoveLeft();
	}

}
