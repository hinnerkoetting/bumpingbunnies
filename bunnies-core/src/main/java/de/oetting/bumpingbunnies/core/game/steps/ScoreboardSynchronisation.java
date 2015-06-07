package de.oetting.bumpingbunnies.core.game.steps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.World;

public class ScoreboardSynchronisation implements PlayerJoinListener {

	private final ScoreboardAccess access;
	private final World world;

	public ScoreboardSynchronisation(ScoreboardAccess access, World world) {
		this.access = access;
		this.world = world;
		scoreIsChanged();
	}

	public void scoreIsChanged() {
		List<Bunny> copiedBunnies = new ArrayList<Bunny>(world.getAllConnectedBunnies());
		Collections.sort(copiedBunnies, new BunnyScoreComparator());
		access.replaceBunnies(copiedBunnies);
	}

	@Override
	public void newEvent(Bunny object) {
		scoreIsChanged();
	}

	@Override
	public void removeEvent(Bunny object) {
		scoreIsChanged();
	}
}
