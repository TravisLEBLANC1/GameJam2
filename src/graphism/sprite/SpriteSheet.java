package graphism.sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private final List<BufferedImage> sprites;
    private int current = 0;
    public SpriteSheet(List<BufferedImage> sprites) {
        this.sprites = new ArrayList<>(sprites);
    }
    
    public void next() {
    	if (0 <= current && current < sprites.size() - 1) {
    		current++;
    	}else {
    		current = sprites.size() - 1;
    	}
    }
    public void back() {
    	if (current > 0) {
    		current--;
    	}
    }
    
    public boolean isDone() {
    	return current == sprites.size() - 1;
    }

    public int getIndex() {
    	return current;
    }
    
    public void set(int current) {
    	if (current >= sprites.size() - 1 || current < 0) {
    		this.current = 0;
    	}
    	this.current = current;
    }
    
    public void reset() {
    	current = 0;
    }
    
    public BufferedImage getSprite() {
    	if (current >= sprites.size() -1) { // panseman
    		return sprites.get(sprites.size() -1);
    	}
        return sprites.get(current);
    }

}