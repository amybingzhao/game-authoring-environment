package authoringenvironment.view;

import authoringenvironment.model.IActionCreator;
import gameengine.model.IAction;
import gameengine.model.IGameElement;
import gameengine.model.Actions.Action;
import gameengine.model.Actions.WinGame;
import javafx.scene.layout.VBox;

public class WinGameActionCreator extends VBox implements IActionCreator {
	private IGameElement myElement;
	
	public WinGameActionCreator(IGameElement element) {
		myElement = element;
	}
	
	@Override
	public Action createAction() {
		return new WinGame(myElement);
	}
}