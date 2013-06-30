package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.model.FixedWorldObject;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

public class TestXmlWorldBuilder implements WorldObjectsBuilder {

	private XmlWorldBuilder worldBuilder;

	public TestXmlWorldBuilder() {
		this.worldBuilder = new XmlWorldBuilder(R.raw.test_world);
	}

	@Override
	public Collection<FixedWorldObject> createAllWalls(Context context) {
		return this.worldBuilder.createAllWalls(context);
	}

	@Override
	public List<SpawnPoint> createSpawnPoints() {
		return this.worldBuilder.createSpawnPoints();
	}

}
