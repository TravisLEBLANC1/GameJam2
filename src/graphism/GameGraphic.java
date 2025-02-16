package graphism;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import game.EventEnum;
import game.Game;
import game.NPC;
import game.Player;
import graphism.sprite.SpriteContainer;
import main.Main;
import util.Vector;

public class GameGraphic extends JPanel {
	private static final int xshiftImage = 13;
	private static final int yshiftImage = 15;
	public static final Color PLAYERCOLOR = new Color(213, 246, 251);
	public static final Color WALLCOLOR = new Color(250, 155, 165);
	public static final Color INTERACTCOLOR = new Color(233, 201, 170);
	private static EventEnum eventLoose;
	private static boolean gameOver = false;
	private Game game;

	private Vector upperLeft;
	
	public GameGraphic(Game game) {
		setBackground(PLAYERCOLOR);
		gameOver = false;
		this.game = game;
	}
	
	private void calculateUpperLeft() {
		upperLeft = game.cam.getUpperLeft();
	}
	  public static void looserScreen(EventEnum event) {
		  eventLoose = event;
		  gameOver = true;
	  }
	  
    private void addGameOverOverlay(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        // Draw black transparent overlay
        g2d.setColor(new Color(0, 0, 0, 150)); // 150 for transparency
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw lost message
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics metrics = g2d.getFontMetrics();
        var message = "lost by : " + eventLoose.toString();
        int x = (getWidth() - metrics.stringWidth(message)) / 2;
        int y = getHeight() / 3;
        g2d.drawString(message, x, y);
        
        // Create restart button
        JButton restartButton = new JButton("Restart");
        restartButton.setBounds(getWidth() / 2 - 50, getHeight() / 2, 100, 40);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.restartGame();
            }
        });
        
        setLayout(null);
        add(restartButton);
        repaint();
    }
	
	public void paintWalls(Graphics g) {
	    var walls = game.map.getWalls();
	    g.setColor(INTERACTCOLOR);
	    for (var wall : walls) {
	      
	      var tmp = wall.getPolygon();
	      var poly = new Polygon(tmp.xpoints, tmp.ypoints, tmp.npoints);
	      poly.translate((int) -upperLeft.x(), (int) -upperLeft.y());
	      g.fillPolygon(poly);
	    }
	}

	public void paintButtons(Graphics g) {
	    var buttons = game.map.getButtons();
	    for (var b : buttons) {
	    	var button = b.getRect();
	      if (b.reflect() != null) {
	    	  var v = b.reflect();
	    	  g.setColor(PLAYERCOLOR);
	    	  
	    	  g.fillArc((int)(v.x()-upperLeft.x()), (int) (v.y()-upperLeft.y()), 20, 20, 0, 360);
	    	  
	      }
	    	
	      if (b.getImg() != null && SpriteContainer.getImage(b.getImg()) != null) {
	    	  var img = SpriteContainer.getImage(b.getImg());
	    	  g.drawImage(img, (int)(button.x + button.width/4 -upperLeft.x()), (int)(button.y + button.height/4-upperLeft.y()), null);
	      }else {
	    	  g.fillRect((int)(button.x-upperLeft.x()), (int)(button.y-upperLeft.y()), button.width, button.height);
	      }
	    }
	}
	
	public void painFire(Graphics g) {
		for(var pos : game.getFires()) {
			g.drawImage(SpriteContainer.fire, (int) (pos.x() - upperLeft.x()), (int) (pos.y() - upperLeft.y()), null);
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
			g.setColor(PLAYERCOLOR);
			
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
		g.fillOval((int)( pos.x()- NPC.WIDTH/2 -upperLeft.x()),(int) (pos.y()-NPC.WIDTH/2-upperLeft.y()), NPC.WIDTH, NPC.WIDTH);
		g.setColor(Color.MAGENTA);
		for (var target : game.npc.getTargets()){
			
			g.fillOval((int)( target.x()- 4 -upperLeft.x()),(int) (target.y()-4-upperLeft.y()), 8, 8);
		}
	}
	
	public void paintBackGround(Graphics g) {
		var r=game.map.getBorder();
//		g.setColor(playerColor);
//		g.fillRect(0, 0,MainGraphic.WINWIDTH, MainGraphic.HEIGHT);
		g.setColor(Color.WHITE);
		g.fillRect((int)(r.x  -upperLeft.x()),(int)(r.y - upperLeft.y()), r.width, r.height);
	}
	
    @Override
    protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       calculateUpperLeft();
       paintBackGround(g);
       paintWalls(g);
       paintButtons(g);
       painFire(g);
       paintNPC(g);
       paintPlayer(g);
       if (gameOver) {
    	   addGameOverOverlay(g);
       }
    }
}
