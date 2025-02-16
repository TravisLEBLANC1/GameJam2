package game.objects;

import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.Timer;

import game.EventEnum;
import game.Game;
import sound.SoundEnum;
import sound.SoundPlayer;
import util.Vector;

public class ObjectFire implements Button {
	private Rectangle r;
	private Game game;
	private final int fireTime = 20000;
	private final int fireDeltaTime = 7000;
	private Timer fireTimer = new Timer(fireTime, e -> game.loose(EventEnum.FIRE));
	private long fireTimerStart ; // in ms
	private Timer fireSoundTimer = new Timer(fireTime - fireDeltaTime, e -> SoundPlayer.play(SoundEnum.FIRE));
	
	public ObjectFire(Vector pos, Game game) {
		this.r = new Rectangle((int)pos.x() - Button.WIDTH/2, (int) pos.y() - Button.HEIGHT/2, Button.WIDTH, Button.HEIGHT);
		this.game = game;
		fireTimer.start();
		fireSoundTimer.start();
		fireTimerStart = System.currentTimeMillis();
	
	}

	@Override
	public void interact() {		
		fireTimer.restart();
		fireSoundTimer.restart();
		fireTimerStart = System.currentTimeMillis();
		game.stopFire();
		
	}
	
	public void stop() {
		fireTimer.stop();
		fireSoundTimer.stop();
		fireTimer = null;
		fireSoundTimer = null;
	}
	
	public Rectangle getRect() {
		return r;
	}
	
	public boolean isOnFire() {
		return System.currentTimeMillis() - fireTimerStart >=  fireTime - fireDeltaTime;
	}

	@Override
	public boolean intersects(Polygon hitbox) {
		return hitbox.intersects(r);
	}

	@Override
	public boolean isValid() {
		return System.currentTimeMillis() - fireTimerStart <=  fireDeltaTime;
	}

	@Override
	public String getImg() {
		return null;
	}

	@Override
	public EventEnum getType() {
		return EventEnum.FIRE;
	}
}
