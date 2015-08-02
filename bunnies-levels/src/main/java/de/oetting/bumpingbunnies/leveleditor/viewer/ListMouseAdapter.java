package de.oetting.bumpingbunnies.leveleditor.viewer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.SwingUtilities;

import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public class ListMouseAdapter<S extends GameObjectWithImage> extends MouseAdapter {

	private final ViewerPanel panel;
	private JList<S> list;

	public ListMouseAdapter(ViewerPanel panel) {
		this.panel = panel;
	}

	public ListMouseAdapter(ViewerPanel viewerPanel, JList<S> list) {
		panel = viewerPanel;
		this.list = list;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getClickCount() > 1) {
			GameObjectWithImage object = list.getSelectedValue();
			PropertyEditorDialog dialog = new PropertyEditorDialog(panel.getFrame(), object, panel);
			SwingUtilities.invokeLater(() -> showDialog(dialog));
			e.consume();
		}

	}

	private void showDialog(PropertyEditorDialog dialog) {
		dialog.show();
		panel.refreshView();
	}
}
