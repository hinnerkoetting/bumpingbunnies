package de.oetting.bumpingbunnies.usecases.game.configuration;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.os.Parcel;
import de.oetting.bumpingbunnies.android.parcel.OpponentConfigurationParceller;
import de.oetting.bumpingbunnies.model.configuration.AiModus;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
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
		new OpponentConfigurationParceller().writeToParcel(configuration, parcel);
		parcel.setDataPosition(0);
		return new OpponentConfigurationParceller().createFromParcel(parcel);
	}

	private void checkValues(OpponentConfiguration configuration) {
		assertThat(configuration.getAiMode(), is(equalTo(AiModus.OFF)));
		assertThat(configuration.getName(), is(equalTo("name")));
		assertThat(configuration.getPlayerId(), is(equalTo(1)));
		assertThat(configuration.getOpponent().getIdentifier(), is(equalTo("opponent")));
		assertThat(configuration.getOpponent().isMyPlayer(), is(true));
		assertThat(configuration.getOpponent().getType(), is(equalTo(OpponentType.LOCAL_PLAYER)));
	}

	public OpponentConfiguration createOpponentConfiguration() {
		return new OpponentConfiguration(AiModus.OFF, new PlayerProperties(1, "name"), Opponent.createMyPlayer("opponent"));
	}
}
