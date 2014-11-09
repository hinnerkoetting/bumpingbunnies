package de.oetting.bumpingbunnies.pc.scoreMenu;

import java.util.Comparator;

public class ScoreEntryComparator implements Comparator<ScoreEntry> {

	@Override
	public int compare(ScoreEntry o1, ScoreEntry o2) {
		return o2.getScore() - o1.getScore();
	}

}
