package de.oetting.bumpingbunnies.android.input.hardwareKeyboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class HardwareKeyboardFactory extends AbstractPlayerInputServicesFactory<HardwareKeyboardInputService> {

	private final boolean leftHanded;

	public HardwareKeyboardFactory(boolean leftHanded) {
		this.leftHanded = leftHanded;
	}

	@Override
	public HardwareKeyboardInputService createInputService(Player bunny, Context context,
			CoordinatesCalculation calculations) {
		return new HardwareKeyboardInputService(bunny, leftHanded);
	}

	@Override
	public InputDispatcher<HardwareKeyboardInputService> createInputDispatcher(HardwareKeyboardInputService inputService) {
		return new HardwareKeyboardInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView, LayoutInflater inflater,
			InputDispatcher<?> inputDispatcher) {
	}

}
