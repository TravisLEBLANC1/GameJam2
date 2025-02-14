package graphism;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import game.Game;

public class GameGraphic extends JComponent {
	private Game game;
	
	public GameGraphic(Game game) {
		this.game = game;
	}
	
	public void paintWalls(Graphics g) {
	    var walls = game.map.getWalls();
	    for (var wall : walls) {
	      g.setColor(Color.GRAY);
	      g.fillPolygon(wall.getPolygon());
}
	}

	public void paintPlayer(Graphics g) {
		if (game.player.isDashing()) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.BLUE);
		}
		var pos = game.player.getPos();
		g.fillOval((int) pos.x()-25,(int) pos.y()-25, 50, 50);
		g.setColor(Color.BLACK);
		var rec = game.player.getHitbox();
		g.drawRect(rec.x, rec.y, rec.width, rec.height);
	}
	
    @Override
    protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       paintWalls(g);
       paintPlayer(g);
    }
}
