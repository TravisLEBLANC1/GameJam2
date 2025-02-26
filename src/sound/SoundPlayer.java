package sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

public class SoundPlayer implements LineListener {
	private static HashMap<SoundEnum, Clip> sounds = new HashMap<>();
	private static final String dashPath = "sound/dash.wav";
	private static final String bonkPath = "sound/bonk.wav";
	private static final String tpPath = "sound/teleport.wav";
	private static final String miaou1 = "sound/miaou1.wav";
	private static final String miaou2 = "sound/miaou2.wav";
	private static final Timer miaouTimer = new Timer(7000, e-> playMiaou());
	
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
		load(miaou1, SoundEnum.MIAOU1);
		load(miaou2, SoundEnum.MIAOU2);
		miaouTimer.start();
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
    
    public static void playMiaou() {
    	if(Math.random() <= 0.7) {
    		if(Math.random() <= 0.5) {
    			play(SoundEnum.MIAOU1);
    		}else {
    			play(SoundEnum.MIAOU2);
    		}
    	}
    }
	
    public static void play(SoundEnum sound) {
    	if (sounds.containsKey(sound)) {
    		var clip = sounds.get(sound);
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
    	}
    }
	
	public void close(){
		for (var clip : sounds.values()) {
			clip.close();
		}
		miaouTimer.stop();

	}
	
    boolean isPlaybackCompleted;
    
    @Override
    public void update(LineEvent event) {
    	
    }
}