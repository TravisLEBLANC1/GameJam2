package game.objects;

import java.awt.Polygon;
import java.awt.Rectangle;

import game.EventEnum;
import game.Player;
import util.Vector;

public class ObjectTreat implements Button {
	private Rectangle r;
	private Player p;
	boolean asTreats = true;
	public ObjectTreat(Vector pos, Player p) {
		this.r = new Rectangle((int)pos.x() - Button.WIDTH/2, (int) pos.y() - Button.HEIGHT/2, Button.WIDTH*3/4, Button.HEIGHT*3/4);
		this.p = p;
	}
	
	@Override
	public EventEnum getType() {
		return EventEnum.TREAT;
	}

	@Override
	public void interact() {
		p.addTreat(1);
		asTreats = false;
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
	public Rectangle getRect() {
		if (!asTreats) return Button.OUT;
		return r;
	}

	@Override
	public String getImg() {
		return "Treat.png";
	}

}
