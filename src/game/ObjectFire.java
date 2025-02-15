package game;

import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.Timer;

import sound.SoundEnum;
import sound.SoundPlayer;

public class ObjectFire implements Button {
	private EventEnum type;
	private Rectangle r;
	private Game game;
	private final int fireTime = 20000;
	private final int fireDeltaTime = 7000;
	private Timer fireTimer = new Timer(fireTime, e -> game.loose(EventEnum.FIRE));
	private long fireTimerStart ; // in ms
	private Timer fireSoundTimer = new Timer(fireTime - fireDeltaTime, e -> SoundPlayer.play(SoundEnum.FIRE));
	
	public ObjectFire(EventEnum type, Rectangle hitbox, Game game) {
		this.type = type;
		this.r = hitbox;
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
}
