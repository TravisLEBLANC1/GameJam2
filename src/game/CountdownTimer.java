package game;

public class CountdownTimer {
    private long startTime;
    private long currentTime;
    private long duration;
    private long remainingTime;

    public CountdownTimer(long durationSeconds) {
        this.duration = durationSeconds * 1_000_000_000L; // Convertir s → ns
    }

    public void start() {
    	startTime = System.nanoTime();
        while (!isFinished()) {
            long oldTime = remainingTime;
        	long remainingTime = getRemainingSeconds();
            if(oldTime != remainingTime) {
            	System.out.print("\rTemps restant : " + remainingTime + "s ");
            } else {
            	
            }
        }
        System.out.println("\nTemps écoulé !");
    }

    private boolean isFinished() {
        return (System.nanoTime() - startTime) >= duration;
    }

    private long getRemainingSeconds() {
        long elapsed = System.nanoTime() - startTime;
        return Math.max((duration - elapsed) / 1_000_000_000L, 0);
    }
}
