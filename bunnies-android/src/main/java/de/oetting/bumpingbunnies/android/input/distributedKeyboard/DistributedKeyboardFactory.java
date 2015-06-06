package de.oetting.bumpingbunnies.android.input.distributedKeyboard;

import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.android.input.VibrateOnceService;
import de.oetting.bumpingbunnies.android.input.VibratorService;
import de.oetting.bumpingbunnies.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.android.input.gamepad.KeyboardDispatcher;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class DistributedKeyboardFactory extends AbstractPlayerInputServicesFactory<DistributedInputService> {

	private final boolean lefthanded;
	
	
	public DistributedKeyboardFactory(boolean lefthanded) {
		this.lefthanded = lefthanded;
	}

	@Override
	public DistributedInputService createInputService(Bunny movement, Context context, CoordinatesCalculation calculations) {
		VibratorService vibrator = createvibratorService(context);
		return new DistributedInputService(movement, vibrator, context);
	}

	private VibratorService createvibratorService(Context context) {
		Vibrator systemService = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		return new VibrateOnceService(systemService);
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(DistributedInputService inputService) {
		return new KeyboardDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView, LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {
		View v = inflater.inflate(getLayout(), rootView, true);
		registerTouchEvents(v, inputDispatcher);
	}

	private int getLayout() {
		if (lefthanded)
			return R.layout.input_distributed_keyboard_lefthanded;
		return R.layout.input_distributed_keyboard;
	}

	private void registerTouchEvents(View v, final InputDispatcher<?> inputDispatcher) {
		OnTouchListener touchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return inputDispatcher.dispatchControlViewTouch(v, event);

			}
		};

		v.findViewById(R.id.button_up).setOnTouchListener(touchListener);
		v.findViewById(R.id.button_left).setOnTouchListener(touchListener);
		v.findViewById(R.id.button_right).setOnTouchListener(touchListener);
		v.findViewById(R.id.input_distributed_left_right).setOnTouchListener(touchListener);

	}

}
