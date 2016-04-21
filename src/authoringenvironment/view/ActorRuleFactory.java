package authoringenvironment.view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import authoringenvironment.model.IAuthoringActor;
import gameengine.controller.Level;
import gui.view.IGUIElement;
/**
 * Factory to create visual representations of triggers and actions that go into an ActorRule object
 * @author AnnieTang
 *
 */
public class ActorRuleFactory {
	private static final String PACKAGE = "authoringenvironment.view.behaviors.";
	private static final String CLASS = "Class";
	private ResourceBundle myResources;
	private IAuthoringActor myActor;
	private Map<IAuthoringActor, List<IAuthoringActor>> myActors;
	private List<Level> myLevels;
	private static final String CREATE = "create";
	private static final String ELEMENT = "Element";
	
	public ActorRuleFactory(ResourceBundle myLibraryResources, IAuthoringActor myActor, Map<IAuthoringActor, List<IAuthoringActor>> myActors2, List<Level> myLevels){
		this.myResources = myLibraryResources;
		this.myActor = myActor;
		this.myActors = myActors2;
		this.myLevels = myLevels;
	}
	
	/**
	 * Return Node type with parameter options for given behavior type
	 * @param behaviorType
	 * @param value
	 * @return
	 */
	public IGUIElement getAuthoringRule(String behaviorType, String value){ //IGUIElement?
		String className = PACKAGE + myResources.getString(behaviorType+CLASS);
		String elementType = myResources.getString(behaviorType + ELEMENT);
		try{
			Method createMethod = this.getClass().getDeclaredMethod(CREATE + elementType, String.class, String.class);
			return (IGUIElement) createMethod.invoke(this, behaviorType, className);
		}catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Return ComboBox IGUIElement type with parameter options for Collision behavior type
	 * @param behaviorType
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private IGUIElement createCollisionBehavior(String behaviorType, String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		try{
			Class<?> clazz = Class.forName(className);
			Constructor<?> constructor = clazz.getConstructor(String.class, IAuthoringActor.class, ResourceBundle.class, List.class);
			return (IGUIElement) constructor.newInstance(behaviorType,myActor,myResources,myActors);
		}catch(Exception e){
			Class<?> clazz = Class.forName(className);
			Constructor<?> constructor = clazz.getConstructor(String.class, ResourceBundle.class, List.class);
			return (IGUIElement) constructor.newInstance(behaviorType,myResources,myLevels);
		}
	}
	/**
	 * Return Label IGUIElement type with parameter options for behavior type
	 * @param behaviorType
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private IGUIElement createLabelBehavior(String behaviorType, String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> clazz = Class.forName(className);
		Constructor<?> constructor = clazz.getConstructor(IAuthoringActor.class, String.class, ResourceBundle.class);
		return (IGUIElement) constructor.newInstance(myActor,behaviorType,myResources);
	}

	private IGUIElement createComboBoxBehavior(String behaviorType, String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> clazz = Class.forName(className);
		Constructor<?> constructor = clazz.getConstructor(String.class, ResourceBundle.class);
		return (IGUIElement) constructor.newInstance(behaviorType,myResources);
	}
	
//	//only parameter is to select an actor
//	private IGUIElement createSelectActorBehavior(String behaviorType, String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
//		Class<?> clazz = Class.forName(className);
//		Constructor<?> constructor = clazz.getConstructor(String.class, ResourceBundle.class, List.class);
//		return (IGUIElement) constructor.newInstance(behaviorType,myResources, myActors);
//	}
	
	private IGUIElement createChangeAttributeBehavior(String behaviorType, String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//same constructor
		return createLabelBehavior(behaviorType, className);
	}
	
	private IGUIElement createTickBehavior(String behaviorType, String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//same constructor
		return createComboBoxBehavior(behaviorType, className);
	}
}