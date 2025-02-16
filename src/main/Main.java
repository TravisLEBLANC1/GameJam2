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
	
	private static Game game;
	private static MainGraphic graphic;
	private static Thread updates;
	public static void restartGame() {
		if (graphic != null) {
			updates.interrupt();
			graphic.stop();
			graphic.dispose();
		}
		if (game != null) {
			game.stop();
		}
		game = new Game();
		graphic = new MainGraphic();


		graphic.init(game);
		var input = new KeyInput(game);
		graphic.inputUser(input);
		updates = new Thread(graphic);
		updates.start();
		graphic.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		System.out.println("hello");

		try {
			SoundPlayer.init();
			SoundPlayer.setVolume(0.7);
		} catch (IOException e) {
			System.err.println(e);
		} catch (UnsupportedAudioFileException e) {
			System.err.println(e);
		} catch (LineUnavailableException e) {
			System.err.println(e);
		};
		restartGame();
	}

}
