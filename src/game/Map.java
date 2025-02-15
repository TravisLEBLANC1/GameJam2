package game;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;


public class Map {
	private ArrayList<Wall> walls = new ArrayList<>(); 
	private ArrayList<Rectangle> buttons = new ArrayList<>();
	public Map() {
		// TODO temporary
		walls.add(new Wall(new Rectangle(50,50, 900,20)));
		walls.add(new Wall(new Rectangle(50,50, 20, 700)));
		walls.add(new Wall(new Rectangle(950,50, 20 ,720)));
		walls.add(new Wall(new Rectangle(50,750, 900,20)));
		int[] xpoints = {200, 500, 480, 180};
		int[] ypoints = {200, 500, 520, 220};
		walls.add(new Wall(xpoints, ypoints, 4));
    buttons.add(new Rectangle (70,70,50,50));
		buttons.add(new Rectangle (150,150,50,50));
		buttons.add(new Rectangle (200,200,50,50));
		buttons.add(new Rectangle (300,400,50,50));
		buttons.add(new Rectangle (500,500,50,50));

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
	
	public List<Rectangle> getButtons(){
		return buttons;
	}
	
	public boolean isPresed(Rectangle hitbox) {
		return buttons.stream().anyMatch(w -> w.intersects(hitbox));
	}
}
