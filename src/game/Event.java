package game;

public class Event {
	public final EventEnum type;
	
	public Event(EventEnum type) {
		this.type = type;
	}
	
	public static Event empty() {
		return new Event(EventEnum.NOEV);
	}
	
}
