package graphism.sprite;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import game.objects.ObjectInteract;
import util.Direction;

public class SpriteContainer {
	private static HashMap<String, BufferedImage> images = new HashMap<>();
	private static SpriteSheet down ;
	private static SpriteSheet up ;
	private static SpriteSheet left ;
	private static SpriteSheet right ;
	private static SpriteSheet bonkdown ;
	private static SpriteSheet bonkup ;
	private static SpriteSheet bonkleft ;
	private static SpriteSheet bonkright ;
	private static SpriteSheet dashdown ;
	private static SpriteSheet dashup ;
	private static SpriteSheet dashleft ;
	private static SpriteSheet dashright ;
	private static SpriteSheet current;
	private static SpriteSheet currentBonk;
	private static SpriteSheet currentDash;
	public static BufferedImage fire;
	
	private static boolean backward = true;
	private static boolean isBonk = false;
	private static boolean isDash = false;
	private static Timer updateTimer = new Timer(300, e -> nextFrame());
	private static Timer bonkUpdateTimer = new Timer(100, e -> nextFrameBonk());
	private static Timer dashUpdateTimer = new Timer(200, e -> nextFrameDash());
	
	private static SpriteSheet build(BufferedImage sheet, int nb) {
		return new SpriteSheetBuilder().
		        withSheet(sheet).
		        withColumns(1).
		        withRows(nb).
		        withSpriteSize(63, 63).
		        withSpriteCount(nb).
		        build();
	}
	
	private static SpriteSheet build(BufferedImage sheet, int width, int height, int nb) {
		return new SpriteSheetBuilder().
		        withSheet(sheet).
		        withColumns(1).
		        withRows(nb).
		        withSpriteSize(width, height).
		        withSpriteCount(nb).
		        build();
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  
	
	private static BufferedImage scale(BufferedImage img, int dx, int dy) {
		int w = img.getWidth()*dx;
		int h = img.getHeight()*dy;
		BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		AffineTransform scalingTransform = new AffineTransform();
		scalingTransform.scale(dx, dy);
		AffineTransformOp scaleOp = new AffineTransformOp(scalingTransform, AffineTransformOp.TYPE_BILINEAR);
		return scaleOp.filter(img, after);
	}
	
    public static void loadImages(String directoryPath) throws IOException {
        HashMap<String, BufferedImage> imageMap = new HashMap<>();
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Invalid directory: " + directoryPath);
            return;
        }

        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg"));
        if (files != null) {
            for (File file : files) {
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                	images.put(file.getName(), scale(image, 2,2));
                } else {
                    System.err.println("Failed to load image: " + file.getName());
                }
            }
        }
    }
	
	public static void init() throws IOException {
		BufferedImage sheet = ImageIO.read(new File("animations/Walk_Down-Sheet.png"));
		down = build(sheet, 4);
		sheet = ImageIO.read(new File("animations/Walk_Up-Sheet.png"));
		up = build(sheet, 4);
		sheet = ImageIO.read(new File("animations/Walk_Right-Sheet.png"));
		right = build(sheet, 4);
		sheet = ImageIO.read(new File("animations/Walk_Left-Sheet.png"));
		left = build(sheet, 4);
		
		sheet = ImageIO.read(new File("animations/Bonk_Down-Sheet.png"));
		bonkdown = build(sheet, 4);
		sheet = ImageIO.read(new File("animations/Bonk_Up-Sheet.png"));
		bonkup = build(sheet, 4);
		sheet = ImageIO.read(new File("animations/Bonk_Right-Sheet.png"));
		bonkright = build(sheet, 4);
		sheet = ImageIO.read(new File("animations/Bonk_Left-Sheet.png"));
		bonkleft = build(sheet, 4);
		
		sheet = ImageIO.read(new File("animations/Dash_Down-Sheet.png"));
		dashdown = build(sheet, 3);
		sheet = ImageIO.read(new File("animations/Dash_Up-Sheet.png"));
		dashup = build(sheet, 4);
		sheet = ImageIO.read(new File("animations/Dash_Right-Sheet.png"));
		dashright = build(sheet, 3);
		sheet = ImageIO.read(new File("animations/Dash_Left-Sheet.png"));
		dashleft = build(sheet, 3);
		
		sheet =ImageIO.read(new File("animations/Window-Sheet.png"));
		var windowsheet = build(sheet, 49, 70 ,5);
		for (int i =0; i < 5; i++) {
			images.put("window" + i, scale(windowsheet.getSprite(),2 , 2));
			windowsheet.next();
		}
		
		loadImages("images");
		current = up;
		updateTimer.start();
		bonkUpdateTimer.start();
		dashUpdateTimer.start();
	}
	
	private static void nextFrameBonk() {
		if  ((!isBonk) || currentBonk == null) return;
		if (currentBonk.isDone()) {
			currentBonk.reset();
			currentBonk = null;
			isBonk = false;
			isDash = false;
			return;
		}
		currentBonk.next();
		return;
	}
	
	private static void nextFrameDash() {
		if  ((!isDash) || currentDash == null) return;
		if (currentDash.isDone()) {
			currentDash.reset();
			currentDash = null;
			isDash = false;
			return;
		}
		currentDash.next();
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
	
	private static void changeCurrentDash(SpriteSheet next) {
		if (currentDash == null) {
			currentDash = next;
			return;
		}
		if (next != currentDash) {
			next.set(currentDash.getIndex());
			currentDash.reset();
			currentDash = next;
		}
	}
	
	public static void bonk(Direction dir) {
		if (current != null) {
			current.reset();
		}
		backward = false;
		isDash = false;
		isBonk = true;
		switch(dir) {
		case Direction.NORTH  -> changeCurrentBonk(bonkup);
		case Direction.EAST,  Direction.NORTHEAST, Direction.SOUTHEAST -> changeCurrentBonk(bonkright);
		case Direction.WEST, Direction.NORTHWEST, Direction.SOUTHWEST -> changeCurrentBonk(bonkleft);
		case Direction.SOUTH  -> changeCurrentBonk(bonkdown);
		}
	}
	
	public static void dash(Direction dir) {
		if (current != null) {
			current.reset();
		}
		backward = false;
		isBonk = false;
		isDash = true;
		switch(dir) {
		case Direction.NORTH  -> changeCurrentDash(dashup);
		case Direction.EAST,  Direction.NORTHEAST, Direction.SOUTHEAST -> changeCurrentDash(dashright);
		case Direction.WEST, Direction.NORTHWEST, Direction.SOUTHWEST -> changeCurrentDash(dashleft);
		case Direction.SOUTH  -> changeCurrentDash(dashdown);
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
		if ((isBonk )&& currentBonk != null) return currentBonk.getSprite();
		if ((isDash )&& currentDash != null) return currentDash.getSprite();
		return current.getSprite();
	}
	
	public static BufferedImage getImage(String name) {
		if (images.containsKey(name)) {
			return images.get(name);
		}
		return null;
	}
}
