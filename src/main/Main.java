package main;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import game.Game;
import game.io.KeyInput;
import graphism.MainGraphic;
import sound.SoundEnum;
import sound.SoundPlayer;

public class Main {

	public static void main(String[] args) {
		System.out.println("hello");
		Game game = new Game();
		
		var graphic = new MainGraphic();
		graphic.init(game);
		var input = new KeyInput(game);
		graphic.inputUser(input);
		game.start();
		try {
			SoundPlayer.init();
			SoundPlayer.setVolume(0.7);
		} catch (IOException e) {
			System.err.println(e);
		} catch (UnsupportedAudioFileException e) {
			System.err.println(e);
		} catch (LineUnavailableException e) {
			System.err.println(e);
		}
		;
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
