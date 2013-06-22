package de.jumpnbump.usecases.game.configuration;

import android.os.Parcel;
import android.os.Parcelable;
import de.jumpnbump.usecases.game.factories.AbstractOtherPlayersFactory;

public class OtherPlayerConfiguration implements Parcelable {

	private final AbstractOtherPlayersFactory factory;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.factory.getClass().getName());
	}

	public OtherPlayerConfiguration(AbstractOtherPlayersFactory factory) {
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
	public OtherPlayerConfiguration(Parcel in) {
		try {
			String strClazz = in.readString();

			Class<? extends AbstractOtherPlayersFactory> clazz = (Class<? extends AbstractOtherPlayersFactory>) Class
					.forName(strClazz, false, getClass().getClassLoader());
			this.factory = clazz.getConstructor(AiModus.class).newInstance(
					AiModus.NORMAL);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public AbstractOtherPlayersFactory getFactory() {
		return this.factory;
	}

}
