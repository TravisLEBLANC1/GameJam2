package game.objects;

import java.awt.Polygon;
import java.awt.Rectangle;

import game.EventEnum;
import util.Vector;

public class ObjectInteract implements Button{
	private Rectangle r;
	private boolean isMoving = false;
	private int nbMove = 0;
	private int state;
	private boolean blocked = false;
	private int maxstate;
	private String img;
	public final EventEnum type;

	
	public ObjectInteract(EventEnum type, Vector pos, String img, int maxstate) {
		this.type = type;
		this.r = new Rectangle((int)pos.x() - Button.WIDTH/2, (int) pos.y() - Button.HEIGHT/2, Button.WIDTH, Button.HEIGHT);
		this.maxstate = maxstate;
		this.img = img;
		state = 0;
		switch(type) {
		case TASSE -> blocked = true;
		default -> {}
		}
	}
	
	public ObjectInteract(EventEnum type, Vector pos, String img) {
		this(type, pos, img,  2);
	}
	
	public ObjectInteract(EventEnum type) {
		this(type, Vector.NULL, null, 2);
	}
	
	public static ObjectInteract empty() {
		return new ObjectInteract(EventEnum.NOEV);
	}
	
	@Override
	public boolean intersects(Polygon hitbox) {
		return hitbox.intersects(r);
	}
	
	public Rectangle getRect() {
		if (type == EventEnum.WOOL || type == EventEnum.TASSE) {
			if (isMoving && nbMove < 50) {
				if (state == 1) {
					r.x -= 1;
					r.y += 1;
				}else {
					r.x += 1;
					r.y -= 1;
				}
				nbMove++;
			}
			if (isMoving && nbMove >= 50) {
				nbMove = 0;
				isMoving = false;
			}
		}
		return r;
	}
	
	public int getState() {
		return state;
	}
	
	public void unlock() {
		blocked = false;
	}
	
	public void interact() {
		if (blocked)  return;
		state = (state + 1) % maxstate;
		if (type == EventEnum.WOOL || type == EventEnum.TASSE ) {
			isMoving = true;
		}
		
	}
	
	public boolean isValid() {
		return (state +1)% maxstate == 0;
	}

	@Override
	public String getImg() {
		return img;
	}

	@Override
	public EventEnum getType() {
		return type;
	}
}
