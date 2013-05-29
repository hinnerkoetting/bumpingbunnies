package de.jumpnbump.usecases.game.android.input.pointer;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfigFactory;

public class PointerInputServiceFactory extends
		AbstractPlayerInputServicesFactory<PointerInputService> {

	@Override
	public PointerInputService createInputService(PlayerConfigFactory config) {
		return config.createPointerInputService();
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(
			PointerInputService inputService) {
		return new PointerInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {
	}

}
