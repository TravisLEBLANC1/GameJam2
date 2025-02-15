package game;

import javax.swing.Timer;

import graphism.Camera;
import graphism.sprite.SpriteContainer;
import sound.SoundEnum;
import sound.SoundPlayer;
import util.Direction;

public class Game {
    public static final int TIC = 1000/60; // nb ms for a tic on the game (each tic is an update of the characters)
    private Timer updateTimer = new Timer(TIC, e -> updateCharacters());
	public Map map = new Map();
	public Player player = new Player();
  public NPC npc = new NPC();
	public Camera cam = new Camera(player);
    private boolean running = true;
    
    CountdownTimer timer = new CountdownTimer(10);
    
	public void start() {
		updateTimer.start();
		player.resetSpeed();
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
	
	public void interaction(Direction dir) {
		if (map.isPresed(player.getHitbox())) {
			npc.skip();
			timer.start();
			System.out.println("Implémenter actions");
		}
	}
	
	public void loose() {
		System.out.println("You loose !");
	}
}
