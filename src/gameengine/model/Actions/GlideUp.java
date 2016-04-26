package gameengine.model.Actions;

import gameengine.model.Actor;
import gameengine.model.IPlayActor;

public class GlideUp extends GlidingAction {

	public GlideUp(Actor assignedActor, double offset) {
		super(assignedActor, offset);
	}

	@Override
	public void perform() {
    	getMyActor().getPhysicsEngine().glideUp(getMyActor(),this.getGlideOffset());					
	}

}
