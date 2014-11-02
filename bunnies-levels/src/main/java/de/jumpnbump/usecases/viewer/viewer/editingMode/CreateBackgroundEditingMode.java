package de.jumpnbump.usecases.viewer.viewer.editingMode;

import de.oetting.bumpingbunnies.model.game.objects.Background;

public class CreateBackgroundEditingMode extends CreateObjectEditingMode<Background> {

	public CreateBackgroundEditingMode(SelectionModeProvider provider, EditorObjectFactory<Background> objectFactory) {
		super(provider, objectFactory);
	}

	@Override
	protected void addToWorld(Background newWall) {
		getProvider().getWorld().addBackground(newWall);
	}

}
