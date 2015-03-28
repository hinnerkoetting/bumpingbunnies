package de.jumpnbump.usecases.viewer.viewer.editingMode;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;

public class CreateJumperEditingMode extends CreateObjectEditingMode<Jumper> {

	public CreateJumperEditingMode(SelectionModeProvider provider, EditorObjectFactory<Jumper> objectFactory, MyCanvas canvas) {
		super(provider, objectFactory, canvas);
	}

	@Override
	protected void addToWorld(Jumper newJumper) {
		getProvider().getWorld().addJumper(newJumper);
	}

}
