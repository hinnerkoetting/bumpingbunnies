package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface NewOtherPlayerInputServiceFactory {

	OtherPlayerInputService create(Player p);
}
