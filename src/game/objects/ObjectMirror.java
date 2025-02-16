package game.objects;

import java.awt.Polygon;
import java.awt.Rectangle;

import game.EventEnum;
import game.NPC;
import util.Vector;

public class ObjectMirror implements Button  {
	private Rectangle r;
	private int state;
	private boolean blocked = true;
	private int maxstate;
	private String img;
	private Vector target;
	

	
	public ObjectMirror(Vector pos, Vector target, String img, int maxstate) {
		this.r = new Rectangle((int)pos.x() - Button.WIDTH/2, (int) pos.y() - Button.HEIGHT/2, Button.WIDTH, Button.HEIGHT*2);
		this.maxstate = maxstate;
		this.target = target;
		this.img = img;
		state = 0;
	}
	
	@Override
	public EventEnum getType() {
		return EventEnum.MIRROR;
	}

	@Override
	public void interact() {
		if (!blocked) {
			state = (state +1)%maxstate;
		}
	}
	
	@Override
	public boolean addTarget(NPC npc) {
		if (state == 1)
			npc.addTarget(target, 0.5, ObjectInteract.empty());
		return true;
	}

	@Override
	public boolean intersects(Polygon hitbox) {
		return hitbox.intersects(r);
	}

	@Override
	public boolean isValid() {
		return true;
	}
	
	@Override
	public void unlock() {
		blocked = false;
	}
	
	@Override
	public Vector reflect() {
		if (state == 1) {
			return target;
		}
		return null;
	}

	@Override
	public Rectangle getRect() {
		return r;
	}

	@Override
	public String getImg() {
		return img;
	}

}
