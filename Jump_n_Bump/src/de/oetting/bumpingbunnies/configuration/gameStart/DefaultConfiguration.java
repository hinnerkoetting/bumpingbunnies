package de.oetting.bumpingbunnies.configuration.gameStart;

import android.os.Build;
import de.oetting.bumpingbunnies.usecases.game.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.SettingsEntity;

public class DefaultConfiguration {

	public static SettingsEntity createDefaultEntity() {
		return new SettingsEntity(InputConfiguration.DISTRIBUTED_KEYBOARD, 5,
				2, 40, Build.MODEL, true, false);
	}
}
