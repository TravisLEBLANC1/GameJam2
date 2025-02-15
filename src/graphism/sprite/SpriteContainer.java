package graphism.sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import game.Player;
import util.Direction;

public class SpriteContainer {
	private static SpriteSheet down ;
	private static SpriteSheet up ;
	private static SpriteSheet left ;
	private static SpriteSheet right ;
	private static SpriteSheet bonkdown ;
	private static SpriteSheet bonkup ;
	private static SpriteSheet bonkleft ;
	private static SpriteSheet bonkright ;
	private static SpriteSheet current;
	private static SpriteSheet currentBonk;
	
	private static boolean backward = true;
	private static boolean isBonk = false;
	private static Timer updateTimer = new Timer(300, e -> nextFrame());
	private static Timer bonkUpdateTimer = new Timer(100, e -> nextFrameBonk());
	private static SpriteSheet build(BufferedImage sheet) {
		return new SpriteSheetBuilder().
		        withSheet(sheet).
		        withColumns(1).
		        withRows(4).
		        withSpriteSize(63, 63).
		        withSpriteCount(4).
		        build();
	}
	
	public static void init() throws IOException {
		BufferedImage sheet = ImageIO.read(new File("images/Walk_Down-Sheet.png"));
		down = build(sheet);
		sheet = ImageIO.read(new File("images/Walk_Up-Sheet.png"));
		up = build(sheet);
		sheet = ImageIO.read(new File("images/Walk_Right-Sheet.png"));
		right = build(sheet);
		sheet = ImageIO.read(new File("images/Walk_Left-Sheet.png"));
		left = build(sheet);
		
		sheet = ImageIO.read(new File("images/Bonk_Down-Sheet.png"));
		bonkdown = build(sheet);
		sheet = ImageIO.read(new File("images/Bonk_Up-Sheet.png"));
		bonkup = build(sheet);
		sheet = ImageIO.read(new File("images/Bonk_Right-Sheet.png"));
		bonkright = build(sheet);
		sheet = ImageIO.read(new File("images/Bonk_Left-Sheet.png"));
		bonkleft = build(sheet);
		
		current = up;
		updateTimer.start();
		bonkUpdateTimer.start();
	}
	
	private static void nextFrameBonk() {
		if (!isBonk || currentBonk == null) {
			return ;
		}
		if (currentBonk.getIndex() == 3) {
			currentBonk.reset();
			currentBonk = null;
			isBonk = false;
			return;
		}
		currentBonk.next();
		return;
	}
	
	private static void nextFrame() {
		if (isBonk) {
			return;
		}
		if (backward) {
			current.back();
		}else {
			current.next();
		}

	}
	
	private static void changeCurrent(SpriteSheet next) {
		if (current == null) {
			current = next;
			return;
		}
		if (next != current) {
			next.set(current.getIndex());
			current.reset();
			current = next;
		}
	}
	
	private static void changeCurrentBonk(SpriteSheet next) {
		if (currentBonk == null) {
			currentBonk = next;
			return;
		}
		if (next != currentBonk) {
			next.set(currentBonk.getIndex());
			currentBonk.reset();
			currentBonk = next;
		}
	}
	
	public static void bonk(Direction dir) {
		if (current != null) {
			current.reset();
		}
		backward = false;
		isBonk = true;
		switch(dir) {
		case Direction.NORTH  -> changeCurrentBonk(bonkup);
		case Direction.EAST,  Direction.NORTHEAST, Direction.SOUTHEAST -> changeCurrentBonk(bonkright);
		case Direction.WEST, Direction.NORTHWEST, Direction.SOUTHWEST -> changeCurrentBonk(bonkleft);
		case Direction.SOUTH  -> changeCurrentBonk(bonkdown);
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
		if (isBonk && currentBonk != null) return currentBonk.getSprite();
		return current.getSprite();
	}
}
