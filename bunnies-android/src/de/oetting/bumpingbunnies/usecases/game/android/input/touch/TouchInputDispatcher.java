package de.oetting.bumpingbunnies.usecases.game.android.input.touch;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.android.input.AbstractTouchService;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;

public class TouchInputDispatcher extends InputDispatcher<AbstractTouchService> {

	public TouchInputDispatcher(AbstractTouchService service) {
		super(service);
	}

	@Override
	public boolean dispatchGameTouch(MotionEvent motion) {
		AbstractTouchService touchService = getInputService();
		touchService.onMotionEvent(motion);
		return true;
	}

}
