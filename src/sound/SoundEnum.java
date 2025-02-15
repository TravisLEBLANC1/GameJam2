package sound;

public enum SoundEnum {
	DASH("sound/dash.wav"),
	BONK("sound/bonk.wav"),
	TELEPORT("sound/teleport.wav"),
	MIAOU1("sound/miaou1.wav"),
	MIAOU2("sound/miaou2.wav"),
	FIRE("sound/fire.wav");
	
	
	public final String path;
	private SoundEnum(String path) {
		this.path = path;
	}
}
