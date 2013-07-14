package de.oetting.bumpingbunnies.usecases.game.configuration;

import android.widget.CompoundButton;
import android.widget.RadioGroup;
import de.oetting.bumpingbunnies.R;

public class WorldConfigurationGenerator {
	public static WorldConfiguration createWorldConfigurationFromRadioGroup(
			RadioGroup group) {
		for (int i = 0; i < group.getChildCount(); i++) {
			CompoundButton cb = (CompoundButton) group.getChildAt(i);
			if (cb.isChecked()) {
				return createWorldConfigurationFromView(cb.getId());
			}
		}
		throw new IllegalArgumentException("no button checked in this group");
	}

	public static WorldConfiguration createWorldConfigurationFromView(int id) {
		switch (id) {
		case R.id.start_world_classic:
			return WorldConfiguration.CLASSIC;
		case R.id.start_world_first:
			return WorldConfiguration.DEMO;
		case R.id.start_world_castle:
			return WorldConfiguration.CASTLE;
		case R.id.start_world_test:
			return WorldConfiguration.TEST;
		default:
			throw new IllegalArgumentException("Unknown inputtype");
		}
	}
}
