package de.jumpnbump.usecases.game.android.input.multiTouch;

import android.view.MotionEvent;
import android.view.View;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;

public class MultiTouchInputDispatcher extends
		InputDispatcher<MultiTouchInputService> {

	public MultiTouchInputDispatcher(MultiTouchInputService service) {
		super(service);
	}

	@Override
	public void dispatchGameTouch(MotionEvent motion) {
		getInputService().onMotionEvent(motion);
		if (motion.getPointerCount() > 1) {
			getInputService().onMultiTouch(motion);
		} else {
			getInputService().onMultiTouchRemoved(motion);
		}
	}

	@Override
	public void dispatchControlViewTouch(View v, MotionEvent motion) {
	}

}
