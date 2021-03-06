package authoringenvironment.view;

import authoringenvironment.model.*;
import gui.view.ObjectObservable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * 
 * This class creates a preview unit containing text (the name of an actor or level)
 * and an image (a picture of an actor or level). When clicked, the preview unit sets
 * the screen to either the Level Editing Environment, if the preview unit's
 * IEditableGameElement is a Level, or the Actor Editing Environment, if the
 * preview unit's IEditableGameElement is an Actor
 * 
 * @author Stephen
 *
 */

public class PreviewUnitWithEditable extends ObjectObservable implements IEditingElement {

	private static final Double IMAGE_FIT_SIZE = 75.0;
	private static final Double LABEL_PADDING = 10.0;
	private static final Double HBOX_SPACING = 5.0;
	private final HBox myHBox;
	private final Label myLabel;
	private IEditableGameElement myEditable;

	/**
	 * Creates a preview unit for an IEditableGameElement
	 * @param editable: the IEditableGameElement the preview unit will be displaying
	 */
	public PreviewUnitWithEditable(IEditableGameElement editable) {
		myEditable = editable;
		myLabel = new Label();
		myLabel.setPadding(new Insets(LABEL_PADDING));
		myLabel.setOnMouseClicked(e -> notifyObservers(myEditable));
		myLabel.wrapTextProperty().setValue(true);
		myHBox = new HBox(myLabel);
		myHBox.setAlignment(Pos.CENTER_LEFT);
		myHBox.setSpacing(HBOX_SPACING);
	}
	
	/**
	 * Updates the preview unit's text and image to account for any changes in the
	 * Actor or Level's name and image
	 */
	public void update() {
		myLabel.setText(myEditable.getName());
		ImageView imageView = new ImageView(myEditable.getImageView().getImage());
		imageView.setFitHeight(IMAGE_FIT_SIZE);
		imageView.setPreserveRatio(true);
		imageView.setRotate(myEditable.getImageView().getRotate());
		imageView.setOpacity(myEditable.getImageView().getOpacity());
		imageView.setScaleX(myEditable.getImageView().getScaleX());
		imageView.setScaleY(myEditable.getImageView().getScaleY());
		myLabel.setGraphic(imageView);
	}

	/**
	 * 
	 * @return the preview unit's instance of IEditableGameElement
	 */
	public IEditableGameElement getEditable() {
		return myEditable;
	}

	/**
	 * 
	 * @return the HBox containing all nodes in the preview unit
	 */
	public HBox getHBox() {
		return myHBox;
	}

	/**
	 * Sets the preview unit's IEditableGameElement to new IEditableGameElement
	 */
	@Override
	public void setEditableElement(IEditableGameElement editable) {
		myEditable = editable;
	}

}