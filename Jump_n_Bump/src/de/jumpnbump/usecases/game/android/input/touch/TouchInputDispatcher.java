package de.jumpnbump.usecases.game.android.input.touch;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import de.jumpnbump.usecases.game.android.input.AbstractTouchService;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;

public class TouchInputDispatcher extends InputDispatcher<AbstractTouchService> {

	public TouchInputDispatcher(AbstractTouchService service) {
		super(service);
	}

	@Override
	public void dispatchGameTouch(MotionEvent motion) {
		AbstractTouchService touchService = getInputService();
		touchService.onMotionEvent(motion);
	}

	@Override
	public void dispatchControlViewTouch(View v, MotionEvent motion) {
	}

	@Override
	public void dispatchOnKeyDown(int keyCode, KeyEvent event) {
	}

	@Override
	public void dispatchOnKeyUp(int keyCode, KeyEvent event) {
	}

}
