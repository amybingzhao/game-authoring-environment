package gui.view;

import gameengine.controller.GameInfo;
import gameengine.controller.Level;

/**
 * This class extends the TextFieldWithButton class and allows the author to set the game's name
 * 
 * @author Stephen
 *
 */

public class TextFieldGameNameEditor extends TextFieldWithButton{
	
	/**
	 * Constructs a TextField to edit a game's name with a given prompt and width, and a label to the left and a 
	 * "GO" button to the right.
	 * @param labelText: text for the label.
	 * @param promptText: prompt text for the textfield.
	 * @param textFieldWidth: width of the textfield.
	 */
	public TextFieldGameNameEditor(String labelText, String prompt, Double textFieldWidth) {
		super(labelText, prompt, textFieldWidth);
		setButtonAction(e -> ((GameInfo) getEditableElement()).setMyName(getTextFieldInput()));
		setTextFieldHGrow();
	}

	@Override
	protected void updateValueBasedOnEditable() {
		setTextFieldValue(((GameInfo) getEditableElement()).getMyName());
	}
}
