package de.oetting.bumpingbunnies.android.parcel;

import android.os.Parcel;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;

public class LocalSettingsParceller implements Parceller<LocalSettings> {

	@Override
	public void writeToParcel(LocalSettings input, Parcel parcel) {
		parcel.writeSerializable(input.getInputConfiguration());
		parcel.writeInt(input.getZoom());
		parcel.writeInt(input.isAltPixelMode() ? 1 : 0);
		parcel.writeInt(input.isBackground() ? 1 : 0);
	}

	@Override
	public LocalSettings createFromParcel(Parcel parcel) {
		InputConfiguration inputConfiguration = (InputConfiguration) parcel.readSerializable();
		int zoom = parcel.readInt();
		boolean background = parcel.readInt() == 1;
		boolean altPixelMode = parcel.readInt() == 1;
		return new LocalSettings(inputConfiguration, zoom, background, altPixelMode);
	}

}
