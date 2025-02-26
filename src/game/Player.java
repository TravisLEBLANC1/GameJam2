package game;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import sound.SoundEnum;
import sound.SoundPlayer;
import util.Direction;
import util.Vector;

public class Player {
	// movent constantes
	private final double normDir = 0.5;
	private final long dashBlocked = 200; // in ms
	private final long translocatorTime = 3000; // in ms
	private final long dashCoolDown = 800; // in ms
	private final long translocatorCoolDown = 10000; // in ms
	private final long stunedTime = 40;

	
	private final double EPSILON = 1; // if it's lower then it's 0
	private final double friction = 0.98;
	private final double dashSpeedMultiplicator = 2.5;
	private final double hitboxShift = 12;
	
	public static final int WIDTH = 34;
	public static final int HEIGHT = 24;
	
	// player variables
	private Direction dir = Direction.NULL;
	private Direction lastDir = Direction.NULL;
	private Direction dashDir = Direction.NULL;
	private Vector translocatorPos = null;
	private Vector pos = new Vector(550,500);
	private Vector speed = Vector.NULL;
	private double maxSpeed = 8;
	private double dashSpeed = maxSpeed *dashSpeedMultiplicator;
	private long dashBeginTime = 0;
	private long transloBeginTime = 0;
	private long stunedBeginTime = 0;
	private boolean isStuned = false;

	private long lastMaj = System.nanoTime();
	
	public void resetSpeed() {
		speed = new Vector(0, 0);
	}
	
	public boolean isDashing() {
		long currentTime = System.nanoTime();
	    long elapsedTime = (currentTime - dashBeginTime)/1000000; // convert fro nano to ms
		return elapsedTime <= dashBlocked;
	}
	
	public boolean canDash() {
		long currentTime = System.nanoTime();
	    long elapsedTime = (currentTime - dashBeginTime)/1000000; // convert fro nano to ms
		return elapsedTime > dashCoolDown;
	}
	
	public boolean isTranslocator() {
		long currentTime = System.nanoTime();
	    long elapsedTime = (currentTime - transloBeginTime)/1000000; // convert fro nano to ms
		return elapsedTime <= translocatorTime;
	}
	
	public boolean canTranslocator() {
		long currentTime = System.nanoTime();
	    long elapsedTime = (currentTime - transloBeginTime)/1000000; // convert fro nano to ms
		return elapsedTime > translocatorCoolDown;
	}
	
	public void changeDirection(Direction dir) {
		if (this.dir != dir) {
			this.lastDir = this.dir;
		}
		this.dir = dir;
	}
	
	public boolean dash() {
		if (canDash()) {
			if (speed == Vector.NULL) {
				dashDir = lastDir;
			}else {
				dashDir = dir;
			}
			speed = Vector.VectorFromDirection(dashDir).normalized(dashSpeed);
			dashBeginTime = System.nanoTime();
			return true;
		}
		return false;
	}
	
	public boolean translocator() {
		if (canTranslocator()) {
			transloBeginTime = System.nanoTime();
			translocatorPos = pos;
			return true;
		}
		return false;
	}
	
	private void checkTranslocator() {
		if (translocatorPos != null && !isTranslocator()) {
			pos = translocatorPos;
			translocatorPos = null;
			SoundPlayer.play(SoundEnum.TELEPORT);
		}
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
	    long currentTime = System.nanoTime();
	    long elapsedTime = (currentTime - lastMaj)/1000000 ;
	    lastMaj = currentTime;
		checkTranslocator();
	    if (isStuned) {
	    	stunedMaj();
	    	return;
	    }
		// speed managment
		changeSpeed();
		
		// actual movement


		pos = Vector.add(pos, speed.times(elapsedTime/(double) 16));
	}

	public void urgenceMove() {
		// move without elapsed Time
		pos = Vector.add(pos, speed);
	}
	
	public void bounce(Vector normal) {
		// System.out.println("scalar = " + normal.times(2*Vector.scalar(speed, normal)));
		speed = Vector.sub(speed, normal.times(2*Vector.scalar(speed, normal)));
		// System.out.println("speed = " +speed);
		isStuned = true;
		stunedBeginTime = System.nanoTime();
	}
	
	public void bounceOpposite() {
		speed = speed.opposite();
		isStuned = true;
		stunedBeginTime = System.nanoTime();
	}
	
	public void stunedMaj() {
		long currentTime = System.nanoTime();
	    long elapsedTime = (currentTime - stunedBeginTime)/1000000; // convert fro nano to ms
		if(elapsedTime > stunedTime) {
			isStuned = false;
		}
	}
	
	public Vector getPos() {
		return pos;
	}
	
	public Vector getTranslocatorPos() {
		return translocatorPos;
	}
	
	public int getTranslocatorTime() {
		return Math.max((int) ((translocatorTime - (System.nanoTime() - transloBeginTime)/1000000)*(double)360/translocatorTime), 0);
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
	
	
	public void teleport(Vector pos) {
		this.pos = pos;
	}
	
	public Polygon getHitbox() {
//		int[] xpoints = {(int) (pos.x() -WIDTH/2 + hitboxShift), (int) (pos.x() +WIDTH/2 - hitboxShift), 
//						(int) (pos.x() +WIDTH/2), (int) (pos.x() +WIDTH/2), 
//						(int) (pos.x() +WIDTH/2 - hitboxShift), (int) (pos.x() -WIDTH/2 + hitboxShift),
//						(int) (pos.x() -WIDTH/2), (int) (pos.x() -WIDTH/2)};
//		int[] ypoints = {(int) (pos.y() -HEIGHT/2), (int) (pos.y() -HEIGHT), 
//				(int) (pos.y() -HEIGHT + hitboxShift), (int) (pos.y() +HEIGHT/2 - hitboxShift), 
//				(int) (pos.y() +HEIGHT ), (int) (pos.y() +HEIGHT ),
//				(int) (pos.y() +HEIGHT/2 - hitboxShift), (int) (pos.y() -HEIGHT/2+ hitboxShift)};
//		return new Polygon(xpoints, ypoints, 8);
		
		int[] xpoints = {(int) (pos.x() -WIDTH/2 ), (int) (pos.x() +WIDTH/2), 
				(int) (pos.x() +WIDTH/2 ), (int) (pos.x() -WIDTH/2 )};
		int[] ypoints = {(int) (pos.y() -HEIGHT/2 ), (int) (pos.y() -HEIGHT/2), 
				(int) (pos.y() +HEIGHT/2 ), (int) (pos.y() +HEIGHT/2 )};
		return new Polygon(xpoints, ypoints, 4);
	}
	
	
}