package game.objects;

import java.awt.Polygon;
import java.awt.Rectangle;

import game.EventEnum;

public class ObjectUnlock implements Button {
	private ObjectInteract toUnlock;
	public ObjectUnlock(ObjectInteract toUnlock) {
		this.toUnlock = toUnlock;
	}
	
	@Override
	public EventEnum getType() {
		return EventEnum.UNLOCK;
	}

	@Override
	public void interact() {}

	@Override
	public boolean intersects(Polygon hitbox) {
		return false;
	}

	@Override
	public boolean isValid() {
		return true;
	}
	
	@Override
	public boolean unlockNext() {
		toUnlock.unlock();
		return true;
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle();
	}

	@Override
	public String getImg() {
		return null;
	}

}
