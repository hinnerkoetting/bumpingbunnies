package de.jumpnbump.usecases.viewer.viewer.editingMode;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;

public class CreateIceWallEditingMode extends CreateObjectEditingMode<IcyWall> {

	public CreateIceWallEditingMode(SelectionModeProvider provider, EditorObjectFactory<IcyWall> objectFactory, MyCanvas canvas) {
		super(provider, objectFactory, canvas);
	}

	@Override
	protected void addToWorld(IcyWall newWall) {
		getProvider().getWorld().addIcyWall(newWall);
	}

}
