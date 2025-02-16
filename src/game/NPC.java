package game;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import game.objects.Button;
import game.objects.ObjectInteract;
import util.Vector;

public class NPC {
    
	private final double hitboxShift = 12;
    
    public static final int WIDTH = 50;
    
	// npc variables
    private Vector pos = new Vector(100, 600);
    private Vector speed = Vector.NULL;
    private int currentTargetIndex = 0;
    private boolean isMoving = true;
    
    private double maxSpeed = 2.0;
	private long lastMaj = System.nanoTime();
	
	private long startTime = System.nanoTime();
	private ArrayList<Vector> targets = new ArrayList<>();
	private ArrayList<Button> events = new ArrayList<>();
	private Game game;
	
	public NPC(Game game) {
		this.game = game;
	}
	
	public void addTarget(Vector v, Button event) {
		targets.add(v);
		events.add(event);
	}
	
	public void move() {
	    long currentTime = System.nanoTime();
	    long elapsedTime = (currentTime - lastMaj) / 1000000;
	    lastMaj = currentTime;
        if (!isMoving) {
            return;
        }
	    if (targets.isEmpty()) return; 
	    
	    Vector targetPos = targets.getLast();
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
            targets.removeLast();
            if (events.getLast().getType() != EventEnum.NOEV) {
            	game.event(events.getLast());
            }
            events.removeLast();
        }        
	}
	
	public void skip() {
		currentTargetIndex++;
	}
	
	public Vector getPos() {
		return pos;
	}
	
	public List<Vector> getTargets(){
		return targets;
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
