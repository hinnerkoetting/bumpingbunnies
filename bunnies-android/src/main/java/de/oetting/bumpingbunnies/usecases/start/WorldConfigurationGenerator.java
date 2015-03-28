package de.oetting.bumpingbunnies.usecases.start;

import android.widget.CompoundButton;
import android.widget.RadioGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;

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
		case R.id.start_world_castle:
			return WorldConfiguration.CASTLE;
		default:
			throw new IllegalArgumentException("Unknown inputtype");
		}
	}
}
