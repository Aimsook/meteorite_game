import javax.swing.*;

public class windows_game extends JFrame {
    private BouncingMeteorite panel;
    private GameThread gameThread;

    // คอนสตรักเตอร์: สร้างหน้าต่างเกมหลัก กำหนดค่า และสั่งเริ่มทำงาน Thread ทั้งหมด
    public windows_game(int numMeteorites) {
        panel = new BouncingMeteorite(numMeteorites);
        gameThread = new GameThread(panel);

        setTitle("Meteorite Game");
        setIconImage(new ImageIcon("texture/icon.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        pack();
        setLocationRelativeTo(null);

        // สั่งให้อุกกาบาตแต่ละลูกทำงานใน Thread ของตัวเอง
        panel.startAllThreads();
        // เริ่ม GameThread สำหรับเช็คการชนและวาดจอ
        gameThread.start();
        setVisible(true);
    }
}
