package de.oetting.bumpingbunnies.pc.game.factory;

import org.junit.Test;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.AbsoluteCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.world.WorldProperties;

public class GameThreadFactoryTest {

	@Test
	public void create_smokeTest() {
		GameStopper gameStopper = createDummyGameStopper();
		new GameThreadFactory().create(new AbsoluteCoordinatesCalculation(1, 1, new WorldProperties(100, 100)), new World(), gameStopper, new Configuration(
				null, null, null, null, true), new Player(1, "test", 1, Opponent.createMyPlayer("")));
	}

	private GameStopper createDummyGameStopper() {
		return new GameStopper() {

			@Override
			public void stopGame() {
			}

			@Override
			public void onDisconnect() {
			}
		};
	}

}
