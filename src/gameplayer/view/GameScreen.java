package gameplayer.view;

import java.util.Observable;

import gameengine.model.Actor;
import gameengine.model.ITrigger;
import gameengine.model.Triggers.ClickTrigger;
import gameengine.model.Triggers.KeyTrigger;
import gui.view.Screen;
import javafx.event.Event;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/** 
 * This class serves as the private interface that a Game screen must implement in order to be able to add visual elements of the game to the screen.
 * It is the container for all the game contents that will be displayed on the screen.
 * @author cmt57
 */

public class GameScreen extends Observable {

	private SubScene mySubscene;
	private Group mySubgroup;

	
	private Camera camera;
	
	public GameScreen(Camera camera){
		mySubgroup = new Group();
		mySubscene = new SubScene(mySubgroup,Screen.SCREEN_WIDTH, 500);
		mySubscene.setFocusTraversable(true);
		mySubscene.setOnKeyPressed(e -> {
			mySubscene.setFill(Color.ALICEBLUE);
		});//handleScreenEvent(e));
		mySubscene.setOnMouseClicked(e ->{
			mySubscene.setFill(Color.ALICEBLUE);
		}); //handleScreenEvent(e));
		this.camera = camera; ///
	}
	
	
	public SubScene getScene(){
		return mySubscene;
	}
	
	/**
	 * Will add a node to the screen's scene representing the given actor's view.
	 * @param actor an instance of IActor
	 */
	public void addActor (Actor actor){
		ImageView imageView = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(actor.getMyImageViewName())));
		actor.setImageView(imageView);
		mySubgroup.getChildren().add(actor.getImageView());//
	}
	

	public void addBackground(String filepath) {
		ImageView imageView = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(filepath)));
		mySubgroup.getChildren().add(imageView);
		
	}
	
	/**
	 * Will receive events on screen and then pass to the game engine's handler to determine what action to take
	 * @param e event 
	 */
	public void handleScreenEvent (Event e){
		if(e.getEventType()==MouseEvent.MOUSE_CLICKED){
			ITrigger trigger = handleClick(((MouseEvent)e).getSceneX(),((MouseEvent)e).getSceneY());
			setChanged();
			notifyObservers(trigger);
		}
		else if(e.getEventType()==KeyEvent.KEY_PRESSED){
			camera.setTranslateX(camera.getTranslateX()+5);
			ITrigger trigger = handleKeyPress(((KeyEvent)e).getCode());
			setChanged();
			notifyObservers(trigger);
		}
	}

	
	private ClickTrigger handleClick(double x, double y){
		ClickTrigger clickTrigger = new ClickTrigger();
		clickTrigger.setClickedAt(x, y);
		return clickTrigger;
	}
	
	private KeyTrigger handleKeyPress(KeyCode key){
		return new KeyTrigger(key);
	}
	
	public void clearGame(){
		mySubgroup.getChildren().clear();
	}

}
