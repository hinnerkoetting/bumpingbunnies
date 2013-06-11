package de.jumpnbump.usecases.game.android.input.touch;

import android.view.MotionEvent;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class TouchService extends LeftRightTouchService implements InputService {

	private static final MyLog LOGGER = Logger.getLogger(TouchService.class);

	public TouchService(PlayerMovementController playerMovement) {
		super(playerMovement);
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		super.onMotionEvent(motionEvent);
		if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
			if (clickOnUpperHalf(motionEvent)) {
				rememberMoveUp();
			} else {
				rememberMoveDown();
			}
		} else {
			rememberMoveDown();
		}

	}

}
