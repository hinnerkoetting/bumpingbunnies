package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import de.oetting.bumpingbunnies.model.configuration.AiModus;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;

public class OpponentConfigurationParceller implements Parceller<OpponentConfiguration> {

	@Override
	public void writeToParcel(OpponentConfiguration input, Parcel parcel) {
		new PlayerPropertiesParceller().writeToParcel(input.getOtherPlayerState(), parcel);
		parcel.writeString(input.getAiMode().toString());
		new OpponentParceller().writeToParcel(input.getOpponent(), parcel);
	}

	@Override
	public OpponentConfiguration createFromParcel(Parcel parcel) {
		PlayerProperties otherPlayerState = new PlayerPropertiesParceller().createFromParcel(parcel);
		AiModus aiMode = AiModus.valueOf(parcel.readString());
		Opponent opponent = new OpponentParceller().createFromParcel(parcel);
		return new OpponentConfiguration(aiMode, otherPlayerState, opponent);
	}

}