package graphism;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JComponent;

import game.Game;
import game.Player;
import util.Vector;

public class GameGraphic extends JComponent {
	private Game game;

	private Vector upperLeft;
	
	public GameGraphic(Game game) {
		this.game = game;
	}
	
	private void calculateUpperLeft() {
		upperLeft = game.cam.getUpperLeft();
	}
	
	public void paintWalls(Graphics g) {
	    var walls = game.map.getWalls();
	    for (var wall : walls) {
	      g.setColor(Color.GRAY);
	      var tmp = wall.getPolygon();
	      var poly = new Polygon(tmp.xpoints, tmp.ypoints, tmp.npoints);
	      poly.translate((int) -upperLeft.x(), (int) -upperLeft.y());
	      g.fillPolygon(poly);
	    }
	}

	public void paintButtons(Graphics g) {
	    var buttons = game.map.getButtons();
	    for (var button : buttons) {
	      g.setColor(Color.BLACK);
	      g.fillRect((int)(button.x-upperLeft.x()), (int)(button.y-upperLeft.y()), button.width, button.height);
	    }
	}
	
	public void paintPlayer(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;  // Cast to Graphics2
		if (game.player.isDashing()) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.BLUE);
		}
		var pos = game.player.getPos();
		g.fillOval((int) (pos.x() -upperLeft.x() - Player.WIDTH/2 ) ,(int) (pos.y() -upperLeft.y()-Player.WIDTH/2), Player.WIDTH, Player.WIDTH);
		g.setColor(Color.BLACK);
		var rec = game.player.getHitbox();
		rec.translate((int) -upperLeft.x(), (int) -upperLeft.y());
		g2d.draw(rec);
		
		if (game.player.isTranslocator()) {
			g.setColor(Color.MAGENTA);
			
			var tPos = game.player.getTranslocatorPos();
			var tTime = game.player.getTranslocatorTime();
			
			g2d.fillArc((int) (tPos.x()- Player.WIDTH/2 -upperLeft.x()), (int) (tPos.y() -Player.WIDTH/2 -upperLeft.y()),  Player.WIDTH, Player.WIDTH, 90, tTime);
		}
	}
	
    @Override
    protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       calculateUpperLeft();
       paintWalls(g);
       paintButtons(g);
       paintPlayer(g);
    }
}
