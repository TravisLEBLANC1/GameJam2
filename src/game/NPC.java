package game;

import java.awt.Polygon;
import java.util.List;
import java.util.ArrayList;
import util.Vector;

public class NPC {
    
	private final double hitboxShift = 12;
    
    public static final int WIDTH = 50;
    
	// npc variables
    private Vector pos = new Vector(500, 500);
    private Vector speed = Vector.NULL;
    private int currentTargetIndex = 0;
    private boolean isMoving = true;
    
    private double maxSpeed = 2.0;
	private long lastMaj = System.nanoTime();
	
	private long startTime = System.nanoTime();
	
	public void move() {
        if (!isMoving) {
            return;
        }

	    List<Vector> targets = new ArrayList<>();
        targets.add(new Vector(700, 700));
        targets.add(new Vector(300, 500));
        targets.add(new Vector(900, 300));

        // actual movement
	    long currentTime = System.nanoTime();
	    long elapsedTime = (currentTime - lastMaj) / 1000000;
	    lastMaj = currentTime;
	    
	    if (targets.isEmpty()) return; 
	    
	    Vector targetPos = targets.get(currentTargetIndex);
        // Target Direction
        Vector direction = Vector.sub(targetPos, pos);
        double distance = direction.norme();

        if (distance > maxSpeed) {
            speed = direction.normalized(maxSpeed);
        } else {
            speed = direction;
        }
	    
        pos = Vector.add(pos, speed.times(elapsedTime/(double) 16));
		
        if (distance < maxSpeed) {
            System.out.println("NPC reach target : " + targetPos);
            currentTargetIndex++;
            if (currentTargetIndex >= targets.size()) {
                System.out.println("Tu as perdu, tu es null !");
//                currentTargetIndex = 0;
                isMoving = false;
            }
        }
        
        // Timer
        
	}
	
	public void skip() {
		currentTargetIndex++;
	}
	
	public Vector getPos() {
		return pos;
	}

	public Polygon getHitbox() {
		int[] xpoints = {(int) (pos.x() - 25 + hitboxShift), (int) (pos.x() + 25 - hitboxShift), 
						(int) (pos.x() + 25), (int) (pos.x() + 25), 
						(int) (pos.x() + 25 - hitboxShift), (int) (pos.x() - 25 + hitboxShift),
						(int) (pos.x() - 25), (int) (pos.x() - 25)};
		int[] ypoints = {(int) (pos.y() - 25), (int) (pos.y() - 25), 
				(int) (pos.y() - 25 + hitboxShift), (int) (pos.y() + 25 - hitboxShift), 
				(int) (pos.y() + 25 ), (int) (pos.y() + 25 ),
				(int) (pos.y() + 25 - hitboxShift), (int) (pos.y() - 25 + hitboxShift)};
		return new Polygon(xpoints, ypoints, 8);
	}
}
