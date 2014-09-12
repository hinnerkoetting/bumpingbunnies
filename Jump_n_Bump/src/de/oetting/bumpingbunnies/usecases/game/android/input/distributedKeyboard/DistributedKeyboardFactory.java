package de.oetting.bumpingbunnies.usecases.game.android.input.distributedKeyboard;

import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.VibrateOnceService;
import de.oetting.bumpingbunnies.usecases.game.android.input.VibratorService;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.gamepad.KeyboardDispatcher;

public class DistributedKeyboardFactory extends
		AbstractPlayerInputServicesFactory<DistributedInputService> {

	@Override
	public DistributedInputService createInputService(PlayerMovement movement,
			Context context, CoordinatesCalculation calculations) {
		VibratorService vibrator = createvibratorService(context);
		return new DistributedInputService(movement, vibrator);
	}

	private VibratorService createvibratorService(Context context) {
		Vibrator systemService = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		return new VibrateOnceService(systemService);
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(
			DistributedInputService inputService) {
		return new KeyboardDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {
		View v = inflater.inflate(R.layout.input_distributed_keyboard,
				rootView, true);
		registerTouchEvents(v, inputDispatcher);
	}

	private void registerTouchEvents(View v,
			final InputDispatcher<?> inputDispatcher) {
		OnTouchListener touchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return inputDispatcher.dispatchControlViewTouch(v, event);

			}
		};

		v.findViewById(R.id.button_up).setOnTouchListener(touchListener);
		v.findViewById(R.id.button_left).setOnTouchListener(touchListener);
		v.findViewById(R.id.button_right).setOnTouchListener(touchListener);
		v.findViewById(R.id.input_distributed_left_right).setOnTouchListener(
				touchListener);

	}

}
