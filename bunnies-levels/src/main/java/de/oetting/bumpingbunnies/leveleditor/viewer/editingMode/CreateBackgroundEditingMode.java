package de.oetting.bumpingbunnies.leveleditor.viewer.editingMode;

import de.oetting.bumpingbunnies.leveleditor.MyCanvas;
import de.oetting.bumpingbunnies.model.game.objects.Background;

public class CreateBackgroundEditingMode extends CreateObjectEditingMode<Background> {

	public CreateBackgroundEditingMode(SelectionModeProvider provider, EditorObjectFactory<Background> objectFactory, MyCanvas canvas) {
		super(provider, objectFactory, canvas);
	}

	@Override
	protected void addToWorld(Background newWall) {
		getProvider().getWorld().addBackground(newWall);
	}

}
