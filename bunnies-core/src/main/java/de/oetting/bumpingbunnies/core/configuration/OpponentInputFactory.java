package de.oetting.bumpingbunnies.core.configuration;

import de.oetting.bumpingbunnies.core.input.OpponentInput;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public interface OpponentInputFactory {

	OpponentInput create(Bunny p);
}
