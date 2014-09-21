package de.oetting.bumpingbunnies.pc.graphics;

import java.util.Collection;

import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PcDrawablesFactory implements DrawablesFactory {

	private final World world;

	public PcDrawablesFactory(World world) {
		super();
		this.world = world;
	}

	@Override
	public Collection<Drawable> createAllDrawables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Drawable createPlayerDrawable(Player p) {
		// TODO Auto-generated method stub
		return null;
	}

}
