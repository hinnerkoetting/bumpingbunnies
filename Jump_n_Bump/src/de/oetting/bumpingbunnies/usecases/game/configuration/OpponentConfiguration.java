package de.oetting.bumpingbunnies.usecases.game.configuration;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

@SuppressLint("ParcelCreator")
public class OpponentConfiguration implements Parcelable {

	private final AiModus aiMode;
	private final PlayerProperties otherPlayerState;
	private final Opponent opponent;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		this.otherPlayerState.writeToParcel(dest, flags);
		dest.writeString(this.aiMode.toString());
		this.opponent.writeToParcel(dest, flags);
	}

	public OpponentConfiguration(AiModus aiMode,
			PlayerProperties otherPlayerState, Opponent opponent) {
		this.aiMode = aiMode;
		this.otherPlayerState = otherPlayerState;
		this.opponent = opponent;
	}

	public OpponentConfiguration(Parcel in) {
		this.otherPlayerState = new PlayerProperties(in);
		this.aiMode = AiModus.valueOf(in.readString());
		this.opponent = new Opponent(in);
	}

	public AiModus getAiMode() {
		return this.aiMode;
	}

	public int getPlayerId() {
		return this.otherPlayerState.getPlayerId();
	}

	public String getName() {
		return this.otherPlayerState.getPlayerName();
	}

	public Opponent getOpponent() {
		return this.opponent;
	}

}
