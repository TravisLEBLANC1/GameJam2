package graphism.sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private List<BufferedImage> sprites;
    private int current = 0;
    private boolean islooping = false;
    public SpriteSheet(List<BufferedImage> sprites) {
        this.sprites = new ArrayList<>(sprites);
    }
    
    public void loop() {
    	islooping = true;
    }
    
    public void scaleAll(int dx, int dy) {
    	sprites = sprites.stream().map(b -> SpriteContainer.scale(b, dx, dy)).toList();
    }
    
    public void next() {
    	if (islooping) {
    		current = (current +1) % sprites.size();
    	}else {
	    	if (0 <= current && current < sprites.size() - 1) {
	    		current++;	
	    	}else {
	    		current = sprites.size() - 1;
			}
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