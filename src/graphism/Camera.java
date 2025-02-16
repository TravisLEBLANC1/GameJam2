package graphism;

import javax.swing.Timer;

import game.Player;
import util.Vector;

public class Camera {
	private Player player;
	private Vector cameraShift = new Vector(MainGraphic.WINWIDTH/2, MainGraphic.WINHEIGHT/2);
	private Vector camPos = new Vector(0,0);
	private Vector cameraShiftTeleport = Vector.NULL;
	private boolean teleporting;
	private final int TELEPORTSPEED = 30;
	private final double EPSILON = TELEPORTSPEED*5;
	private final Timer timerFix = new Timer(2000, e -> teleport());
	private boolean fixed = false;
	public Camera(Player player) {
		this.player = player;
	}
	
	public Vector getUpperLeft() {
		return camPos;
	}
	
	public void fix(Vector pos) {
		camPos = Vector.sub(pos, cameraShift);
		fixed = true;
		timerFix.start();
	}
	
	public void teleport() {
		fixed=false;
		timerFix.stop();
		teleporting = true;
	}
	
	public void maj() {
		if (fixed) return;
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
