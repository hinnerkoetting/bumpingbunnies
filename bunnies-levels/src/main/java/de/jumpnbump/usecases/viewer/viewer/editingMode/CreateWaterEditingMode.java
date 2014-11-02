package de.jumpnbump.usecases.viewer.viewer.editingMode;

import de.oetting.bumpingbunnies.model.game.objects.Water;

public class CreateWaterEditingMode extends CreateObjectEditingMode<Water> {

	public CreateWaterEditingMode(SelectionModeProvider provider, EditorObjectFactory<Water> objectFactory) {
		super(provider, objectFactory);
	}

	@Override
	protected void addToWorld(Water newWater) {
		getProvider().getWorld().addWater(newWater);
	}

}
