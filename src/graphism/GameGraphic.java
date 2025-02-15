package graphism;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JComponent;

import game.Game;
import game.NPC;
import game.Player;
import graphism.sprite.SpriteContainer;
import util.Vector;

public class GameGraphic extends JComponent {
	private static final int xshiftImage = 13;
	private static final int yshiftImage = 15;
	private static final Color playerColor = new Color(213, 246, 251, 200);
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
		var img = SpriteContainer.getImage();
		if (img == null) {
			g.fillOval((int) (pos.x() -upperLeft.x() - Player.WIDTH/2 ) ,(int) (pos.y() -upperLeft.y()-Player.HEIGHT/2), 5, 5);

		}else {
			g.drawImage(img, (int) (pos.x() -upperLeft.x() - Player.WIDTH/2 -xshiftImage ), (int) (pos.y() -upperLeft.y()-Player.HEIGHT/2 -yshiftImage), null);
		}
		
		if (game.player.isTranslocator()) {
			g.setColor(playerColor);
			
			var tPos = game.player.getTranslocatorPos();
			var tTime = game.player.getTranslocatorTime();
			
			g2d.fillArc((int) (tPos.x()- Player.WIDTH/2 -upperLeft.x()), (int) (tPos.y() -Player.HEIGHT/2 -upperLeft.y()),  Player.WIDTH, Player.HEIGHT, 90, tTime);
		}
//		g.setColor(Color.BLACK);
//		var rec = game.player.getHitbox();
//		rec.translate((int) -upperLeft.x(), (int) -upperLeft.y());
//		g2d.draw(rec);
	}
	
	public void paintNPC(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;  // Cast to Graphics2
		g.setColor(Color.YELLOW);
		var pos = game.npc.getPos();
		g.fillOval((int) pos.x()- NPC.WIDTH/2,(int) pos.y()-NPC.WIDTH/2, NPC.WIDTH, NPC.WIDTH);
		g.setColor(Color.BLACK);
		var rec = game.npc.getHitbox();
		g2d.draw(rec);
	}
	
    @Override
    protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       calculateUpperLeft();
       paintWalls(g);
       paintButtons(g);

       paintNPC(g);

       paintPlayer(g);

    }
}
