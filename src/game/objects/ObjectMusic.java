package game.objects;

import java.awt.Polygon;
import java.awt.Rectangle;

import game.EventEnum;
import sound.SoundEnum;
import sound.SoundPlayer;

public class ObjectMusic implements Button {

	private final Rectangle r;
	private int state = 0;
	public final int NBMUSIC = 3;
	
	public ObjectMusic(Rectangle r) {
		this.r = r;
	}
	
	private void changeMusicInternal(SoundEnum toclose, SoundEnum toopen) {
		if (toclose != null) {
			SoundPlayer.stop(toclose);
		}
		if (toopen != null) {
			SoundPlayer.play(toopen);
		}
	}
	
	private void changeMusic() {
		switch(state) {
		case 0 -> changeMusicInternal(SoundEnum.HIPHOP, null);
		case 1 -> changeMusicInternal(null, SoundEnum.LOFI1);
		case 2 -> changeMusicInternal(SoundEnum.LOFI1, SoundEnum.LOFI2);
		case 3 -> changeMusicInternal(SoundEnum.LOFI2, SoundEnum.HIPHOP);
		}
	}
	
	@Override
	public void interact() {
		state = (state +1 )%(NBMUSIC+1);
		changeMusic();

	}

	@Override
	public boolean intersects(Polygon hitbox) {
		return hitbox.intersects(r);
	}

	@Override
	public boolean isValid() {
		return state > 0;
	}

	@Override
	public Rectangle getRect() {
		return r;
	}

	@Override
	public String getImg() {
		return "Vinyl_Player.png";
	}

	@Override
	public EventEnum getType() {
		return EventEnum.MUSIC;
	}

}
