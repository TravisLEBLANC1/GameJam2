package game;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import graphism.Camera;
import graphism.GameGraphic;
import graphism.sprite.SpriteContainer;
import sound.SoundEnum;
import sound.SoundPlayer;
import util.Direction;
import util.Vector;

public class Game {
    public static final int TIC = 1000/60; // nb ms for a tic on the game (each tic is an update of the characters)
    private Timer updateTimer = new Timer(TIC, e -> updateCharacters());
    private ArrayList<ObjectFire> timerObjects = new ArrayList<>();
	public Map map = new Map();
	public Player player = new Player();

    public NPC npc = new NPC(this);
	public Camera cam = new Camera(player);
    private boolean running = true;
    
    
    public void initNPC() {
    	// buttons objects
    	
    	var but1 = new ObjectInteract(EventEnum.FISH, new Rectangle (1900,450,50,50));
    	map.addButton(but1);
    	npc.addTarget(new Vector(2000, 450), but1);
    	
    	npc.addTarget(new Vector(1500, 700), ObjectInteract.empty());
    	
    	but1 = new ObjectInteract(EventEnum.TASSE, new Rectangle (1090,700,50,50));
    	map.addButton(but1);
    	npc.addTarget(new Vector(1110, 700), but1);
    	
    	var but2 = new ObjectInteract(EventEnum.UNLOCKTASSE, new Rectangle (), but1, 2);
    	npc.addTarget(new Vector(950, 700), but2);
    	npc.addTarget(new Vector(400, 600), ObjectInteract.empty());
    	
    	but1 = new ObjectInteract(EventEnum.FISH, new Rectangle (300,400,50,50));
    	map.addButton(but1);
    	npc.addTarget(new Vector(350, 450), but1);
    	
    	npc.addTarget(new Vector(100, 200), ObjectInteract.empty());
    	

    	
    	
    	// timer objets
    	var but3 = new ObjectFire(EventEnum.FIRE, new Rectangle (600,100,50,50), this);
    	timerObjects.add(but3);
    	map.addButton(but3);
    }
    
	public void start() {
		updateTimer.start();
		player.resetSpeed();
		initNPC();
	}
	
	public void stop() {
		updateTimer.stop();
		updateTimer = null;
		map = null;
		player = null;
		timerObjects.forEach(b -> b.stop());
		timerObjects.clear();
	}
	
	public void stopFire() {
		for (var b : timerObjects) {
			if (b.isOnFire()) {
				return;
			}
		}
		SoundPlayer.stop(SoundEnum.FIRE);
	}
	
	public List<Vector> getFires(){
		return timerObjects.stream()
				.filter(b -> b.isOnFire())
				.map(b -> new Vector(b.getRect().x, b.getRect().y))
				.toList();
	}
	
	public void translocator() {
		var old = player.getPos();
		if (player.translocator()) {
			cam.teleport(old);
		}
	}
	
	public void dash() {
		if (player.dash()) {
			SpriteContainer.dash(player.getDir());
			SoundPlayer.play(SoundEnum.DASH);
		}
	}
	
	public void majCharacter(Direction dir) {
		player.changeDirection(dir);
	}
	
	private void updateCharacters() {
		var old = player.getPos();
		player.move();
		npc.move();
		var wall = map.touchWall(player);
		if (wall != null) {
			
			int edge = wall.getCollidingEdge(player);
			if (edge == Wall.NOTFOUND) {
				// should not happen
//				System.out.println("hmmm...");
			}else {
				var bonkdir = Direction.closest(player.getSpeed().x(), player.getSpeed().y());
				if (edge == Wall.CORNER){
					player.bounceOpposite();
					// System.out.println("corner");
				}else {
					var normal = wall.getEdgeNormal(edge);
					player.bounce(normal);
					
				}
				player.teleport(old);
				SpriteContainer.bonk(bonkdir);
				SoundPlayer.play(SoundEnum.BONK);
			}
		}
//		if (map.isPresed(player.getHitbox())) {
//			System.out.println("Presed");
//		}
		cam.maj();
	}
	
	
	
	public void event(ObjectInteract event) {
		if (!map.event(event)) {
			loose(event.type);
		}else {
			System.out.println("good!");
		}
	}
	
	
	public void interaction(Direction dir) {
		map.clickButton(player.getHitbox());
	}
	
	public void loose(EventEnum event) {
		System.out.println("You loose because of " + event);
		GameGraphic.looserScreen(event);
	}

}
