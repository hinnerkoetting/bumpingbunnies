package de.jumpnbump.usecases.game.android.input.rememberPointer;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.jumpnbump.usecases.game.android.input.AbstractTouchService;
import de.jumpnbump.usecases.game.android.input.InputDispatcher;
import de.jumpnbump.usecases.game.android.input.PathFinder.PathFinderFactory;
import de.jumpnbump.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.jumpnbump.usecases.game.android.input.touch.TouchInputDispatcher;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfig;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class RememberPointerInputFactory extends
		AbstractPlayerInputServicesFactory<AbstractTouchService> {

	@Override
	public AbstractTouchService createInputService(PlayerConfig config) {
		PlayerMovementController tabletControlledPlayerMovement = config
				.getTabletControlledPlayerMovement();
		RememberPointerInputService touchService = new RememberPointerInputService(
				tabletControlledPlayerMovement,
				PathFinderFactory
						.createPathFinder(tabletControlledPlayerMovement
								.getPlayer()));
		config.getGameView().addOnSizeListener(touchService);
		return touchService;
	}

	@Override
	public InputDispatcher<?> createInputDispatcher(
			AbstractTouchService inputService) {
		return new TouchInputDispatcher(inputService);
	}

	@Override
	public void insertGameControllerViews(ViewGroup rootView,
			LayoutInflater inflater, InputDispatcher<?> inputDispatcher) {
	}

}
