package game;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import util.Vector;


public class Map {
	private ArrayList<Rectangle> walls = new ArrayList<>();
	private ArrayList<Rectangle> buttons = new ArrayList<>();
	
	public Map() {
		// TODO temporary
		walls.add(new Rectangle(50,50, 900,20));
		walls.add(new Rectangle(50,50, 20, 700));
		walls.add(new Rectangle(950,50, 20 ,700));
		walls.add(new Rectangle(50,750, 900,20));
		buttons.add(new Rectangle (70,70,50,50));
		buttons.add(new Rectangle (150,150,50,50));
		buttons.add(new Rectangle (200,200,50,50));
		buttons.add(new Rectangle (300,400,50,50));
		buttons.add(new Rectangle (500,500,50,50));
		
	}
	
	public List<Rectangle> getWalls(){
		return walls;
	}
	
	public boolean isNotValid(Rectangle hitbox) {
		return walls.stream().anyMatch(w -> w.intersects(hitbox));
	}
	
	public List<Rectangle> getButtons(){
		return buttons;
	}
	
	public boolean isPresed(Rectangle hitbox) {
		return buttons.stream().anyMatch(w -> w.intersects(hitbox));
	}
}
