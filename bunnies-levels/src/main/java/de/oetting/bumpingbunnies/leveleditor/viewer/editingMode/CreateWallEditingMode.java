package de.oetting.bumpingbunnies.leveleditor.viewer.editingMode;

import de.oetting.bumpingbunnies.leveleditor.MyCanvas;
import de.oetting.bumpingbunnies.model.game.objects.Wall;

public class CreateWallEditingMode extends CreateObjectEditingMode<Wall> {

	public CreateWallEditingMode(SelectionModeProvider provider, EditorObjectFactory<Wall> objectFactory, MyCanvas canvas) {
		super(provider, objectFactory, canvas);
	}

	@Override
	protected void addToWorld(Wall newWall) {
		getProvider().getWorld().addWall(newWall);
	}

}
