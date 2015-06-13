package de.oetting.bumpingbunnies.core.game.player;

public class BunnyNameFactory {

	public static String createAiName(int index ) {
		switch (index) {
		case 1:
			return "Siggi";
		case 2:
			return "Stoibli";
		case 3:
			return "Wulfi";
		case 4:
			return "Omaba";
		case 5:
			return "Angel";
		case 6:
			return "Nallis";
		case 7:
			return "Tupin";
		case 8:
			return "Sorkazy";
		case 9:
			return "Tuttengerb";
		case 10:
			return "Fluschi";
			default:
				return "AI" + index;
		}
	
	}
}
