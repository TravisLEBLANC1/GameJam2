package game;

import util.Direction;
import util.Vector;

public class Game {
	public Map map = new Map();
	public Player player = new Player();
	
	public void majCharacter(Direction dir) {
		var old = player.getPos();
		player.move(dir);
		if (map.isNotValid(player.getHitbox())) {
			player.teleport(old);
		}
	}
}
