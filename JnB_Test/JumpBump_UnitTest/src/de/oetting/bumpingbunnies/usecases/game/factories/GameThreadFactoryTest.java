package de.oetting.bumpingbunnies.usecases.game.factories;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.configuration.TestConfigurationFactory;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.WorldObjectsBuilder;

@RunWith(RobolectricTestRunner.class)
public class GameThreadFactoryTest {

	@Test
	public void create_shouldNotThrowException() {
		World w = new World(mock(WorldObjectsBuilder.class));
		w.buildWorld(mock(Context.class));
		GameThreadFactory.create(null, w, mock(Context.class),
				TestConfigurationFactory.createDummyHost(),
				mock(CoordinatesCalculation.class), null, new GameMain(
						null, null), null,
				new ArrayList<PlayerConfig>(), null);
	}

}
