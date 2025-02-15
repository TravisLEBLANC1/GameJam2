package game;

import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.Timer;

import sound.SoundEnum;
import sound.SoundPlayer;
import util.Vector;

public class ObjectInteract implements Button{
	private Rectangle r;
	private int state;
	private boolean blocked = false;
	private int maxstate;
	private ObjectInteract toUnlock;
	public final EventEnum type;
	
	public ObjectInteract(EventEnum type, Rectangle hitbox, ObjectInteract toUnlock, int maxstate) {
		this.type = type;
		this.r = hitbox;
		this.maxstate = maxstate;
		this.toUnlock = toUnlock;
		state = 0;
		switch(type) {
		case TASSE -> blocked = true;
		default -> {}
		}
	}
	
	public ObjectInteract(EventEnum type, Rectangle hitbox) {
		this(type, hitbox, null, 2);
	}
	
	public ObjectInteract(EventEnum type) {
		this(type, new Rectangle(), null, 2);
	}
	
	public static ObjectInteract empty() {
		return new ObjectInteract(EventEnum.NOEV);
	}
	
	@Override
	public boolean intersects(Polygon hitbox) {
		return hitbox.intersects(r);
	}
	
	public Rectangle getRect() {
		return r;
	}
	
	public int getState() {
		return state;
	}
	
	public void unlock() {
		blocked = false;
	}
	
	public void unlockNext() {
		if (toUnlock != null) {
			toUnlock.unlock();
		}
	}
	
	public void interact() {
		if (!blocked)
			state = (state + 1) % maxstate;
	}
	
	public boolean isValid() {
		return (state +1)% maxstate == 0;
	}
}
