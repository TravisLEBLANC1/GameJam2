package main;

import game.Game;
import game.io.KeyInput;
import graphism.MainGraphic;

public class Main {

	public static void main(String[] args) {
		System.out.println("hello");
		Game game = new Game();
		
		var graphic = new MainGraphic();
		graphic.init(game);
		var input = new KeyInput(game);
		graphic.inputUser(input);
		game.start();
		graphic.run();
		
//		double hitboxShift = 12;
//		var player = new Player();
//		var pos = new Vector(49.442, 149.482);
//		player.teleport(pos);
//		var wall = new Wall(new Rectangle(00,50, 30,100));
//		System.out.println(Arrays.toString(player.getHitbox().xpoints)); 
//		System.out.println(Arrays.toString(player.getHitbox().ypoints));
//		System.out.println(player.getHitbox().ypoints);
//		System.out.println(wall.getCollidingEdge(player));
	}
	
	

}
