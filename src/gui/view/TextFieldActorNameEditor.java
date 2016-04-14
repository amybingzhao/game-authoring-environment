package gui.view;

import authoringenvironment.model.IAuthoringActor;

public class TextFieldActorNameEditor extends TextFieldWithButton {

	public TextFieldActorNameEditor(String labelText, String promptText, Double textFieldWidth) {
		super(labelText, promptText, textFieldWidth);
		setButtonAction(e -> ((IAuthoringActor) getEditableElement()).setMyName(getTextFieldInput()));
	}

	@Override
	protected void updateValueBasedOnEditable() {
		// TODO Auto-generated method stub
	}

}
