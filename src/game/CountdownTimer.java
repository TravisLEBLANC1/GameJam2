package game;

public class CountdownTimer {
    private final long duration; // Durée en nanosecondes
    private boolean running = false;

    public CountdownTimer(int durationSeconds) {
        this.duration = durationSeconds * 1_000_000_000L; // Convertir s → ns
    }

    public void start() {
        if (running) return;
        running = true;

        Thread thread = new Thread(() -> {
            long startTime = System.nanoTime();

            while (running) {
                long remainingTime = Math.max((duration - (System.nanoTime() - startTime)) / 1_000_000_000L, 0);
                System.out.println("Temps restant : " + remainingTime + "s");

                if (remainingTime == 0) {
                    System.out.println("Temps écoulé !");
                    running = false;
                }

                sleep(1000);
            }
        });

        thread.start();
    }

    private void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}