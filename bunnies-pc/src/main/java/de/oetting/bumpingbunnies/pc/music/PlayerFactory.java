package de.oetting.bumpingbunnies.pc.music;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

public class PlayerFactory {

	private final InputStream inputstream;
	private final String classpath;
	private AudioDevice audioDevice;

	public PlayerFactory(InputStream inputstream, String classpath) {
		this.inputstream = inputstream;
		this.classpath = classpath;
		try {
			audioDevice = FactoryRegistry.systemRegistry().createAudioDevice();
		} catch (JavaLayerException e) {
			throw new RuntimeException(e);
		}
	}

	public Mp3Player createPlayer() {
		try {
			return new Mp3Player(createInputStream(), audioDevice);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public InputStream createInputStream() {
		try {
			ByteArrayOutputStream bais = new ByteArrayOutputStream(1024);
			InputStream inputStream = getClass().getResourceAsStream(classpath);
			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = inputStream.read(buffer)) > 0) {
				bais.write(buffer, 0, bytesRead);
			}
			return new ByteArrayInputStream(bais.toByteArray());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
