package game;

import java.awt.Polygon;

import util.Vector;

public class NPC {
    
	private final double hitboxShift = 12;
    
    public static final int WIDTH = 50;
    
	// npc variables
	private Vector pos = new Vector(500,500);
	private Vector speed = new Vector(1,0);
	
	private long lastMaj = 0;
    
	public void move() {	
		// actual movement
	    long currentTime = System.nanoTime();
	    long elapsedTime = (currentTime - lastMaj)/1000000 ;
	    lastMaj = currentTime;
//		pos = Vector.add(pos, speed.times(elapsedTime/(double) 16));
		pos = Vector.add(pos, speed);
		System.out.println("CurrentTime : " + currentTime);
		System.out.println("elapsedTime : " + elapsedTime);
		System.out.println("speed * elapsedTime : " + speed.times(elapsedTime/(double) 16));
		System.out.println("speed NPC : " + pos);
	}
    
	public Vector getPos() {
		return pos;
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
