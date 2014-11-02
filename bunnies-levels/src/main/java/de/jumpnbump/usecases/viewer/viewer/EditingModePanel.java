package de.jumpnbump.usecases.viewer.viewer;

import java.io.IOException;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

public class EditingModePanel extends Box {

	private JToggleButton selectButton;
	private JToggleButton deleteButton;
	private JToggleButton backgroundButton;
	private JToggleButton wallButton;
	private JToggleButton iceWallButton;
	private JToggleButton waterButton;
	private JToggleButton jumperButton;

	public EditingModePanel() {
		super(BoxLayout.Y_AXIS);
		ButtonGroup group = new ButtonGroup();
		group.add(createPointerButton());
		group.add(createTrashButton());
		group.add(createWallButton());
		group.add(createIceButton());
		group.add(createWaterButton());
		group.add(createJumperButton());
		group.add(createBackgroundButton());
		Enumeration<AbstractButton> enumeration = group.getElements();
		while (enumeration.hasMoreElements()) {
			add(enumeration.nextElement());
			add(Box.createVerticalStrut(10));
		}
	}

	private AbstractButton createBackgroundButton() {
		backgroundButton = new JToggleButton(readIcon("/images/background.png"));
		return backgroundButton;
	}

	private AbstractButton createTrashButton() {
		deleteButton = new JToggleButton(readIcon("/images/trash.png"));
		return deleteButton;
	}

	private AbstractButton createWallButton() {
		wallButton = new JToggleButton(readIcon("/images/wall.png"));
		return wallButton;
	}

	private AbstractButton createIceButton() {
		iceWallButton = new JToggleButton(readIcon("/images/ice.png"));
		return iceWallButton;
	}

	private AbstractButton createWaterButton() {
		waterButton = new JToggleButton(readIcon("/images/water.png"));
		return waterButton;
	}

	private AbstractButton createJumperButton() {
		jumperButton = new JToggleButton(readIcon("/images/jumper.png"));
		return jumperButton;
	}

	private AbstractButton createPointerButton() {
		selectButton = new JToggleButton(readIcon("/images/pointer.png"));
		return selectButton;
	}

	private ImageIcon readIcon(String resource) {
		try {
			return new ImageIcon(ImageIO.read(getClass().getResourceAsStream(resource)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isSelectModeActive() {
		return selectButton.isSelected();
	}

}
