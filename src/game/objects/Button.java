package game.objects;

import java.awt.Polygon;
import java.awt.Rectangle;

import game.EventEnum;
import game.NPC;
import game.Player;
import util.Vector;

public interface Button {
	public static final int WIDTH = 100;
	public static final int HEIGHT = 100;
	public EventEnum getType();
	public void interact();
	public boolean intersects(Polygon hitbox);
	public boolean isValid();
	default public void unlock() {}
	default public boolean unlockNext() {return true;}
	default public boolean addTarget(NPC npc) {return true;}
	default public Vector reflect() {return null;}
	public Rectangle getRect();
	public String getImg();
}
