package game;

import java.awt.Rectangle;

import util.Direction;
import util.Vector;

public class Player {
	private  Vector pos = new Vector(500,500);
	
	public void move(Direction dir) {
		var v = Vector.VectorFromDirection(dir).times(10);
		pos = Vector.add(pos, v);
	}
	
	public Vector getPos() {
		return pos;
	}
	
	public void teleport(Vector pos) {
		this.pos = pos;
	}
	
	public Rectangle getHitbox() {
		return new Rectangle((int) pos.x(), (int) pos.y(), 50, 50);
	}
}
