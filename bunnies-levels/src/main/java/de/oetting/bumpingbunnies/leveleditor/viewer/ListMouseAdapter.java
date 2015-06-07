package de.oetting.bumpingbunnies.leveleditor.viewer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.SwingUtilities;

import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public class ListMouseAdapter extends MouseAdapter {

	private final ViewerPanel panel;

	public ListMouseAdapter(ViewerPanel panel) {
		this.panel = panel;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getClickCount() > 1) {
			JList<? extends GameObjectWithImage> list = (JList<? extends GameObjectWithImage>) e.getSource();
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
