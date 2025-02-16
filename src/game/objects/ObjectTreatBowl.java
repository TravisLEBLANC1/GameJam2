package game.objects;

import java.awt.Polygon;
import java.awt.Rectangle;

import game.EventEnum;
import game.Player;
import util.Vector;

public class ObjectTreatBowl implements Button {
	private Rectangle r;
	private Player p;
	private int nbtreats = 0;
	boolean asTreats = true;
	public ObjectTreatBowl(Vector pos, Player p) {
		this.r = new Rectangle((int)pos.x() - Button.WIDTH/2, (int) pos.y() - Button.HEIGHT/2, Button.WIDTH*3/4, Button.HEIGHT*3/4);
		this.p = p;
	}
	
	@Override
	public EventEnum getType() {
		return EventEnum.TREATBOWL;
	}

	@Override
	public void interact() {
		nbtreats += p.takeTreats();
	}

	@Override
	public boolean intersects(Polygon hitbox) {
		return hitbox.intersects(r);
	}

	@Override
	public boolean isValid() {
		return nbtreats > 0;
	}

	@Override
	public Rectangle getRect() {
		if (!asTreats) return Button.OUT;
		return r;
	}

	@Override
	public String getImg() {
		if (nbtreats > 0)
			return "Bowl1";
		else
			return "Bowl0";
	}

}
