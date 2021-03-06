package gameplayer.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import gui.view.IScreen;
import javafx.stage.Stage;

/**
 * This abstract controller class will allow returning to splash functionality
 * for all screens that are branches off the splash screen
 * 
 * @author Carine
 *
 */
public abstract class BranchScreenController implements Observer {
	@XStreamOmitField
	private Stage myStage;
	private ResourceBundle myResources;
	private IScreen myScreen;

	public BranchScreenController(Stage stage, String resource) {
		this.myStage = stage;
		this.myResources = ResourceBundle.getBundle(resource);
	}

	/**
	 * Creates a splash screen and swaps the stage scene for the splash screen
	 * stage
	 */
	protected void goToSplash() {
		// TODO create a splash screen controller
		SplashScreenController splashScreenController = new SplashScreenController(myStage);
	}

	/**
	 * Changes the scene of the stage
	 * @param newScreen
	 */
	protected void changeScreen(IScreen newScreen) {
		this.myStage.setScene(newScreen.getScene());
	}

	/**
	 * 
	 * @return the stage
	 */
	protected Stage getStage() {
		return this.myStage;
	}
	
	/**
	 * Sets the current screen
	 * @param screen
	 */
	protected void setMyScreen(IScreen screen) {
		this.myScreen = screen;
	}
	
	/**
	 * Used in classes that extend this controller, this method uses reflection to invoke controller-specific methods
	 * @param method
	 * @param parameterTypes
	 * @param parameters
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public abstract void invoke(String method, Class[] parameterTypes, Object[] parameters) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;
	
	public void update(Observable o, Object arg) {
		List<Object> myList = (List<Object>) arg;
		String methodName = (String) myList.get(0);
		try {
			if (myResources.getString(methodName).equals("null")) {
				invoke(methodName, null, null);
			} 
			else if (myResources.getString(methodName).equals("String")) {
				Class[] parameterTypes = {String.class};
				Object[] parameters = {(String) myList.get(1)};
				invoke(methodName, parameterTypes, parameters);
			} 
			else {
				Class<?> myClass = Class.forName(myResources.getString(methodName));
				Object arg2 = myClass.cast(myList.get(1));
				Class[] parameterTypes = { myClass };
				Object[] parameters = { arg2 };
				invoke(methodName, parameterTypes, parameters);
			
			}
		} catch (IllegalArgumentException | SecurityException | ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			try {
				this.getClass().getSuperclass().getDeclaredMethod(methodName).invoke(this);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e1) {
				this.myScreen.showError(e.getMessage());
			}
		}
	}
	

}