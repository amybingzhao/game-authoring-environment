package authoringenvironment.view;

import java.util.ArrayList;
import java.util.List;

import authoringenvironment.controller.Controller;
import authoringenvironment.model.IEditableGameElement;
import authoringenvironment.model.IEditingEnvironment;
import gameengine.controller.Actor;
import gameengine.controller.ILevel;
import gameengine.controller.Level;
import gameengine.model.IActor;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author Stephen
 *
 */

public class GUIMainScreen implements IGUI {

	private Controller controller;
	private VBox actorLabelContainer;
	private VBox levelLabelContainer;
	private ScrollPane actorScrollPane;
	private ScrollPane levelScrollPane;
	private HBox scrollPaneContainer;
	private BorderPane borderPane;
	private List<LabelClickable> clickableLabels;
	private List<IActor> createdActors;
	private List<ILevel> createdLevels;
	private IEditingEnvironment actorEditor;
	private IEditingEnvironment levelEditor;

	public GUIMainScreen(Controller controller, List<IActor> createdActors, List<ILevel> createdLevels,
			IEditingEnvironment actorEditor, IEditingEnvironment levelEditor) {
		this.controller = controller;
		this.createdActors = createdActors;
		this.createdLevels = createdLevels;
		this.actorEditor = actorEditor;
		this.levelEditor = levelEditor;
		clickableLabels = new ArrayList<LabelClickable>();
		levelScrollPane = new ScrollPane();
		actorScrollPane = new ScrollPane();
		initializeEnvironment();
	}

	public void initializeEnvironment() {
		initBorderPane();
		initScrollPaneContainer();
		initLabelContainers();
		initScrollPanes();
		scrollPaneContainer.getChildren().addAll(levelScrollPane, actorScrollPane);
		borderPane.setCenter(scrollPaneContainer);
	}

	private void initBorderPane() {
		Stage stage = controller.getStage();
		borderPane = new BorderPane();
		bindNodeSizeToGivenSize(borderPane, stage.widthProperty(), stage.heightProperty());
	}

	private void initScrollPaneContainer() {
		scrollPaneContainer = new HBox();
		bindNodeSizeToGivenSize(scrollPaneContainer, borderPane.widthProperty(), borderPane.heightProperty());
	}

	private void initScrollPanes() {
		initScrollPane(actorScrollPane, actorLabelContainer);
		initScrollPane(levelScrollPane, levelLabelContainer);
	}
	
	private void initScrollPane(ScrollPane scrollPane, VBox container) {
		bindNodeSizeToGivenSize(scrollPane, scrollPaneContainer.widthProperty().divide(2.0), null);
		scrollPane.setContent(container);
	}
	
	private void initLabelContainers() {
		actorLabelContainer = createLabelContainer(actorScrollPane);
		levelLabelContainer = createLabelContainer(levelScrollPane);
	}

	private VBox createLabelContainer(ScrollPane parentScrollPane) {
		VBox container = new VBox();
		bindNodeSizeToGivenSize(container, parentScrollPane.widthProperty(), parentScrollPane.heightProperty());
		return container;
	}

	private void bindNodeSizeToGivenSize(Region child, DoubleExpression width, DoubleExpression height) {
		if (width != null)
			child.prefWidthProperty().bind(width);
		if (height != null)
			child.prefHeightProperty().bind(height);
	}

	public Pane getPane() {
		return borderPane;
	}

	public void addActor() {
		IEditableGameElement newActor = new Actor();
		IActor actorAdded = (IActor) newActor;
		createdActors.add(actorAdded);
		createLabel(newActor, actorEditor, actorLabelContainer);
		controller.goToEditingEnvironment(newActor, actorEditor);
	}

	public void addLevel() {
		IEditableGameElement newLevel = new Level();
		ILevel levelAdded = (ILevel) newLevel;
		createdLevels.add(levelAdded);
		createLabel(newLevel, levelEditor, levelLabelContainer);
		controller.goToEditingEnvironment(newLevel, levelEditor);
	}

	public void createLabel(IEditableGameElement editable, IEditingEnvironment environment, VBox container) {
		LabelClickable label = new LabelClickable(editable, environment, controller);
		container.getChildren().add(label);
		clickableLabels.add(label);
	}

	@Override
	public void updateAllNodes() {
		clickableLabels.stream().forEach(label -> label.update());
	}

}
