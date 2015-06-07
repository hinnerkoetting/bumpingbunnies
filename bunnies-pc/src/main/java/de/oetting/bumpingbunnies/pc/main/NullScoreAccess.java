package de.oetting.bumpingbunnies.pc.main;

import java.util.List;

import de.oetting.bumpingbunnies.core.game.steps.ScoreboardAccess;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class NullScoreAccess implements ScoreboardAccess {

	@Override
	public void replaceBunnies(List<Bunny> replace) {
	}
}
