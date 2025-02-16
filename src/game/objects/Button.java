package game.objects;

import java.awt.Polygon;
import java.awt.Rectangle;

import game.EventEnum;

public interface Button {
	public static final int WIDTH = 100;
	public static final int HEIGHT = 100;
	public EventEnum getType();
	public void interact();
	public boolean intersects(Polygon hitbox);
	public boolean isValid();
	default public boolean unlockNext() {return true;}
	public Rectangle getRect();
	public String getImg();
}
