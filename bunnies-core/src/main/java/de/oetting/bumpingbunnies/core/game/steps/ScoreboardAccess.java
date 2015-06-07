package de.oetting.bumpingbunnies.core.game.steps;

import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public interface ScoreboardAccess {

	void replaceBunnies(List<Bunny> replace);
}
