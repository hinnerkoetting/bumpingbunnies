package de.oetting.bumpingbunnies.usecases.game.android.input.hardwareKeyboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;

public class HardwareKeyboardFactory extends
		AbstractPlayerInputServicesFactory<HardwareKeyboardInputService> {

	@Override
	public HardwareKeyboardInputService createInputService(PlayerMovement movement,
			Context context, CoordinatesCalculation calculations) {
		return new HardwareKeyboardInputService(movement);
	}

	@Override
	public InputDispatcher<HardwareKeyboardInputService> createInputDispatcher(
			HardwareKeyboardInputService inputService) {
		return new HardwareKeyboardInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {
	}

}
