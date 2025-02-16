package sound;

public enum SoundEnum {
	DASH("sound/dash.wav"),
	BONK("sound/bonk.wav"),
	TELEPORT("sound/teleport.wav"),
	MIAOU1("sound/miaou1.wav"),
	MIAOU2("sound/miaou2.wav"),
	FIRE("sound/fire.wav"),
	LOFI1("sound/lofi1.wav"),
	LOFI2("sound/lofi2.wav"),
	HIPHOP("sound/hiphop.wav"),
	PRRR("sound/prrrr.wav");
	
	
	public final String path;
	private SoundEnum(String path) {
		this.path = path;
	}
}
