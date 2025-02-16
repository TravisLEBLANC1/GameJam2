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
	private static int nbMap = 0;
	
	public static void restartGame() {
		if (graphic != null) {
			updates.interrupt();
			graphic.stop();
			graphic.dispose();
		}
		if (game != null) {
			if (game.isWon()) {
				nbMap = 0; // (nbMap + 1) %4;
			}
			game.stop();
		}
		game = new Game(nbMap);
		graphic = new MainGraphic();


		graphic.init(game);
		var input = new KeyInput(game);
		graphic.inputUser(input);
		updates = new Thread(graphic);
		updates.start();
		graphic.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		try {
			SoundPlayer.init();
			SoundPlayer.setVolume(0.8);
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
