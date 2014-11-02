package de.jumpnbump.usecases.viewer.viewer.editingMode;

import de.oetting.bumpingbunnies.model.game.objects.IcyWall;

public class CreateIceWallEditingMode extends CreateObjectEditingMode<IcyWall> {

	public CreateIceWallEditingMode(SelectionModeProvider provider, EditorObjectFactory<IcyWall> objectFactory) {
		super(provider, objectFactory);
	}

	@Override
	protected void addToWorld(IcyWall newWall) {
		getProvider().getWorld().addIcyWall(newWall);
	}

}
