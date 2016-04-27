package authoringenvironment.view;

import authoringenvironment.model.IAuthoringActor;
import gameengine.model.Actor;
import javafx.scene.image.ImageView;

/**
 * ImageView that serves as an icon for an actor.
 * 
 * @author amyzhao
 *
 */
public class ImageviewActorIcon extends ImageView {
	private IAuthoringActor myActor;
	private int myID;
	private boolean onLevel;
	private ActorCopier myActorCopier;

	/**
	 * Construct an icon for a given actor.
	 * 
	 * @param actor:
	 *            actor to construct an icon for.
	 */
	public ImageviewActorIcon(IAuthoringActor actor, double height) {
		myActor = actor;
		setImage(actor.getImageView().getImage());
		this.setFitHeight(height);
		this.setPreserveRatio(true);
		this.setRotate(actor.getRotate());
		this.setOpacity(actor.getOpacity());
		myID = actor.getID();
		onLevel = false;
		updateImageView();
		myActorCopier = new ActorCopier((Actor) myActor);
	}

	public IAuthoringActor getActor() {
		//IAuthoringActor actor = new Actor();
		/*actor.setName(myActor.getName());
		actor.setImageViewName(myActor.getImageViewName());
		actor.setImageView(myActor.getImageView());
		actor.setID(myActor.getMyID());
		actor.setSize(myActor.getSize());*/
		//copyActor(actor, myActor);
		//return actor;
		return myActorCopier.makeCopy();
	}

	public IAuthoringActor getRefActor() {
		return myActor;
	}

	// if you have this already on the board, then it should reference the
	// already new actor not the original actor the icon was made from
	/**
	 * Gets the actor associated with this icon.
	 * 
	 * @return my actor.
	 */
	public void updateIconActorPosition(double x, double y) {
		myActor.setX(x);
		myActor.setY(y);
	}

	/**
	 * Gets the ID of the actor associated with this icon. (ID of actor and ID
	 * of its icon are the same).
	 * 
	 * @return my ID.
	 */
	public int getID() {
		return myID;
	}

	/**
	 * Update the imageview based on the actor's current image.
	 */
	public void updateImageView() {
		setImage(myActor.getImageView().getImage());
		setPreserveRatio(true);
		setRotate(myActor.getRotate());
		setOpacity(myActor.getOpacity());
		setScaleX(myActor.getScaleX());
		setScaleY(myActor.getScaleY());
		if (onLevel) {
			setFitHeight(myActor.getImageView().getFitHeight());
		}
	}

	public void setOnLevel(boolean bool) {
		onLevel = bool;
	}
}
