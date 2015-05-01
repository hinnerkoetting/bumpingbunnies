package de.jumpnbump.usecases.viewer.viewer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.SwingUtilities;

import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;

public class SpawnListAdapter  extends MouseAdapter {

	private final ViewerPanel panel;

	public SpawnListAdapter(ViewerPanel panel) {
		this.panel = panel;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getClickCount() > 1) {
			JList<? extends SpawnPoint> list = (JList<? extends SpawnPoint>) e.getSource();
			SpawnPoint object = list.getSelectedValue();
			SpawnPropertyEditorDialog dialog = new SpawnPropertyEditorDialog(panel.getFrame(), object);
			SwingUtilities.invokeLater(() -> showDialog(dialog));
			e.consume();
		}

	}

	private void showDialog(SpawnPropertyEditorDialog dialog) {
		dialog.show();
		panel.refreshView();
	}
}
