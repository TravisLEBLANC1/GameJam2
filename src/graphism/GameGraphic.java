package graphism;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import game.Game;
import game.Player;

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

	public void paintButtons(Graphics g) {
	    var buttons = game.map.getButtons();
	    for (var button : buttons) {
	      g.setColor(Color.BLACK);
	      g.fillRect(button.x, button.y, button.width, button.height);
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
		g.fillOval((int) pos.x()- Player.WIDTH/2,(int) pos.y()-Player.WIDTH/2, Player.WIDTH, Player.WIDTH);
		g.setColor(Color.BLACK);
		var rec = game.player.getHitbox();
		g2d.draw(rec);
		
		if (game.player.isTranslocator()) {
			g.setColor(Color.MAGENTA);
			
			var tPos = game.player.getTranslocatorPos();
			var tTime = game.player.getTranslocatorTime();
			
			g2d.fillArc((int) tPos.x()- Player.WIDTH/2, (int) tPos.y() -Player.WIDTH/2,  Player.WIDTH, Player.WIDTH, 90, tTime);
		}
	}
	
    @Override
    protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       paintWalls(g);
       paintPlayer(g);
       paintButtons(g);
    }
}
