package game.map;

import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.Timer;

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
	private int state = 0; // 0 -> close  1 -> open
	private Timer closeTimer = new Timer(2000, e-> close());
	public SecretTunnel(Rectangle r, Player p) {
		this.r = r;
		this.p = p;
	}
	
	public void setSibling(SecretTunnel s) {
		sibling = s;
	}
	
	public void open() {
		closeTimer.start();
		state = 1;
	}
	
	public void close() {
		closeTimer.stop();
		state = 0;
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
		if (state == 1) return; // no tp if open
		p.teleport(getSiblingPos());
		SoundPlayer.play(SoundEnum.TELEPORT);
		open();
		sibling.open();
		
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
		return "closet" + state;
	}	
}
