package de.jumpnbump.usecases.viewer.viewer.editingMode;

import de.oetting.bumpingbunnies.model.game.objects.Jumper;

public class CreateJumperEditingMode extends CreateObjectEditingMode<Jumper> {

	public CreateJumperEditingMode(SelectionModeProvider provider, EditorObjectFactory<Jumper> objectFactory) {
		super(provider, objectFactory);
	}

	@Override
	protected void addToWorld(Jumper newJumper) {
		getProvider().getWorld().addJumper(newJumper);
	}

}
