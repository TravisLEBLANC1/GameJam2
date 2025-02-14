package game;

import javax.swing.Timer;

import util.Direction;
import util.Vector;

public class Game {
    public static final int TIC = 1000/60; // nb ms for a tic on the game (each tic is an update of the characters)
    private Timer updateTimer = new Timer(TIC, e -> updateCharacters());
	public Map map = new Map();
	public Player player = new Player();
	
	public void start() {
		updateTimer.start();
		player.resetSpeed();
	}
	
	public void majCharacter(Direction dir) {
		player.changeDirection(dir);
	}
	
	private void updateCharacters() {
		var old = player.getPos();
		player.move();
		if (map.isNotValid(player.getHitbox())) {
			player.teleport(old);
			player.resetSpeed();
		}
	}
}
