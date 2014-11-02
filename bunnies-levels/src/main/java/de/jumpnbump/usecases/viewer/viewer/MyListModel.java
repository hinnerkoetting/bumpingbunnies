package de.jumpnbump.usecases.viewer.viewer;

import java.util.List;

import javax.swing.AbstractListModel;

public class MyListModel<T> extends AbstractListModel<T> {

	private static final long serialVersionUID = 1L;
	private List<T> list;

	public MyListModel(List<T> model) {
		this.list = model;
	}

	@Override
	public void fireContentsChanged(Object source, int index0, int index1) {
		super.fireContentsChanged(source, index0, index1);
	}

	@Override
	public T getElementAt(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getSize() {
		return list.size();
	}
}
