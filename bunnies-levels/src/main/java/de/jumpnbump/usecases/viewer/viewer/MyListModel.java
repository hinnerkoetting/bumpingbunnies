package de.jumpnbump.usecases.viewer.viewer;

import javax.swing.DefaultListModel;

public class MyListModel<T> extends DefaultListModel<T> {

	private static final long serialVersionUID = 1L;

	@Override
	public void fireContentsChanged(Object source, int index0, int index1) {
		super.fireContentsChanged(source, index0, index1);
	}
}
