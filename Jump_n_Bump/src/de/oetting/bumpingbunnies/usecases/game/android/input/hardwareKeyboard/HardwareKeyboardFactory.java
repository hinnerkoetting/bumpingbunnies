package de.oetting.bumpingbunnies.usecases.game.android.input.hardwareKeyboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;

public class HardwareKeyboardFactory extends
		AbstractPlayerInputServicesFactory<HardwareKeyboardInputService> {

	@Override
	public HardwareKeyboardInputService createInputService(AllPlayerConfig config,
			Context context) {
		return new HardwareKeyboardInputService(
				config.getTabletControlledPlayerMovement());
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