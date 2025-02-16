package game;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import game.map.Map;
import game.map.SecretTunnel;
import game.map.Wall;
import game.objects.Button;
import game.objects.ObjectFire;
import game.objects.ObjectInteract;
import game.objects.ObjectLid;
import game.objects.ObjectMirror;
import game.objects.ObjectMusic;
import game.objects.ObjectTrash;
import game.objects.ObjectTreat;
import game.objects.ObjectTreatBowl;
import game.objects.ObjectUnlock;
import game.objects.ObjectWindow;
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
	public Player player = new Player();
	public Map map = new Map(this);

    public NPC npc = new NPC(this);
	public Camera cam = new Camera(player);
	private boolean started = false;
    private boolean gameEnded = false;
    private boolean won = false;
    
    public Game(int nb) {
    	switch(nb) {
    	case 0 -> initNPC();
    	case 1 -> initNPC1();
    	case 2 -> initNPC2();
    	case 3-> initNPC3();
    	case 4-> initNPC4();
    	}
    }
    
    public void initNPC4() {
    	Vector pos;
    	ObjectInteract but1, but2;
    	
    	// objectif
    	pos = new Vector(2800, 450);
    	var butFinal = new ObjectTreatBowl(pos, player);
    	map.addButton(butFinal);
    	npc.addTarget(pos, butFinal);
    	
    	// buttons objects
    	pos = new Vector(2500, 70);
    	var butMiror = new ObjectMirror(pos, new Vector(2000, 200), "Mirror.png", 2);
    	map.addButton(butMiror);
    	
    	pos = new Vector(3000, 50);
    	var butWindow = new ObjectWindow(pos, "window", 5);
    	map.addButton(butWindow);
    	
    	pos = new Vector(620, 70);
    	var butMiror1 = new ObjectMirror(pos, new Vector(2000, 200), "Mirror.png", 2);
    	map.addButton(butMiror1);
    	
    	pos = new Vector(120, 50);
    	var butWindow1 = new ObjectWindow(pos, "window", 5);
    	map.addButton(butWindow1);
    	
    	pos = new Vector(750, 450);
    	but1 = new ObjectInteract(EventEnum.TASSE, pos, "Mug.png");
    	map.addButton(but1);
    	npc.addTarget(pos, but1);
    	
    	pos = new Vector(1500, 450);
    	var butWool = new ObjectInteract(EventEnum.WOOL, pos, "Wool_Ball.png");
    	map.addButton(butWool);
    	npc.addTarget(pos, butWool);
    	
    	pos = new Vector(3000, 700);
    	but1 = new ObjectInteract(EventEnum.FISH, pos, "Fish_Tank.png");
    	map.addButton(but1);
    	npc.addTarget(pos, but1);

    	
    	// timer objets
    	
    	// music objects
    	var but5 = new ObjectMusic(new Rectangle(1700, 650, 100, 100));
    	map.addButton(but5);
    	
    	pos = new Vector(1500, 30);
    	var butWindow4 = new ObjectWindow(pos, "window", 5);
    	map.addButton(butWindow4);
    	
    	// fire objects
    	var but4 = new ObjectFire(new Vector(1500, 120), this, butWindow4);
    	timerObjects.add(but4);
    	map.addButton(but4);
    }
    
    
    public void initNPC3() {
    	Vector pos;
    	ObjectInteract but1, but2;
    	
    	// objectif
    	pos = new Vector(2800, 450);
    	var butFinal = new ObjectTreatBowl(pos, player);
    	map.addButton(butFinal);
    	npc.addTarget(pos, butFinal);
    	
    	// buttons objects
    	pos = new Vector(2500, 70);
    	var butMiror = new ObjectMirror(pos, new Vector(2000, 200), "Mirror.png", 2);
    	map.addButton(butMiror);
    	
    	pos = new Vector(3000, 50);
    	var butWindow = new ObjectWindow(pos, "window", 5);
    	map.addButton(butWindow);
    	
    	pos = new Vector(2500, 700);
    	but1 = new ObjectInteract(EventEnum.TASSE, pos, "Mug.png");
    	map.addButton(but1);
    	npc.addTarget(pos, but1);
    	
    	pos = new Vector(1500, 650);
    	var butWool = new ObjectInteract(EventEnum.WOOL, pos, "Wool_Ball.png");
    	map.addButton(butWool);
    	npc.addTarget(pos, butWool);
    	
    	pos = new Vector(1000, 120);
    	but1 = new ObjectInteract(EventEnum.FISH, pos, "Fish_Tank.png");
    	map.addButton(but1);
    	npc.addTarget(pos, but1);

    	
    	// timer objets
    	
    	// music objects
    	var but5 = new ObjectMusic(new Rectangle(500, 650, 100, 100));
    	map.addButton(but5);
    	
    	
    	pos = new Vector(120, 700);
    	var butWindow4 = new ObjectWindow(pos, "window", 5);
    	map.addButton(butWindow4);
    	
    	// fire objects
    	var but4 = new ObjectFire(new Vector(120, 700), this, butWindow4);
    	timerObjects.add(but4);
    	map.addButton(but4);
    }
    
    public void initNPC2() {
    	Vector pos;
    	ObjectInteract but1, but2;
    	
    	// objectif
    	pos = new Vector(2800, 450);
    	var butFinal = new ObjectTreatBowl(pos, player);
    	map.addButton(butFinal);
    	npc.addTarget(pos, butFinal);
    	
    	pos = new Vector(2000, 600);
    	var butMiror = new ObjectMirror(pos, new Vector(2000, 200), "Mirror.png", 2);
    	map.addButton(butMiror);
    	
    	pos = new Vector(2000, 50);
    	var butWindow = new ObjectWindow(pos, "window", 5);
    	map.addButton(butWindow);
    	
    	pos = new Vector(1500, 120);
    	but1 = new ObjectInteract(EventEnum.TASSE, pos, "Mug.png");
    	map.addButton(but1);
    	npc.addTarget(pos, but1);
    	
    	pos = new Vector(1200, 600);
    	but1 = new ObjectInteract(EventEnum.FISH, pos, "Fish_Tank.png");
    	map.addButton(but1);
    	npc.addTarget(pos, but1);
    	
    	pos = new Vector(700, 400);
    	var butWool = new ObjectInteract(EventEnum.WOOL, pos, "Wool_Ball.png");
    	map.addButton(butWool);
    	npc.addTarget(pos, butWool);
    	
    	// timer objets
    	
    	// music objects
    	var but5 = new ObjectMusic(new Rectangle(200, 70, 100, 100));
    	map.addButton(but5);
    	
    	
    	pos = new Vector(3000, 700);
    	var butWindow4 = new ObjectWindow(pos, "window", 5);
    	map.addButton(butWindow4);
    	
    	// fire objects
    	var but4 = new ObjectFire(new Vector(3000, 700), this, butWindow4);
    	timerObjects.add(but4);
    	map.addButton(but4);
    }
    
    public void initNPC1() {
    	Vector pos;
    	ObjectInteract but1, but2;
    	
    	// objectif
    	pos = new Vector(2800, 450);
    	var butFinal = new ObjectTreatBowl(pos, player);
    	map.addButton(butFinal);
    	npc.addTarget(pos, butFinal);
    	
    	// buttons objects
    	pos = new Vector(3000, 120);
    	but1 = new ObjectInteract(EventEnum.FISH, pos, "Fish_Tank.png");
    	map.addButton(but1);
    	npc.addTarget(pos, but1);
    	
    	pos = new Vector(2400, 500);
    	var butWool = new ObjectInteract(EventEnum.WOOL, pos, "Wool_Ball.png");
    	map.addButton(butWool);
    	npc.addTarget(pos, butWool);
    	
    	pos = new Vector(2000, 600);
    	var butMiror = new ObjectMirror(pos, new Vector(2000, 200), "Mirror.png", 2);
    	map.addButton(butMiror);
    	
    	pos = new Vector(2000, 50);
    	var butWindow = new ObjectWindow(pos, "window", 5);
    	map.addButton(butWindow);
    	
    	pos = new Vector(1500, 120);
    	but1 = new ObjectInteract(EventEnum.TASSE, pos, "Mug.png");
    	map.addButton(but1);
    	npc.addTarget(pos, but1);
    	
    	// timer objets
    	
    	// music objects
    	var but5 = new ObjectMusic(new Rectangle(800, 650, 100, 100));
    	map.addButton(but5);
    	
    	pos = new Vector(3000, 700);
    	var butWindow4 = new ObjectWindow(pos, "window", 5);
    	map.addButton(butWindow4);
    	
    	// fire objects
    	var but4 = new ObjectFire(new Vector(120, 120), this, butWindow4);
    	timerObjects.add(but4);
    	map.addButton(but4);
    }
    
    public void initNPC() {
    	Vector pos;
    	ObjectInteract but1, but2;
    	// objectif
    	pos = new Vector(2800, 450);
    	var butFinal = new ObjectTreatBowl(pos, player);
    	map.addButton(butFinal);
    	npc.addTarget(pos, butFinal);
    	
    	pos = new Vector(2800, 700);
    	var lid = new ObjectLid(pos, player);
    	map.addButton(lid);
    	
    	// buttons objects
    	pos = new Vector(2400, 500);
    	but1 = new ObjectInteract(EventEnum.FISH, pos, "Fish_Tank.png");
    	map.addButton(but1);
    	npc.addTarget(pos, but1);
    	
    	
    	pos = new Vector(2000, 50);
    	var butWindow = new ObjectWindow(pos, "window", 5);
    	map.addButton(butWindow);
    	
    	pos = new Vector(2100, 100);
    	npc.addTarget(pos, 3.0, butWindow);
    	
    	pos = new Vector(2000, 600);
    	var butMiror = new ObjectMirror(pos, new Vector(2000, 500), "Mirror.png", 2);
    	map.addButton(butMiror);
    	
    	pos = new Vector(1902, 100);
    	npc.addTarget(pos,5.0, butMiror);
    	pos = new Vector(1900, 100);
    	npc.addTarget(pos, butWindow);


    	
    	pos = new Vector(1500, 700);
    	var unlockMirror = new ObjectUnlock(butMiror);
    	npc.addTarget(pos, unlockMirror);
    	
    	pos = new Vector(1480, 500);
    	var bin = new ObjectTrash(pos, player);
    	map.addButton(bin);
    	npc.addTarget(pos, bin);
    	
		pos = new Vector(1000, 650);
		var table = new ObjectInteract(EventEnum.NOEV, pos, "Table.png");
		map.addButton(table);
    	
    	pos = new Vector(1140, 630);
    	but1 = new ObjectInteract(EventEnum.TASSE, pos, "Mug.png");
    	map.addButton(but1);
    	npc.addTarget(pos, but1);
    	
    	pos = new Vector(950, 700);
    	var unlockMug = new ObjectUnlock(but1);
    	npc.addTarget(pos, unlockMug);
    	npc.addTarget(new Vector(400, 600), ObjectInteract.empty());
    	

    	
    	pos = new Vector(350, 450);
    	var butWool = new ObjectInteract(EventEnum.WOOL, pos, "Wool_Ball.png");
    	map.addButton(butWool);
    	npc.addTarget(pos, butWool);
    	
    	npc.addTarget(new Vector(100, 200), 1.0, ObjectInteract.empty());	

    	
    	pos = new Vector(200, 170);
    	lid = new ObjectLid(pos, player);
    	map.addButton(lid);
    	
    	List<Vector> treats = List.of(new Vector(1000, 400), new Vector(1500, 600));
    	
    	for (var v: treats) {
    		map.addButton(new ObjectTreat(v, player));
    	}
    	
    	
		var tunnel1 = new SecretTunnel(new Rectangle(300, 30, 200, 200), player);
		var tunnel2 = new SecretTunnel(new Rectangle(2500, 30, 200, 200), player);
		tunnel1.setSibling(tunnel2);
		tunnel2.setSibling(tunnel1);
		map.addButton(tunnel1);
		map.addButton(tunnel2);
    	
		
		
		pos = new Vector(1030, 75);
		var door = new ObjectInteract(EventEnum.NOEV, pos, "Door.png");
		map.addButton(door);
		
		pos = new Vector(2900, 100);
		var tree = new ObjectInteract(EventEnum.NOEV, pos, "Cat_Tree.png");
		map.addButton(tree);
		
		pos = new Vector(800, 350);
		tree = new ObjectInteract(EventEnum.NOEV, pos, "Cat_Tree.png");
		map.addButton(tree);

    	// timer objets
    	
    	pos = new Vector(650, 50);
    	butWindow = new ObjectWindow(pos, "window", 1);
    	map.addButton(butWindow);
    	
    	var but4 = new ObjectFire(new Vector(650, 150), this, butWindow);
    	timerObjects.add(but4);
    	map.addButton(but4);
    	
    	pos = new Vector(1200, 50);
    	butWindow = new ObjectWindow(pos, "window", 5);
    	map.addButton(butWindow);
    	

    	
    	// music objects
    	var but5 = new ObjectMusic(new Rectangle(50, 650, 100, 100));
    	map.addButton(but5);
    }
    
	public void start() {
		started = true;
		updateTimer.start();
		player.resetSpeed();
		npc.resetSpeed();
		timerObjects.forEach(b -> b.start());
		cam.fix(map.objectif());
	}
	
	public void stop() {
		updateTimer.stop();
		updateTimer = null;
		map = null;
		player = null;
		timerObjects.forEach(b -> b.stop());
		timerObjects.clear();
		SoundPlayer.stop(SoundEnum.PRRR);
		SoundPlayer.stop(SoundEnum.FIRE);
	}
	
	public void stopFire() {
		for (var b : timerObjects) {
			b.stop();
		}
		SoundPlayer.stop(SoundEnum.FIRE);
	}
	
	public List<Vector> getFires(){
		return timerObjects.stream()
				.filter(b -> b.isOnFire())
				.map(b -> new Vector(b.getRect().x, b.getRect().y))
				.toList();
	}
	
	public double getFireLevel() {
		double max = 0;
		for (var f : timerObjects) {
			if (f.getFireLevel() >= max) {
				max = f.getFireLevel();
			}
		}
		return max;
	}
	
	public void translocator() {
		if (player.translocator()) {
			cam.teleport();
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
		}
	}
	
	
	public void interaction(Direction dir) {
		if (!started) {
			start();
			GameGraphic.gameScreen();
		}
		map.clickButton(player.getHitbox());
	}
	
	public void loose(EventEnum event) {
		if (!gameEnded) {
			GameGraphic.looserScreen(event);
			gameEnded = true;
			npc.stop();
			SoundPlayer.play(SoundEnum.PRRR);
		}
		
	}
	
	public void win() {
		GameGraphic.winnerScreen();
		stopFire();
		gameEnded = true;
		npc.stop();
		won = true;
		SoundPlayer.play(SoundEnum.PRRR);
	}
	
	
	public boolean isWon() {
		return won;
	}

}
