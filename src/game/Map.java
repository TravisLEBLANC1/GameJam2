package game;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import util.Vector;


public class Map {
	private ArrayList<Rectangle> walls = new ArrayList<>(); 
	
	public Map() {
		// TODO temporary
		walls.add(new Rectangle(50,50, 900,20));
		walls.add(new Rectangle(50,50, 20, 700));
		walls.add(new Rectangle(950,50, 20 ,700));
		walls.add(new Rectangle(50,750, 900,20));
		
	}
	
	public List<Rectangle> getWalls(){
		return walls;
	}
	
	public boolean isNotValid(Rectangle hitbox) {
		return walls.stream().anyMatch(w -> w.intersects(hitbox));
	}
}
