package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.os.Parcel;
import de.oetting.bumpingbunnies.usecases.game.factories.SingleplayerFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.ai.NoAiInputFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

@RunWith(RobolectricTestRunner.class)
public class OpponentConfigurationTest {

	@Test
	public void testParcelling() {
		OpponentConfiguration configuration = createOpponentConfiguration();
		checkValues(configuration);
		OpponentConfiguration after = serializeAndDeserialize(configuration);
		checkValues(after);
	}

	private OpponentConfiguration serializeAndDeserialize(OpponentConfiguration configuration) {
		Parcel parcel = Parcel.obtain();
		configuration.writeToParcel(parcel, 0);
		return new OpponentConfiguration(parcel);
	}

	private void checkValues(OpponentConfiguration configuration) {
		assertThat(configuration.getFactory().getInputServiceFactory(), is(instanceOf(NoAiInputFactory.class)));
		assertThat(configuration.getName(), is(equalTo("name")));
		assertThat(configuration.getPlayerId(), is(equalTo(1)));
		assertThat(configuration.getOpponent().getIdentifier(), is(equalTo("opponent")));
	}

	public OpponentConfiguration createOpponentConfiguration() {
		return new OpponentConfiguration(new SingleplayerFactory(AiModus.OFF), new PlayerProperties(1, "name"), new Opponent(
				"opponent"));
	}
}
