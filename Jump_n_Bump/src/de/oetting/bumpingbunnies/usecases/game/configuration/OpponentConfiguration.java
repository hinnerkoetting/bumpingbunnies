package de.oetting.bumpingbunnies.usecases.game.configuration;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractOtherPlayersFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

@SuppressLint("ParcelCreator")
public class OpponentConfiguration implements Parcelable {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpponentConfiguration.class);
	private final AbstractOtherPlayersFactory factory;
	private final PlayerProperties otherPlayerState;
	private final Opponent opponent;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		this.otherPlayerState.writeToParcel(dest, flags);
		dest.writeString(this.factory.getClass().getName());
		this.factory.writeToParcel(dest, flags);
		this.opponent.writeToParcel(dest, flags);
	}

	public OpponentConfiguration(AbstractOtherPlayersFactory factory,
			PlayerProperties otherPlayerState, Opponent opponent) {
		this.factory = factory;
		this.otherPlayerState = otherPlayerState;
		this.opponent = opponent;
	}

	@SuppressWarnings("unchecked")
	public OpponentConfiguration(Parcel in) {
		this.otherPlayerState = new PlayerProperties(in);
		String strClazz = in.readString();
		try {

			Class<? extends AbstractOtherPlayersFactory> clazz = (Class<? extends AbstractOtherPlayersFactory>) Class
					.forName(strClazz, false, getClass().getClassLoader());
			this.factory = clazz.getConstructor(Parcel.class).newInstance(in);
			this.opponent = new Opponent(in);
		} catch (Exception e) {
			LOGGER.error("error for %s", strClazz);
			throw new RuntimeException(e);
		}
	}

	public AbstractOtherPlayersFactory getFactory() {
		return this.factory;
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
