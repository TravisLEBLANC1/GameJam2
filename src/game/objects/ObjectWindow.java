package game.objects;

import java.awt.Polygon;
import java.awt.Rectangle;

import game.EventEnum;
import util.Vector;

public class ObjectWindow implements Button {
	private Rectangle r;
	private int state;
	private int maxstate;
	private String img;
	private int nbChecked = 0;
	private boolean reversed = false;
	
	public ObjectWindow(Vector pos, String img, int maxstate) {
		this.r = new Rectangle((int)pos.x() - Button.WIDTH/2, (int) pos.y() - Button.HEIGHT/2, Button.WIDTH, Button.HEIGHT*2);
		this.maxstate = maxstate;
		this.img = img;
		if (maxstate == 1) {
			state = 2;
		}else {
			state = 0;
		}
	}
	
	@Override
	public EventEnum getType() {
		return EventEnum.WINDOW;
	}

	@Override
	public void interact() {
		if (maxstate == 1) return;
		if (reversed) {
			state = (state - 1)%maxstate;
			if (state == 0) reversed = false;
		}else {
			state = (state + 1)%maxstate;
			if (state == maxstate - 1) reversed = true;
		}
	}

	@Override
	public boolean intersects(Polygon hitbox) {
		return hitbox.intersects(r);
	}

	@Override
	public boolean isValid() {
		nbChecked++;
		if (nbChecked == 1) { // première fois on doit être à droite 
			return state == maxstate-1;
		}
		if (nbChecked == 2) {
			return state == 0; // deuxième fois on doit être à gauche
		}
		return state == 0;
	}

	@Override
	public Rectangle getRect() {
		return r;
	}

	@Override
	public String getImg() {
		return img + state;
	}

}
