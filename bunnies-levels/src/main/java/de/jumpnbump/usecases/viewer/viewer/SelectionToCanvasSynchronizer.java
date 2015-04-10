package de.jumpnbump.usecases.viewer.viewer;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;

import de.jumpnbump.usecases.viewer.MyCanvas;

public class SelectionToCanvasSynchronizer implements javax.swing.event.ListSelectionListener {

	private final MyCanvas canvas;

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			@SuppressWarnings("unchecked")
			JList<Object> source = (JList<Object>) e.getSource();
			
			this.canvas.setSelectedObject(source.getSelectedValue());
			this.canvas.repaint();
		}
	}

	public SelectionToCanvasSynchronizer(MyCanvas canvas) {
		super();
		this.canvas = canvas;
	}

}
