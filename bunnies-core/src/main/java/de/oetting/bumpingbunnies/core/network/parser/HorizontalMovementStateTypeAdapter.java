package de.oetting.bumpingbunnies.core.network.parser;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import de.oetting.bumpingbunnies.model.game.objects.HorizontalMovementState;

public class HorizontalMovementStateTypeAdapter extends TypeAdapter<HorizontalMovementState> {

	@Override
	public HorizontalMovementState read(JsonReader arg0) throws IOException {
		String encoding = arg0.nextString();
		return HorizontalMovementState.find(encoding);
	}

	@Override
	public void write(JsonWriter arg0, HorizontalMovementState arg1) throws IOException {
		arg0.value(arg1.getEncoding());
	}

}
