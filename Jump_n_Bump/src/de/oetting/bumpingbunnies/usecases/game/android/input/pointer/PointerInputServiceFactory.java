package de.oetting.bumpingbunnies.usecases.game.android.input.pointer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import de.oetting.bumpingbunnies.usecases.game.android.GameView;
import de.oetting.bumpingbunnies.usecases.game.android.input.AbstractTouchService;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.android.input.PathFinder.PathFinderFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.TouchInputDispatcher;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;

public class PointerInputServiceFactory extends
		AbstractPlayerInputServicesFactory<AbstractTouchService> {

	@Override
	public AbstractTouchService createInputService(AllPlayerConfig config,
			Context context, GameView view) {
		PlayerMovementController playerMovement = config
				.getTabletControlledPlayerMovement();
		PointerInputService touchService = new PointerInputService(
				playerMovement,
				PathFinderFactory.createPathFinder(playerMovement.getPlayer()),
				config.getCoordinateCalculations());
		view.addOnSizeListener(touchService);
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
