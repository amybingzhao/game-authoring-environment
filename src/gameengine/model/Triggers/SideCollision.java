package gameengine.model.Triggers;

import gameengine.model.Actor;

public class SideCollision extends CollisionTrigger {

	private static final String COLLISION_NAME = "SideCollision";

	public SideCollision(Actor actor1, Actor actor2, Boolean oneTime) {
		super(actor1, actor2, oneTime);
	}
	
	public SideCollision(Actor actor1, Actor actor2) {
		super(actor1, actor2, false);
	}

	@Override
	public boolean evaluateCollision(ITrigger otherTrigger) {
		SideCollision otherCollision = (SideCollision) otherTrigger;
		return this.equals(otherCollision);

	}

	@Override
	public String getMyKey() {
		return COLLISION_NAME;
	}
}