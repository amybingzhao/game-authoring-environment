package authoringenvironment.view;

import javafx.scene.layout.Pane;

/**
 * This interface contains the information that the frontend (of the authoring environment)
 *  will pass to the backend (of the authoring environment). The model will call these methods 
 *  in order to change the data representation of the authoring environment, after receiving
 *  input from the user using the environment.
 *
 * @author Annie
 */
public interface IGUI {

	Pane getPane();

}