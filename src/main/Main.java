package main;

import game.Game;
import game.io.KeyInput;
import graphism.MainGraphic;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello");
		Game game = new Game();
		
		var graphic = new MainGraphic();
		graphic.init(game);
		var input = new KeyInput(game);
		graphic.inputUser(input);
		game.start();
		graphic.run();
	}
	
	

}
