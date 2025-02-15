package game;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;


public class Map {
	private ArrayList<Wall> walls = new ArrayList<>();
<<<<<<< Updated upstream
  private ArrayList<Rectangle> buttons = new ArrayList<>();
=======
	private ArrayList<ObjectInteract> buttons = new ArrayList<>();
>>>>>>> Stashed changes
  
	public Map() {
		// TODO temporary
		walls.add(new Wall(new Rectangle(50,50, 3000,20)));
		walls.add(new Wall(new Rectangle(50,50, 20, 700)));
		walls.add(new Wall(new Rectangle(3050,50, 20 ,720)));
		walls.add(new Wall(new Rectangle(50,750, 3000,20)));
		int[] xpoints = {200, 500, 480, 180};
		int[] ypoints = {200, 500, 520, 220};
		walls.add(new Wall(xpoints, ypoints, 4));
<<<<<<< Updated upstream
=======

		buttons.add(new ObjectInteract(EventEnum.FISH, new Rectangle (300,400,50,50)));

>>>>>>> Stashed changes
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
	
	private boolean basicCheck(EventEnum event) {
		return buttons.stream().filter(b -> b.type == event).allMatch(b -> b.isValid());
	}
	
	/*
	 * return true if the event is validated
	 * false otherwise (and we loose?)
	 */
	public boolean event(EventEnum event) {
		return switch(event) {
		case NOEV -> true;
		case FISH -> basicCheck(event);
		case MOUSE -> basicCheck(event);
		case TASSE -> basicCheck(event);
		default -> {
			System.out.println("event " + event + " inconnu");
			yield false;
			}
		};
		
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
	}
	
	public List<ObjectInteract> getButtons(){
		return buttons;
	}
	
	private void clickButton(ObjectInteract button, Polygon hitbox) {
		if (button.intersects(hitbox)) {
			button.nextState();
		}
	}
	
	public void clickButton (Polygon hitbox) {
		buttons.stream().forEach(b -> clickButton(b, hitbox));
	}
}
