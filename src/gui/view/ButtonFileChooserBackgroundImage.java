package gui.view;

import java.io.File;

import authoringenvironment.model.IEditableGameElement;
import authoringenvironment.model.IEditingEnvironment;
import authoringenvironment.view.LevelEditingEnvironment;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import gameengine.controller.Level;

/**
 * 
 * @author amyzhao
 *
 */
public class ButtonFileChooserBackgroundImage extends ButtonFileChooser {

	public ButtonFileChooserBackgroundImage(String buttonText, String imageName, IEditingEnvironment editor) {
		super(buttonText, imageName, editor);
	}

	@Override
	protected void updateImage(IEditingEnvironment editor, Image image, File imageFile) {
		((LevelEditingEnvironment) editor).changeBackgroundImage(image, imageFile);
	}

}
