package de.jumpnbump.usecases.game.android.input.hardwareKeyboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfig;

public class HardwareKeyboardFactory extends
		AbstractPlayerInputServicesFactory<HardwareKeyboardInputService> {

	@Override
	public HardwareKeyboardInputService createInputService(PlayerConfig config,
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
