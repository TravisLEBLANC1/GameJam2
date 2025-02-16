package game;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import game.objects.Button;
import game.objects.ObjectFire;
import game.objects.ObjectInteract;
import game.objects.ObjectMusic;
import game.objects.ObjectUnlock;
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
    
    
    public void initNPC() {
    	// buttons objects
    	var pos = new Vector(2000, 450);
    	var but1 = new ObjectInteract(EventEnum.FISH, pos, "Fish_Tank.png");
    	map.addButton(but1);
    	npc.addTarget(pos, but1);
    	
    	npc.addTarget(new Vector(1500, 700), ObjectInteract.empty());
    	
    	pos = new Vector(1110, 700);
    	but1 = new ObjectInteract(EventEnum.TASSE, pos, "Mug.png");
    	map.addButton(but1);
    	npc.addTarget(pos, but1);
    	
    	pos = new Vector(950, 700);
    	var but2 = new ObjectUnlock(but1);
    	npc.addTarget(pos, but2);
    	npc.addTarget(new Vector(400, 600), ObjectInteract.empty());
    	
    	pos = new Vector(350, 450);
    	but1 = new ObjectInteract(EventEnum.WOOL, pos, "Wool_Ball.png");
    	map.addButton(but1);
    	npc.addTarget(pos, but1);
    	
    	npc.addTarget(new Vector(100, 200), ObjectInteract.empty());
    	

    	
    	
    	// timer objets
    	var but3 = new ObjectFire(new Vector(650, 150), this);
    	timerObjects.add(but3);
    	map.addButton(but3);
    	
    	// music objects
    	var but4 = new ObjectMusic(new Rectangle(50, 650, 100, 100));
    	map.addButton(but4);
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
	
	
	
	public void event(Button event) {
		if (!map.event(event)) {
			loose(event.getType());
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
