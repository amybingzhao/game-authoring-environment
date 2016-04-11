package gui.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * 
 * Abstract class generating a VBox containing a Label prompting user to do something, a TextArea in which the author
 * can enter text, and a Button that can perform any defined action
 * game's description
 * 
 * @author Stephen
 *
 */

public abstract class TextAreaParent {
	
	private VBox myContainer;
	private Label myPrompt;
	private TextArea myTextArea;
	private Button myButton;
	
	public TextAreaParent(String promptText, String buttonText, int prefRows) {
		myContainer = new VBox();
		myPrompt = new Label(promptText);
		myPrompt.setWrapText(true);
		myTextArea = new TextArea();
		myTextArea.setPrefRowCount(prefRows);
		myButton = new Button(buttonText);
		myButton.prefWidthProperty().bind(myContainer.widthProperty());
		myContainer.getChildren().addAll(myPrompt, myTextArea, myButton);
	}
	
	protected abstract void declareButtonAction();
	
	protected void setButtonAction(EventHandler<ActionEvent> buttonAction) {
		myButton.setOnAction(buttonAction);
	}
	
	protected void setContainerPadding(Insets insets) {
		myContainer.setPadding(insets);
	}
	
	protected void setTextAreaPromptText(String prompt) {
		myTextArea.setPromptText(prompt);
	}
	
	public VBox getCoupledNodes() {
		return myContainer;
	}

}
