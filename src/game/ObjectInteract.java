package game;

import java.awt.Polygon;
import java.awt.Rectangle;

import util.Vector;

public class ObjectInteract {
	private Rectangle r;
	private int state;
	private int maxstate;
	public final EventEnum type;
	
	public ObjectInteract(EventEnum type, Rectangle hitbox, int maxstate) {
		this.type = type;
		this.r = hitbox;
		this.maxstate = maxstate;
		state = 0;
	}
	
	public ObjectInteract(EventEnum type, Rectangle hitbox) {
		this(type, hitbox, 2);
	}
	
	public boolean intersects(Polygon hitbox) {
		return hitbox.intersects(r);
	}
	
	public Rectangle getRect() {
		return r;
	}
	
	public int getState() {
		return state;
	}
	
	public void nextState() {
		state = (state + 1) % maxstate;
	}
	
	public boolean isValid() {
		System.out.println((state +1)% maxstate == 0);
		return (state +1)% maxstate == 0;
	}
}
