package graphism;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import game.Game;

public class MainGraphic extends JFrame{
	public static final String APPNAME = "SPEED_GHOST"; 
	public static final Color BACKGROUNDCOLOR = Color.white;
	public static final int FPS = 60;
	  private Timer updateTimer = new Timer(1000/FPS, e -> draw());
	private GameGraphic gameGraphic;
	private Game game;
	  public MainGraphic() {
	    super(APPNAME);
	    getContentPane().setBackground(BACKGROUNDCOLOR); // set background color
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close when "X" is clicked
	    setSize(1080, 900);  // Set window size
	    setVisible(true);
	    updateTimer.start();
	  }
	  
	  public void init(Game game) {
		  gameGraphic = new GameGraphic(game);
		  add(gameGraphic);
	  }
	  
	  public void inputUser(KeyListener inputUser) {
		  gameGraphic.addKeyListener(inputUser);
		  gameGraphic.setFocusable(true);
		  gameGraphic.requestFocusInWindow();
	      revalidate();
	      repaint();
	  }
	  
	  public void draw() {
		  repaint();
	  }
}
