package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;

public class GameStartParameterParceller implements Parceller<GameStartParameter> {

	@Override
	public void writeToParcel(GameStartParameter startParameter, Parcel dest) {
		new ConfigurationParceller().writeToParcel(startParameter.getConfiguration(), dest);
		dest.writeInt(startParameter.getPlayerId());
	}

	@Override
	public GameStartParameter createFromParcel(Parcel source) {
		Configuration configuration = new ConfigurationParceller().createFromParcel(source);
		int playerId = source.readInt();
		return new GameStartParameter(configuration, playerId);
	}

}
