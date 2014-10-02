package de.oetting.bumpingbunnies.usecases.game.android.input.gamepad;

import android.view.MotionEvent;
import android.view.View;
import de.oetting.bumpingbunnies.core.input.InputService;

public interface KeyboardInputService extends InputService {

	boolean onButtonTouch(View v, MotionEvent event);
}
