package de.jumpnbump.usecases.game.android.input;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.businesslogic.GamePlayerController;
import de.jumpnbump.usecases.game.businesslogic.GameScreenSizeChangeListener;
import de.jumpnbump.usecases.game.communication.StateSender;

public class TouchService extends LeftRightTouchService implements
		GameScreenSizeChangeListener, InputService {

	private static final MyLog LOGGER = Logger.getLogger(TouchService.class);

	public TouchService(GamePlayerController playerMovement, StateSender sender) {
		super(playerMovement, sender);
	}

	private float getRelativeY() {
		return this.getLastEvent().getY() / this.getWindowHeight();
	}

	@Override
	protected void executePlayerMovement() {
		super.executePlayerMovement();
		int movement = 10;
		moveUpOrDown(movement);
	}

	private void moveUpOrDown(int movement) {
		if (clickOnUpperHalf()) {
			this.getPlayerMovement().tryMoveUp();
		} else {
			this.getPlayerMovement().tryMoveDown();
		}
	}

	private boolean clickOnUpperHalf() {
		return getRelativeY() < 0.5f;
	}

}
