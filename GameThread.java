public class GameThread implements Runnable {
    private BouncingMeteorite panel;
    private Thread thread;
    private boolean running = false;

    public GameThread(BouncingMeteorite panel) {
        this.panel = panel;
    }

    public void start() {
        if (thread == null) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        while (running) {
            panel.checkCollisions();
            panel.repaint();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
