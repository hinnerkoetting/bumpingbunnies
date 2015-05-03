package de.oetting.bumpingbunnies.leveleditor.viewer.editingMode;

import de.oetting.bumpingbunnies.leveleditor.MyCanvas;
import de.oetting.bumpingbunnies.model.game.objects.Water;

public class CreateWaterEditingMode extends CreateObjectEditingMode<Water> {

	public CreateWaterEditingMode(SelectionModeProvider provider, EditorObjectFactory<Water> objectFactory, MyCanvas canvas) {
		super(provider, objectFactory, canvas);
	}

	@Override
	protected void addToWorld(Water newWater) {
		getProvider().getWorld().addWater(newWater);
	}

}
