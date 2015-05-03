package de.oetting.bumpingbunnies.leveleditor.xml;

public class XmlRect {

	private double minX;
	private double minY;
	private double maxX;
	private double maxY;

	public double getMinX() {
		return this.minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMinY() {
		return this.minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getMaxX() {
		return this.maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMaxY() {
		return this.maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public XmlRect(double minX, double minY, double maxX, double maxY) {
		super();
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public XmlRect(String minX, String minY, String maxX, String maxY) {
		super();
		this.minX = Double.parseDouble(minX);
		this.minY = Double.parseDouble(minY);
		this.maxX = Double.parseDouble(maxX);
		this.maxY = Double.parseDouble(maxY);
	}

}
