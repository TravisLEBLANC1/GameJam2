package graphism;

import game.Player;
import util.Vector;

public class Camera {
	private Player player;
	private Vector cameraShift = new Vector(MainGraphic.WINWIDTH/2, MainGraphic.WINHEIGHT/2);
	private Vector camPos = new Vector(0,0);
	private Vector cameraShiftTeleport;
	private boolean teleporting;
	private final int TELEPORTSPEED = 30;
	private final double EPSILON = TELEPORTSPEED*4;
	public Camera(Player player) {
		this.player = player;
	}
	
	public Vector getUpperLeft() {
		return camPos;
	}
	
	public void teleport(Vector old) {
		cameraShiftTeleport =Vector.sub(Vector.sub(player.getPos(),cameraShift) , Vector.sub(old, cameraShift)).normalized(1);
		// System.out.println("camshift = " + cameraShiftTeleport);
		teleporting = true;
	}
	
	public void maj() {
		var tmp = Vector.sub(player.getPos(), cameraShift);
		if (teleporting && !player.isTranslocator()) {
			camPos = Vector.add(camPos, cameraShiftTeleport);
			if (camPos.distanceSquare(tmp) <= EPSILON) {
				camPos = tmp;
				teleporting = false;
			}else {
				cameraShiftTeleport = Vector.sub(tmp, camPos).normalized(TELEPORTSPEED);
//				System.out.println("camshift = " + cameraShiftTeleport);
			}
			
		}else {
			camPos = tmp;
		}
	}
}
