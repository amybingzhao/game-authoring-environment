package authoringenvironment.view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
//import java.util.Map;
import java.util.ResourceBundle;

import authoringenvironment.controller.Controller;
import authoringenvironment.model.IAuthoringActor;
import authoringenvironment.model.IAuthoringBehavior;
import gameengine.model.Actor;

/**
 * Factory to create visual representations of triggers and actions that go into
 * an ActorRule object
 * 
 * @author AnnieTang
 *
 */
public class ActorRuleFactory {
	private static final String PACKAGE = "authoringenvironment.view.behaviors.";
	private static final String CLASS = "Class";
	private ResourceBundle myResources;
	private IAuthoringActor myActor;
	private static final String CREATE = "create";
	private static final String ELEMENT = "Element";
	private Controller myController;
	private ActorRule myActorRule;

	public ActorRuleFactory(ResourceBundle myLibraryResources, IAuthoringActor myActor, Controller myController,
			ActorRule myActorRule) {
		this.myResources = myLibraryResources;
		this.myActor = myActor;
		this.myController = myController;
		this.myActorRule = myActorRule;
	}

	/**
	 * Return Node type with parameter options for given behavior type
	 * 
	 * @param behaviorType
	 * @param value
	 * @return
	 */
	public IAuthoringBehavior getAuthoringRule(String behaviorType) {
		String className = PACKAGE + myResources.getString(behaviorType + CLASS);
		String elementType = myResources.getString(behaviorType + ELEMENT);
		try {
			Method createMethod = this.getClass().getDeclaredMethod(CREATE + elementType, String.class, String.class);
			return (IAuthoringBehavior) createMethod.invoke(this, behaviorType, className);
		} catch (NoSuchMethodException | SecurityException e) {
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
	 * Return ComboBox IAuthoringRule type with parameter options for Collision
	 * behavior type
	 * 
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
	private IAuthoringBehavior createSelectActorBehavior(String behaviorType, String className)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Class<?> clazz = Class.forName(className);
			Constructor<?> constructor = clazz.getConstructor(ActorRule.class, String.class, ResourceBundle.class, 
					IAuthoringActor.class, List.class);
			List<IAuthoringActor> myActors = new ArrayList<>(myController.getActorMap().keySet());
			return (IAuthoringBehavior) constructor.newInstance(myActorRule, behaviorType, myResources,myActor, 
					myActors);
	}

	/**
	 * Return Label IAuthoringRule type with parameter options for behavior type
	 * 
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
	private IAuthoringBehavior createStandardActorBehavior(String behaviorType, String className)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = Class.forName(className);
		Constructor<?> constructor = clazz.getConstructor(ActorRule.class, IAuthoringActor.class, String.class,
				ResourceBundle.class);
		return (IAuthoringBehavior) constructor.newInstance(myActorRule, myActor, behaviorType, myResources);
	}

	private IAuthoringBehavior createStandardBehavior(String behaviorType, String className)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> clazz = Class.forName(className);
		Constructor<?> constructor = clazz.getConstructor(ActorRule.class, String.class, ResourceBundle.class);
		return (IAuthoringBehavior) constructor.newInstance(myActorRule, behaviorType, myResources);
	}
}