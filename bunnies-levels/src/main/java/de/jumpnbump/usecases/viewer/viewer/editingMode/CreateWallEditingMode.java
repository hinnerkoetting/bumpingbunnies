package de.jumpnbump.usecases.viewer.viewer.editingMode;

import de.oetting.bumpingbunnies.model.game.objects.Wall;

public class CreateWallEditingMode extends CreateObjectEditingMode<Wall> {

	public CreateWallEditingMode(SelectionModeProvider provider, EditorObjectFactory<Wall> objectFactory) {
		super(provider, objectFactory);
	}

	@Override
	protected void addToWorld(Wall newWall) {
		getProvider().getWorld().addWall(newWall);
	}

}
