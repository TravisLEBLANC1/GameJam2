package game;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import game.objects.Button;
import game.objects.ObjectInteract;
import util.Vector;


public class Map {
	private ArrayList<Wall> walls = new ArrayList<>();

	private ArrayList<Button> buttons = new ArrayList<>();

  
	public Map() {
		// TODO temporary
		walls.add(new Wall(new Rectangle(50,50, 3000,20)));
		walls.add(new Wall(new Rectangle(50,50, 20, 700)));
		walls.add(new Wall(new Rectangle(3050,50, 20 ,720)));
		walls.add(new Wall(new Rectangle(50,750, 3000,20)));
		int[] xpoints = {200, 500, 480, 180};
		int[] ypoints = {200, 500, 520, 220};
		walls.add(new Wall(xpoints, ypoints, 4));
		
		xpoints = new int[]{1500, 1800, 1780, 1480};
		ypoints = new int[]{200, 500, 520, 220};
		walls.add(new Wall(xpoints, ypoints, 4));
		
		walls.add(new Wall(new Rectangle(900,200, 40, 400)));
		walls.add(new Wall(new Rectangle(2000,200, 40, 400)));
		
		
		walls.add(new Wall(new Rectangle(2800,50, 30,100)));
		walls.add(new Wall(new Rectangle(2900,50, 30 ,100)));

//		walls.add(new Wall(new Rectangle(50,50, 20, 700)));
//		walls.add(new Wall(new Rectangle(50,50, 20, 700)));
	}
	
	public void addButton(Button but) {
		buttons.add(but);
	}
	
	private boolean basicCheck(Button event) {
		return event.isValid();
	}
	
	private boolean unlockNext(ObjectInteract event) {
		event.unlockNext();
		return true;
	}
	
	/*
	 * return true if the event is validated
	 * false otherwise (and we loose?)
	 */
	public boolean event(Button event) {
		return switch(event.getType()) {
		case NOEV -> true;
		case FISH,WOOL,MOUSE -> basicCheck(event);
		case UNLOCK -> event.unlockNext();
		case TASSE -> basicCheck(event);
		default -> {
			System.out.println("event " + event.getType() + " inconnu");
			yield false;
			}
		};
		
	}
	
	public Wall touchWall(Player player) {
		int edge;
		for (var wall: walls) {
			if (wall.intersects(player.getHitbox())) {
				return wall;
			}
		}
		return null;
	}
	
	public List<Wall> getWalls(){
		return walls;
	}
	
	public List<Button> getButtons(){
		return buttons;
	}
	
	public Rectangle getBorder() {
		return new Rectangle(50,50, 3000,720);
	}
	
	private void clickButton(Button button, Polygon hitbox) {
		if (button.intersects(hitbox)) {
			button.interact();
		}
	}
	
	public void clickButton (Polygon hitbox) {
		buttons.stream().forEach(b -> clickButton(b, hitbox));
	}
}
