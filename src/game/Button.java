package game;

import java.awt.Polygon;
import java.awt.Rectangle;

public interface Button {
	public void interact();
	public boolean intersects(Polygon hitbox);
	public boolean isValid();
	public Rectangle getRect();
}
