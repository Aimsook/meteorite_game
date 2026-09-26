public class GameThread implements Runnable {
    private BouncingMeteorite panel;
    private Thread thread;
    private boolean running = false;

    // คอนสตรักเตอร์: กำหนดค่าอ้างอิงไปยังพาเนลเกม (BouncingMeteorite)
    public GameThread(BouncingMeteorite panel) {
        this.panel = panel;
    }

    // เมธอดสำหรับเริ่มการทำงานของ Thread หลักของเกม
    public void start() {
        if (thread == null) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    // เมธอดทำงานของ Thread: ลูปตรวจสอบการชนและสั่งวาดหน้าจอใหม่ทุกๆ 16 มิลลิวินาที
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
