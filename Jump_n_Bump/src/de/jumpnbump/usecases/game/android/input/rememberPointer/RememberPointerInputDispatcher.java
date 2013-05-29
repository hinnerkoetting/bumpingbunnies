package de.jumpnbump.usecases.game.android.input.rememberPointer;

import android.view.MotionEvent;
import android.view.View;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;

public class RememberPointerInputDispatcher extends
		InputDispatcher<RememberPointerInputService> {

	public RememberPointerInputDispatcher(RememberPointerInputService service) {
		super(service);
	}

	@Override
	public void dispatchGameTouch(MotionEvent motion) {
		getInputService().onTouch(motion);
	}

	@Override
	public void dispatchControlViewTouch(View v, MotionEvent motion) {
	}

}
