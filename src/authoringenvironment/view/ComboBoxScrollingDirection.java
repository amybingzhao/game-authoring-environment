package authoringenvironment.view;


import java.util.List;
import java.util.ResourceBundle;

public class ComboBoxScrollingDirection extends ComboBoxTextCell {
	private List<String> myOptions;
	
	public ComboBoxScrollingDirection(ResourceBundle myResources, String promptText, List<String> options) {
		super(myResources, promptText);
		myOptions = options;
	}

	@Override
	void setButtonAction() {
		// TODO Auto-generated method stub
	}

	@Override
	List<String> getOptionsList() {
		return myOptions;
	}
}
