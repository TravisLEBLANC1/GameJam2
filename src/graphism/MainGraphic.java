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
	private boolean running = true;
	private int x;
	
	private Game game;
	  public MainGraphic() {
	    super(APPNAME);
	    getContentPane().setBackground(BACKGROUNDCOLOR); // set background color
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close when "X" is clicked
	    setSize(1080, 900);  // Set window size
	    setVisible(true);
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
    public void run() {
        int fps = 60;
        long timePerFrame = 1000 / fps; // Milliseconds per frame

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
