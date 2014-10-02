package de.oetting.bumpingbunnies.usecases.game.android.view.configuration;

import android.widget.CompoundButton;
import android.widget.RadioGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.model.configuration.AiModus;

public class AiModusGenerator {
	public static AiModus createFromRadioGroup(RadioGroup group) {
		for (int i = 0; i < group.getChildCount(); i++) {
			CompoundButton cb = (CompoundButton) group.getChildAt(i);
			if (cb.isChecked()) {
				return createFromView(cb.getId());
			}
		}
		throw new IllegalArgumentException("no button checked in this group");
	}

	public static AiModus createFromView(int id) {
		switch (id) {
		case R.id.start_ai_no:
			return AiModus.OFF;
		case R.id.start_ai_normal:
			return AiModus.NORMAL;
			// case R.id.start_ai_runner:
			// return AiModus.RUNNER;
		default:
			throw new IllegalArgumentException("Unknown inputtype");
		}
	}
}
