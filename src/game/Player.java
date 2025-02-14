package game;

import java.awt.Rectangle;

import util.Direction;
import util.Vector;

public class Player {
	// movent constantes
	private final double normDir = 0.5;
	private final long dashCoolDown = 200; // in ms
	private final double EPSILON = 1; // if it's lower then it's 0
	private final double friction = 0.98;
	private final double dashSpeedMultiplicator = 2;
	
	// player variables
	private Direction dir = Direction.NULL;
	private Direction lastDir = Direction.NULL;
	private  Vector pos = new Vector(500,500);
	private Vector speed = Vector.NULL;
	private double maxSpeed = 8.5;
	private double dashSpeed = maxSpeed *dashSpeedMultiplicator;
	private long dashBeginTime = 0;

	private long lastMaj = 0;
	
	public void resetSpeed() {
		speed = new Vector(0, 0);
	}
	
	public boolean isDashing() {
		long currentTime = System.nanoTime();
	    long elapsedTime = (currentTime - dashBeginTime)/1000000; // convert fro nano to ms
		return elapsedTime <= dashCoolDown;
	}
	
	public void changeDirection(Direction dir) {
		if (canChangeDirection()) {
			if (this.dir != dir) {
				this.lastDir = this.dir;
			}
			this.dir = dir;
		}
	}
	
	public void dash() {
		if (speed == Vector.NULL) {
			speed = Vector.VectorFromDirection(lastDir).normalized(dashSpeed);
		}else {
			speed = Vector.VectorFromDirection(dir).normalized(dashSpeed);
		}
		dashBeginTime = System.nanoTime();
	}
	
	private boolean canChangeDirection() {
	    return !isDashing();
	}
	
	public void move() {
	    long currentTime = System.nanoTime();
	    long elapsedTime = (currentTime - lastMaj)/1000000 ;
	    lastMaj = currentTime;
		
		if (dir == Direction.NULL && speed.norme() <= EPSILON ) {
			speed = Vector.NULL;
		}else {
			speed = speed.normalized((speed.norme()*friction));
		}

		var v = Vector.VectorFromDirection(dir).normalized(normDir);
		var newspeed = Vector.add(speed, v);
		if (newspeed.norme() <= maxSpeed) {
			speed = newspeed;
		}else {
			speed = newspeed.normalized(speed.norme());
		}

		pos = Vector.add(pos, speed.times(elapsedTime/16));
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
