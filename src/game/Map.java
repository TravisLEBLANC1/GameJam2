package game;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;


public class Map {
	private ArrayList<Wall> walls = new ArrayList<>(); 
	
	public Map() {
		// TODO temporary
		walls.add(new Wall(new Rectangle(50,50, 900,20)));
		walls.add(new Wall(new Rectangle(50,50, 20, 700)));
		walls.add(new Wall(new Rectangle(950,50, 20 ,700)));
		walls.add(new Wall(new Rectangle(50,750, 900,20)));
		
	}
	
	public List<Wall> getWalls(){
		return walls;
	}
	
	public Wall touchWall(Player player) {
		int edge;
		for (var wall: walls) {
			if (wall.intersects(player.getHitbox())) {
				return wall;
			}
		}
		return null;
//		return walls.stream().anyMatch(w -> w.intersects(hitbox));
	}
}
