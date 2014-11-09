package de.oetting.bumpingbunnies.pc.configMenu;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class TitledBorder extends StackPane {

	private Label titleLabel = new Label();
	private StackPane contentPane = new StackPane();
	private Node content;

	public void setContent(Node content) {
		content.getStyleClass().add("bordered-titled-content");
		contentPane.getChildren().add(content);
	}

	public Node getContent() {
		return content;
	}

	public void setTitle(String title) {
		titleLabel.setText(" " + title + " ");
	}

	public String getTitle() {
		return titleLabel.getText();
	}

	public TitledBorder() {
		titleLabel.setText("default title");
		titleLabel.getStyleClass().add("bordered-titled-title");
		StackPane.setAlignment(titleLabel, Pos.TOP_CENTER);

		getStyleClass().add("bordered-titled-border");
		getChildren().addAll(titleLabel, contentPane);
	}
}