package authoringenvironment.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import authoringenvironment.view.GUIActorEditingEnvironment;
import authoringenvironment.view.GUILevelEditingEnvironment;
import authoringenvironment.view.GUIMain;
import authoringenvironment.view.GUIMainScreen;
import gameengine.controller.ILevel;
import gameengine.model.IActor;
import javafx.stage.Stage;

/**
 * This class serves as the interface that all authoring environment main screens must implement
 * 
 * @author Stephen, AnnieTang
 */

public class Controller {
	private Stage myStage;
	private List<ILevel> levels;
	private List<IActor> actors;
	private GUILevelEditingEnvironment levelEnvironment;
	private GUIActorEditingEnvironment actorEnvironment; 
	private GUIMainScreen mainScreen;
	private GUIMain guiMain;
	private ResourceBundle myResources;
	
	public Controller(Stage myStage, GUIMain guiMain, ResourceBundle myResources){
		this.myStage = myStage;
		this.guiMain = guiMain;
		this.myResources = myResources; 
		init();
	}
	
	private void init(){
		levels = new ArrayList<>();
		actors = new ArrayList<>();
		levelEnvironment = new GUILevelEditingEnvironment();
		actorEnvironment = new GUIActorEditingEnvironment(myResources);
		mainScreen = new GUIMainScreen(this);
	}
	
	/**
	 * Switches screen to Level Editing Environment
	 * 
	 * @param level - level to be edited 
	 * @param createdActors - list of created Actors that can be placed into the level 
	 */
	public void goToLevelEditing(ILevel level){
		levelEnvironment.setLevel(level);
		clearPanes();
		guiMain.setCenterPane(levelEnvironment.getPane());
		
	}
	
	/**
	 * Switches screen to Actor Editing Environment
	 * 
	 * @param actor - Actor to edit
	 */
	public void goToActorEditing(IActor actor){
		actorEnvironment.setActor(actor);
		clearPanes();
		guiMain.setCenterPane(actorEnvironment.getPane());
	}
	
	/**
	 * Switches screen to main screen
	 */
	public void goToMainScreen(){
		mainScreen.updateAllNodes();
		clearPanes();
		guiMain.setCenterPane(mainScreen.getPane());
	}
	
	/**
	 * Passes Actor and Level info to Game Data to be saved in XML file
     * @param file: file to write to.
     */
	public void saveGame(File file){
		
	}
	
	public void loadGame(File file){
		
	}
	
	/**
     * Gets the current workspace's stage.
     * @return current workspace's stage.
     */
    public Stage getStage() {
        return myStage;
    }
    
    /**
     * Returns list of created levels.
     * @return
     */
    public List<ILevel> getLevels(){
    	return levels;
    }
    
    /**
     * For each level that is created, adds it to the running list in this class. 
     * @param newLevel
     */
    public void addLevel(){
    	mainScreen.addLevel();
    }
    
    private void clearPanes(){
    	guiMain.setBottomPane(null);
    	guiMain.setCenterPane(null);
    	guiMain.setLeftPane(null);
    	guiMain.setRightPane(null);
    }

	public void addActor() {
		mainScreen.addActor();
	}
	
}