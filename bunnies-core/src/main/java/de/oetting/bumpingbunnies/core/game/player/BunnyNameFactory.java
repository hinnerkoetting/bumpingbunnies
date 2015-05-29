package de.oetting.bumpingbunnies.core.game.player;

public class BunnyNameFactory {

	public static String createAiName(int index ) {
		switch (index) {
		case 1:
			return "Siggi";
		case 2:
			return "Angel";
		case 3:
			return "Wulfi";
		case 4:
			return "Nallis";
		case 5:
			return "Stoibli";
		case 6:
			return "Omaba";
		case 7:
			return "Tutengerb";
		case 8:
			return "Sorkazy";
			default:
				return "AI" + index;
		}
	
	}
}
