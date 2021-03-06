package authoringenvironment.view.behaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import authoringenvironment.model.*;
import gameengine.model.*;
import gameengine.model.Triggers.ITrigger;
import gui.view.EditingElementParent;
import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Abstract class to implement ComboBox<IAuthoringActor> or ComboBox<Level>.
 * 
 * @author AnnieTang
 */

public abstract class SelectActorBehavior extends EditingElementParent implements IAuthoringBehavior {
	private static final String HEADER = "Failed to set rules.";
	private static final String CONTENT = "Please choose a value for all fields.";
	private static final double IMAGE_HEIGHT = 20;
	private static final String LABEL = "Label";
	private static final String PROMPT = "Prompt";
	private static final int COMBOBOX_WIDTH = 300;
	private static final int HBOX_SPACING = 5;
	private static final String GO = "GO"; //bottom collision
	private String promptText;
	private ObservableList<IEditableGameElement> options;
	private ComboBox<IEditableGameElement> comboBox;
	private String labelText;
	private TriggerFactory triggerFactory;
	private ActionFactory actionFactory;
	private ActorRule myActorRule;
	private String behaviorType;
	private List<IAuthoringActor> myActors;
	private IAuthoringActor otherActor;
	private IAuthoringActor myActor;
	private IRule myRule;
	private HBox hbox;

	public SelectActorBehavior(IRule myRule, ActorRule myActorRule, String behaviorType, ResourceBundle myResources, 
			IAuthoringActor myActor, List<IAuthoringActor> myActors) {
		super(GO);
		this.myRule = myRule;
		this.behaviorType = behaviorType;
		this.promptText = myResources.getString(behaviorType + PROMPT);
		this.labelText = myResources.getString(behaviorType + LABEL);
		this.triggerFactory = new TriggerFactory();
		this.actionFactory = new ActionFactory();
		this.myActorRule = myActorRule;
		this.myActors = myActors;
		this.myActor = myActor;
		this.hbox = new HBox(HBOX_SPACING);
	}

	/**
	 * Creates ComboBox Node.
	 */
	public Node createNode() {
		Label label = new Label(labelText);
		label.setWrapText(true);
		initComboBox();
		hbox.getChildren().addAll(label, comboBox);
		hbox.setAlignment(Pos.CENTER_LEFT);
		return hbox;
	}

	/**
	 * Initialize ComboBox with size, row count, text, cell factory
	 */
	private void initComboBox() {
		options = FXCollections.observableArrayList(getOptionsList());
		comboBox = new ComboBox<>(options);
		comboBox.setPrefWidth(COMBOBOX_WIDTH);
		comboBox.setPromptText(promptText);
		comboBox.setCellFactory(factory -> new MyCustomCell());
		HBox.setHgrow(comboBox, Priority.ALWAYS);
	}
	
	public void setValue(){
		this.otherActor = (IAuthoringActor) comboBox.getValue();
		if(otherActor == null) showAlert(HEADER, CONTENT);
		createTriggerOrAction();
		setTriggerOrAction();
	}
	
	/**
	 * Shows an alert.
	 * @param alertHeader: alert title.
	 * @param alertContent: text in alert.
	 */
	private void showAlert(String alertHeader, String alertContent) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(alertHeader);
		alert.setContentText(alertContent);
		alert.showAndWait();
	}

	/**
	 * Creates custom cell factory for ComboBox
	 * 
	 * @author AnnieTang
	 */
	private class MyCustomCell extends ListCell<IEditableGameElement> {
		@Override
		protected void updateItem(IEditableGameElement item, boolean empty) {
			super.updateItem(item, empty);
			if (item == null || empty) {
				setGraphic(null);
			} else {
				HBox graphic = new HBox();
				ImageView imageView = item.getImageView();
				imageView.setFitHeight(IMAGE_HEIGHT);
				imageView.setPreserveRatio(true);
				graphic.getChildren().addAll(imageView, new Label(item.getName()));
				setGraphic(graphic);
			}
		}
	}

	/**
	 * Updates Node whenever new information or data is available.
	 */
	public void updateNode() {
		options = FXCollections.observableArrayList(getOptionsList());
		comboBox.setItems(options);
	}

	/**
	 * Returns list of items (IAuthoringActors) in the ComboBox.
	 * 
	 * @return
	 */
	List<IEditableGameElement> getOptionsList() {
		List<IEditableGameElement> toReturn = new ArrayList<>();
		for (IAuthoringActor actor : myActors) {
			toReturn.add(actor);
		}
		return toReturn;
	}

	/**
	 * Create ITrigger or IAction depending on type of behavior
	 */
	protected abstract void createTriggerOrAction();

	/**
	 * Add ITrigger or IAction to actor IRule
	 */
	public abstract void setTriggerOrAction();

	/**
	 * Return if this behavior is a trigger
	 */
	public abstract boolean isTrigger();

	/**
	 * Update the value based on the editable's rules.
	 */
	public abstract void updateValueBasedOnEditable();

	/**
	 * Gets the trigger factory.
	 * 
	 * @return factory
	 */
	protected TriggerFactory getTriggerFactory() {
		return this.triggerFactory;
	}

	/**
	 * Gets the action factory.
	 * 
	 * @return factory
	 */
	protected ActionFactory getActionFactory() {
		return this.actionFactory;
	}

	/**
	 * Set a trigger.
	 * @param key: key for trigger.
	 * @param value: value for trigger.
	 */
	public void setTrigger(IAuthoringBehavior key, ITrigger value) {
		myActorRule.setTrigger(key, value);
	}

	/**
	 * Set an action.
	 * @param key: key for action.
	 * @param value: value for action.
	 */
	public void setAction(IAuthoringBehavior key, IAction value) {
		myActorRule.setAction(key, value);
	}

	/**
	 * Gets the behavior type.
	 * @return behavior type.
	 */
	protected String getBehaviorType() {
		return this.behaviorType;
	}
	
	/**
	 * Gets the actor.
	 * @return actor.
	 */
	protected IAuthoringActor getMyActor(){
		return this.myActor;
	}
	
	/**
	 * Gets the other actor to act on.
	 * @return other actor to act on.
	 */
	protected IAuthoringActor getOtherActor(){
		return this.otherActor;
	}
	
	/**
	 * Gets the rule.
	 * @return rule.
	 */
	protected IRule getMyRule(){
		return this.myRule;
	}
	
	/**
	 * Combobox showing actors.
	 * @return combobox showing actors.
	 */
	protected ComboBox<IEditableGameElement> getComboBox(){
		return this.comboBox;
	}
	
	/**
	 * Gets the hbox container.
	 * @return container.
	 */
	protected HBox getHBox(){
		return this.hbox;
	}
}