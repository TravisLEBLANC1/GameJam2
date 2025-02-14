package game;

import java.awt.Rectangle;

import util.Direction;
import util.Vector;

public class Player {
	private Direction dir = Direction.NULL;
	private  Vector pos = new Vector(500,500);
	private Vector speed = Vector.NULL;
	private double maxSpeed = 10;
	private double desceleration = 0.1;
	private long lastMaj = 0;
	
	public void resetSpeed() {
		speed = new Vector(0, 0);
	}
	
	public void changeDirection(Direction dir) {
		this.dir = dir;

	}
	
	public void move() {
	    long currentTime = System.nanoTime();
	    long elapsedTime = (currentTime - lastMaj)/1000000 ;
	    lastMaj = currentTime;
		pos = Vector.add(pos, speed.times(elapsedTime/8));
		if (dir == Direction.NULL) {
			if (speed.norme() <= desceleration ) {
				speed = Vector.NULL;
			}else {
			speed = speed.times((speed.norme()-desceleration)/speed.norme()); 
				}
		}else {
			var v = Vector.VectorFromDirection(dir).normalized(0.5);
			var newspeed = Vector.add(speed, v);
			if (newspeed.norme() <= maxSpeed) {
				speed = newspeed;
				System.out.println(speed.norme());
			}
		}
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
