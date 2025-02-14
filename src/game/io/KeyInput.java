package game.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Game;
import util.Direction;

public class KeyInput implements KeyListener  {
	  private int isPressed = 0;
	  private static final int FLAGUP    = 0b0001;
	  private static final int FLAGRIGHT = 0b0010;
	  private static final int FLAGDOWN  = 0b0100;
	  private static final int FLAGLEFT  = 0b1000;
	  private static int cmdisPressed = 0;
	  private static final int FLAGSHIFT = 0b0001;
	  private static final int FLAGCTRL  = 0b0010;
	  private static final int FLAGALT   = 0b0100;
	  private static final int FLAGTALTGR= 0b1000;
	  private static boolean capsToggle = false;
	  private Game game; 
	  
	  public KeyInput(Game game) {
	    this.game = game;
	  }

	  /*
	   * clear inputs
	   */
	  public void clear() {
	    isPressed = 0;
	    cmdisPressed = 0;
	    game.majCharacter(directionFromPressed());
	  }
	  
	  
	  private boolean ctrlIsPressed() {
	    return (cmdisPressed & FLAGCTRL) > 0;
	  }
	  
	  private Direction directionFromPressed() {
	    return switch(isPressed) {
	    case  0b0001, 0b1011 -> Direction.NORTH;
	    case  0b0010, 0b0111 -> Direction.EAST;
	    case  0b0100, 0b1110 -> Direction.SOUTH;
	    case  0b1000, 0b1101 -> Direction.WEST;
	    case  0b0011         -> Direction.NORTHEAST;
	    case  0b0110         -> Direction.SOUTHEAST;
	    case  0b1100         -> Direction.SOUTHWEST;
	    case  0b1001         -> Direction.NORTHWEST;
	    default              -> Direction.NULL;
	    };
	  }
	  
	  @Override
	  public void keyPressed(KeyEvent e) {
	    var code = e.getKeyCode();
	    switch (code) {
	      case KeyEvent.VK_UP, KeyEvent.VK_Z -> isPressed |= FLAGUP;
	      case KeyEvent.VK_RIGHT, KeyEvent.VK_D ->isPressed |= FLAGRIGHT;
	      case KeyEvent.VK_DOWN, KeyEvent.VK_S -> isPressed |= FLAGDOWN;
	      case KeyEvent.VK_LEFT, KeyEvent.VK_Q -> isPressed |= FLAGLEFT;
	      case KeyEvent.VK_SHIFT -> cmdisPressed |= FLAGSHIFT;
	      case KeyEvent.VK_CONTROL -> cmdisPressed |= FLAGCTRL;
	      case KeyEvent.VK_CAPS_LOCK -> {
	        capsToggle = ! capsToggle;
	      }
	    }
	    game.majCharacter(directionFromPressed());
	  }

	  @Override
	  public void keyReleased(KeyEvent e) {
	    switch (e.getKeyCode()) {
	      case KeyEvent.VK_UP, KeyEvent.VK_Z -> isPressed &= ~FLAGUP;
	      case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> isPressed &= ~FLAGRIGHT;
	      case KeyEvent.VK_DOWN, KeyEvent.VK_S -> isPressed &= ~FLAGDOWN;
	      case KeyEvent.VK_LEFT, KeyEvent.VK_Q -> isPressed &= ~FLAGLEFT;
	      case KeyEvent.VK_SHIFT -> cmdisPressed &= ~FLAGSHIFT;
	      case KeyEvent.VK_CONTROL -> cmdisPressed &= ~FLAGCTRL;
	    }
	    game.majCharacter(directionFromPressed());
	  }


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	  
}
