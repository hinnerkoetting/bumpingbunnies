package de.jumpnbump.usecases.viewer.viewer.editingMode;

import de.jumpnbump.usecases.viewer.MyCanvas;
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
