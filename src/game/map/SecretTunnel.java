package game.map;

import java.awt.Polygon;
import java.awt.Rectangle;

import game.EventEnum;
import game.Player;
import game.objects.Button;
import sound.SoundEnum;
import sound.SoundPlayer;
import util.Vector;

public class SecretTunnel implements Button{
	private Rectangle r;
	private SecretTunnel sibling;
	private Player p;
	public SecretTunnel(Rectangle r, Player p) {
		this.r = r;
		this.p = p;
	}
	
	public void setSibling(SecretTunnel s) {
		sibling = s;
	}
	
	public Vector getPos() {
		return new Vector(r.x + r.width/2, r.y + r.height/2);
	}
	
	public Vector getSiblingPos() {
		if (sibling == null) {
			System.err.println("error sibling");
			return Vector.NULL;
		}
		return sibling.getPos();
	}
	
	@Override
	public EventEnum getType() {
		return EventEnum.ARMOIR;
	}
	
	@Override
	public void interact() {
		p.teleport(getSiblingPos());
		SoundPlayer.play(SoundEnum.TELEPORT);
	}
	
	@Override
	public boolean intersects(Polygon hitbox) {
		return hitbox.intersects(r);
	}
	@Override
	public boolean isValid() {
		return true;
	}
	@Override
	public Rectangle getRect() {
		return r;
	}
	@Override
	public String getImg() {
		return "image/armoir.png";
	}	
}
