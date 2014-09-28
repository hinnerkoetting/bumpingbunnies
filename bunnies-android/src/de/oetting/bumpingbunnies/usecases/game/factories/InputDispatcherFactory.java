package de.oetting.bumpingbunnies.usecases.game.factories;

import android.view.ViewGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.android.input.InputDispatcher;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.core.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.factory.AbstractPlayerInputServicesFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class InputDispatcherFactory {

	public static InputDispatcher<?> createInputDispatcher(GameActivity activity, GameStartParameter parameter, Player myPlayer,
			CoordinatesCalculation coordinatesCalculation) {
		AbstractPlayerInputServicesFactory<InputService> myPlayerFactory = new InputConfigurationFactory().create(parameter.getConfiguration()
				.getInputConfiguration());
		InputService touchService = myPlayerFactory.createInputService(new PlayerMovement(myPlayer), activity, coordinatesCalculation);
		InputDispatcher<?> inputDispatcher = myPlayerFactory.createInputDispatcher(touchService);
		myPlayerFactory.insertGameControllerViews((ViewGroup) activity.findViewById(R.id.game_root), activity.getLayoutInflater(), inputDispatcher);
		return inputDispatcher;
	}
}
