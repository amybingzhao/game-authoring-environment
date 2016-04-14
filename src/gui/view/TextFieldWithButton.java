package gui.view;

<<<<<<< HEAD
=======
import authoringenvironment.model.IEditableGameElement;
import authoringenvironment.model.IEditingElement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
>>>>>>> 491945f32ffcf2c9e25b40408068e5db271d8a2f
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * This class serves as the base class for all horizontal couplings of a label,
 * text field, and button
 * 
 * @author Stephen
 *
 */

<<<<<<< HEAD
public abstract class TextFieldWithButton extends EditingElementParent {
=======
public abstract class TextFieldWithButton implements IGUIElement, IEditingElement {
>>>>>>> 491945f32ffcf2c9e25b40408068e5db271d8a2f
	private static final int PADDING = 10;
	private static final String GO = "Go";
	private HBox myContainer;
	private Label myTextFieldPrompt;
	private TextField myTextField;
	private String labelText;
	private String promptText;
	private Double textFieldWidth;

	public TextFieldWithButton(String labelText, String promptText, Double textFieldWidth) {
		super(GO);
		this.labelText = labelText;
		this.promptText = promptText;
		this.textFieldWidth = textFieldWidth;
		myTextField = new TextField();
	}
	
	@Override
	public Node createNode() {
		myContainer = new HBox(PADDING);
		myTextFieldPrompt = new Label(labelText);
		myTextField.setPrefWidth(textFieldWidth);
		myTextField.setPromptText(promptText);
		myContainer.getChildren().addAll(myTextFieldPrompt, myTextField, getButton());
		return myContainer;
	}
	
	protected abstract void updateValueBasedOnEditable();
	
	protected void setTextFieldValue(String value) {
		myTextField.setText(value);
	}
	
	protected String getTextFieldInput() {
		return myTextField.getText();
	}
	
	protected void setTextFieldHGrow() {
		HBox.setHgrow(myTextField, Priority.ALWAYS);
	}
}
