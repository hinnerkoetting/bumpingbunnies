package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.os.Parcel;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent.OpponentType;

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
		assertThat(configuration.getAiMode(), is(equalTo(AiModus.OFF)));
		assertThat(configuration.getName(), is(equalTo("name")));
		assertThat(configuration.getPlayerId(), is(equalTo(1)));
		assertThat(configuration.getOpponent().getIdentifier(), is(equalTo("opponent")));
		assertThat(configuration.getOpponent().isMyPlayer(), is(true));
		assertThat(configuration.getOpponent().getType(), is(equalTo(OpponentType.MY_PLAYER)));
	}

	public OpponentConfiguration createOpponentConfiguration() {
		return new OpponentConfiguration(AiModus.OFF, new PlayerProperties(1, "name"), Opponent.createMyPlayer(
				"opponent"));
	}
}
