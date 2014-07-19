package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.os.Parcel;
import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.game.configuration.TestConfigurationFactory;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
public class GameStartParameterTest {

	@Test
	public void testParcelling() {
		GameStartParameter parameter = new GameStartParameter(TestConfigurationFactory.createDummyHost(), 1);
		checkValues(parameter);
		GameStartParameter after = serializeAndDeserialize(parameter);
		checkValues(after);
	}

	private GameStartParameter serializeAndDeserialize(GameStartParameter parameter) {
		Parcel parcel = Parcel.obtain();
		parameter.writeToParcel(parcel, 0);
		return new GameStartParameter(parcel);
	}

	private void checkValues(GameStartParameter parameter) {
		assertThat(parameter.getPlayerId(), is(equalTo(1)));
		// rest shoiuld be tested in their own class
	}

}
