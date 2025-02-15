package graphism.sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import util.Direction;

public class SpriteContainer {
	public static SpriteSheet down ;
	public static SpriteSheet up ;
	public static SpriteSheet left ;
	public static SpriteSheet right ;
	private static SpriteSheet current;
	private static boolean backward = true;
	private static Timer updateTimer = new Timer(300, e -> nextFrame());
	
	public static void init() throws IOException {
		BufferedImage sheet = ImageIO.read(new File("images/Walk_Down-Sheet.png"));
		down = new SpriteSheetBuilder().
		        withSheet(sheet).
		        withColumns(1).
		        withRows(4).
		        withSpriteSize(63, 63).
		        withSpriteCount(4).
		        build();
		sheet = ImageIO.read(new File("images/Walk_Up-Sheet.png"));
		up = new SpriteSheetBuilder().
		        withSheet(sheet).
		        withColumns(1).
		        withRows(4).
		        withSpriteSize(63, 63).
		        withSpriteCount(4).
		        build();
		sheet = ImageIO.read(new File("images/Walk_Right-Sheet.png"));
		right = new SpriteSheetBuilder().
		        withSheet(sheet).
		        withColumns(1).
		        withRows(4).
		        withSpriteSize(63, 63).
		        withSpriteCount(4).
		        build();
		 sheet = ImageIO.read(new File("images/Walk_Left-Sheet.png"));
		left = new SpriteSheetBuilder().
		        withSheet(sheet).
		        withColumns(1).
		        withRows(4).
		        withSpriteSize(63, 63).
		        withSpriteCount(4).
		        build();
		current = up;
		updateTimer.start();
	}
	
	private static void nextFrame() {
		if (backward) {
			current.back();
		}else {
			current.next();
		}
	}
	
	private static void changeCurrent(SpriteSheet next) {
		if (next != current) {
			next.set(current.getIndex());
			current.reset();
			current = next;
		}
	}
	
	public static void changeDirection(Direction dir) {
		if (dir == Direction.NULL) {
			backward = true;
			return;
		}
		backward = false;
		switch(dir) {
		case Direction.NORTH  -> changeCurrent(up);
		case Direction.EAST,  Direction.NORTHEAST, Direction.SOUTHEAST -> changeCurrent(right);
		case Direction.WEST, Direction.NORTHWEST, Direction.SOUTHWEST -> changeCurrent(left);
		case Direction.SOUTH  -> changeCurrent(down);
		}

	}
	
	public static BufferedImage getImage() {
		if (current == null) return null;
		return current.getSprite();
	}
}
