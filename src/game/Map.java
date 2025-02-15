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
		walls.add(new Wall(new Rectangle(50,50, 3000,20)));
		walls.add(new Wall(new Rectangle(50,50, 20, 700)));
		walls.add(new Wall(new Rectangle(3050,50, 20 ,720)));
		walls.add(new Wall(new Rectangle(50,750, 3000,20)));
		int[] xpoints = {200, 500, 480, 180};
		int[] ypoints = {200, 500, 520, 220};
		walls.add(new Wall(xpoints, ypoints, 4));

		buttons.add(new Rectangle (300,400,50,50));

		xpoints = new int[]{1500, 1800, 1780, 1480};
		ypoints = new int[]{200, 500, 520, 220};
		walls.add(new Wall(xpoints, ypoints, 4));
		buttons.add(new Rectangle (300,400,50,50));
		
		buttons.add(new Rectangle (300,400,50,50));
		
		walls.add(new Wall(new Rectangle(900,200, 40, 400)));
		walls.add(new Wall(new Rectangle(2000,200, 40, 400)));
		
		
		walls.add(new Wall(new Rectangle(2800,50, 30,100)));
		walls.add(new Wall(new Rectangle(2900,50, 30 ,100)));

//		walls.add(new Wall(new Rectangle(50,50, 20, 700)));
//		walls.add(new Wall(new Rectangle(50,50, 20, 700)));

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
	
	public boolean isPresed(Polygon hitbox) {
		return buttons.stream().anyMatch(w -> hitbox.intersects(w));
	}
}
