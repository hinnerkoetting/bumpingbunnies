package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

public class XmlWorldBuilderTemplate implements WorldObjectsBuilder {
	private XmlWorldBuilder worldBuilder;

	public XmlWorldBuilderTemplate(int id) {
		this.worldBuilder = new XmlWorldBuilder(id);
	}

	@Override
	public Collection<GameObject> createAllWalls(Context context) {
		return this.worldBuilder.createAllWalls(context);
	}

	@Override
	public List<SpawnPoint> createSpawnPoints() {
		return this.worldBuilder.createSpawnPoints();
	}

}