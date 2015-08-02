package de.oetting.bumpingbunnies.leveleditor.viewer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.SwingUtilities;

import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;

public class SpawnListAdapter  extends MouseAdapter {

	private final ViewerPanel panel;
	private final JList<SpawnPoint> list;

	public SpawnListAdapter(ViewerPanel panel, JList<SpawnPoint> spawns) {
		this.panel = panel;
		this.list = spawns;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getClickCount() > 1) {
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
