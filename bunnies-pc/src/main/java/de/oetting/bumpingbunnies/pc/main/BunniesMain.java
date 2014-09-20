package de.oetting.bumpingbunnies.pc.main;

public class BunniesMain {

	public static void main(String[] args) {
		BunniesApplication app = new BunniesApplication();
		startApplication(app);
	}

	private static void startApplication(BunniesApplication app) {
		new Thread(app).start();
	}

}
