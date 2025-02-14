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
	      g.fillRect(wall.x, wall.y, wall.width, wall.height);
}
	}

	public void paintPlayer(Graphics g) {
		g.setColor(Color.BLUE);
		var pos = game.player.getPos();
		g.fillOval((int) pos.x(),(int) pos.y(), 50, 50);
	}
	
    @Override
    protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       paintWalls(g);
       paintPlayer(g);
    }
}
