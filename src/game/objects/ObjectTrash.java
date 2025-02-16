package game.objects;

import java.awt.Polygon;
import java.awt.Rectangle;

import game.EventEnum;
import game.Player;
import util.Vector;

public class ObjectTrash implements Button {
	private Rectangle r;
	private Player p;
	boolean asLid = false;
	public ObjectTrash(Vector pos, Player p) {
		this.r = new Rectangle((int)pos.x() - Button.WIDTH/2, (int) pos.y() - Button.HEIGHT/2, Button.WIDTH, Button.HEIGHT);
		this.p = p;
	}
	
	@Override
	public EventEnum getType() {
		return EventEnum.TRASH;
	}

	@Override
	public void interact() {
		if (p.hasLid()) {
			p.takeLid();
			asLid = true;
		}
	}

	@Override
	public boolean intersects(Polygon hitbox) {
		return hitbox.intersects(r);
	}

	@Override
	public boolean isValid() {
		return asLid;
	}

	@Override
	public Rectangle getRect() {
		return r;
	}

	@Override
	public String getImg() {
		if (asLid )
			return "Bin1";
		else
			return "Bin0";
	}
}