package de.jumpnbump.usecases.game.android.input.rememberPointer;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfigFactory;

public class RememberPointerInputFactory extends
		AbstractPlayerInputServicesFactory<RememberPointerInputService> {

	@Override
	public RememberPointerInputService createInputService(
			PlayerConfigFactory config) {
		return config.createRememberPointerInputService();
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(
			RememberPointerInputService inputService) {
		return new RememberPointerInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {
	}

}
