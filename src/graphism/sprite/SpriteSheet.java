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
    	if (current < sprites.size() - 1) {
    		current++;
    	}
    }
    public void back() {
    	if (current > 0) {
    		current--;
    	}
    }

    public int getIndex() {
    	return current;
    }
    
    public void set(int current) {
    	this.current = current;
    }
    
    public void reset() {
    	current = 0;
    }
    
    public BufferedImage getSprite() {
        return sprites.get(current);
    }

}