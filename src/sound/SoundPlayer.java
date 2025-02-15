package sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer implements LineListener {
	private static HashMap<SoundEnum, Clip> sounds = new HashMap<>();
	private static final String dashPath = "sound/dash.wav";
	private static final String bonkPath = "sound/bonk.wav";
	private static final String tpPath = "sound/teleport.wav";
	
	private static void load(String path, SoundEnum name) throws IOException, UnsupportedAudioFileException, LineUnavailableException{
		InputStream inputStream = new FileInputStream(new File(path));
		inputStream = new BufferedInputStream(inputStream);
		var audioStream = AudioSystem.getAudioInputStream(inputStream);
		var audioClip = AudioSystem.getClip();
		audioClip.open(audioStream);
		sounds.put(name, audioClip);
		
	}
	
	public static void init() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		load(dashPath, SoundEnum.DASH);
		load(bonkPath, SoundEnum.BONK);
		load(tpPath, SoundEnum.TELEPORT);
	}
	
    public static void setVolume(double volume) {
    	volume = Math.max(Math.min(volume, 1), 0);
    	for (var clip : sounds.values()) {
	        if (clip != null) {
	            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
	            gainControl.setValue(dB);
	        }
    	}
    }
    
    
	
    public static void play(SoundEnum sound) {
    	if (sounds.containsKey(sound)) {
    		var clip = sounds.get(sound);
            if (clip.isRunning()) {
                clip.stop();
            }
            System.out.println(clip);
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
    	}
    }
	
	public void close(){
		for (var clip : sounds.values()) {
			clip.close();
		}

	}
	
    boolean isPlaybackCompleted;
    
    @Override
    public void update(LineEvent event) {
    	
    }
}