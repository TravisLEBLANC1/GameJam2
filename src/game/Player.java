package game;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import util.Direction;
import util.Vector;

public class Player {
	// movent constantes
	private final double normDir = 0.5;
	private final long dashCoolDown = 200; // in ms
	private final double EPSILON = 1; // if it's lower then it's 0
	private final double friction = 0.98;
	private final double dashSpeedMultiplicator = 2;
	private final double hitboxShift = 12;
	
	// player variables
	private Direction dir = Direction.NULL;
	private Direction lastDir = Direction.NULL;
	private Direction dashDir = Direction.NULL;
	private  Vector prev = new Vector(500,500);
	private  Vector pos = new Vector(550,500);
	private Vector speed = Vector.NULL;
	private double maxSpeed = 6.5;
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
		if (this.dir != dir) {
			this.lastDir = this.dir;
		}
		this.dir = dir;
	}
	
	public void dash() {
		if (speed == Vector.NULL) {
			dashDir = lastDir;
		}else {
			dashDir = dir;
		}
		speed = Vector.VectorFromDirection(dashDir).normalized(dashSpeed);
		dashBeginTime = System.nanoTime();
	}
	
	private boolean canChangeDirection() {
	    return !isDashing();
	}
	
	private void changeSpeed() {
		if (dir == Direction.NULL && speed.norme() <= EPSILON ) {
			speed = Vector.NULL;
		}else {
			speed = speed.normalized((speed.norme()*friction));
		}
		Direction d = getDir();

		var v = Vector.VectorFromDirection(d).normalized(normDir);
		var newspeed = Vector.add(speed, v);
		if (newspeed.norme() <= maxSpeed) {
			speed = newspeed;
		}else {
			speed = newspeed.normalized(speed.norme());
		}
	}
	
	public void move() {
		// speed managment

		changeSpeed();
		
		// actual movement
	    long currentTime = System.nanoTime();
	    long elapsedTime = (currentTime - lastMaj)/1000000 ;
	    lastMaj = currentTime;
		prev = pos;
		pos = Vector.add(pos, speed.times(elapsedTime/(double) 16));
	}

	public void urgenceMove() {
		// move without elapsed Time
		pos = Vector.add(pos, speed);
	}
	
	public void bounce(Vector normal) {
		speed = Vector.sub(speed, normal.times(2*Vector.scalar(speed, normal)));
		System.out.println("bounce " + speed);
		move();
	}
	
	public void bounceOpposite() {
		speed = speed.opposite();
	}
	
	public Vector getPos() {
		return pos;
	}
	
	public Vector getPrevPos() {
		return prev;
	}
	
	public Vector getSpeed() {
		return speed;
	}
	
	public Direction getDir() {
		if (isDashing()) {
			return dashDir;
		}else {
			return dir;
		}
	}
	
	public Line2D getMovementLine() {
		return new Line2D.Double(prev.x(), prev.y(), pos.x(), pos.y());
	}
	
	public void teleport(Vector pos) {
		this.pos = pos;
	}
	
	public Polygon getHitbox() {
		int[] xpoints = {(int) (pos.x() -25 + hitboxShift), (int) (pos.x() +25 - hitboxShift), 
						(int) (pos.x() +25), (int) (pos.x() +25), 
						(int) (pos.x() +25 - hitboxShift), (int) (pos.x() -25 + hitboxShift),
						(int) (pos.x() -25), (int) (pos.x() -25)};
		int[] ypoints = {(int) (pos.y() -25), (int) (pos.y() -25), 
				(int) (pos.y() -25 + hitboxShift), (int) (pos.y() +25 - hitboxShift), 
				(int) (pos.y() +25 ), (int) (pos.y() +25 ),
				(int) (pos.y() +25 - hitboxShift), (int) (pos.y() -25 + hitboxShift)};
		return new Polygon(xpoints, ypoints, 8);
	}
}
