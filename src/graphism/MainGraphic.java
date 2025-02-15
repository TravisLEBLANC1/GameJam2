package graphism;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

import game.EventEnum;
import game.Game;
import graphism.sprite.SpriteContainer;

public class MainGraphic extends JFrame implements Runnable{
	public static final String APPNAME = "SPEED_GHOST"; 
	public static final Color BACKGROUNDCOLOR = Color.white;
	public static final int FPS = 60;
	private Timer updateTimer = new Timer(1000/FPS, e -> draw());
	private GameGraphic gameGraphic;
	private static boolean running = true;
	public static int WINWIDTH = 1080;
	public static int WINHEIGHT = 900;
	
	
	private Game game;
	  public MainGraphic() {
	    super(APPNAME);
	    getContentPane().setBackground(BACKGROUNDCOLOR); // set background color
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close when "X" is clicked
	    setResizable(false);
	    setSize(WINWIDTH, WINHEIGHT);  // Set window size
	    setVisible(true);
	  }
	  
	  public void init(Game game) {
		  gameGraphic = new GameGraphic(game);
		  add(gameGraphic);
		  try {
			 SpriteContainer.init();
			
			} catch (IOException e) {
				System.err.println(e + " while loading sprites");
			}
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
	  
	  public void stop() {
		  running = false;
		  setVisible(false);
	  }
	  
	  @Override
    public void run() {
        int fps = 60;
        long timePerFrame = 1000 / fps; // Milliseconds per frame
        running = true;
        while (running) {
            long startTime = System.currentTimeMillis();

            repaint();
            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = timePerFrame - elapsedTime;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
