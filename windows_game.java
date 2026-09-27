import javax.swing.*;
//หน้าต่างเกมหลัก สร้าง BouncingMeteorite แล้วเริ่ม Thread ต่าง ๆ
public class windows_game extends JFrame {
    private BouncingMeteorite panel;

    // คอนสตรักเตอร์: สร้างหน้าต่างเกมหลัก กำหนดค่า และสั่งเริ่มทำงาน Thread ทั้งหมด
    public windows_game(int numMeteorites) {
        panel = new BouncingMeteorite(numMeteorites);

        setTitle("Meteorite Game");
        setIconImage(new ImageIcon("texture/icon.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        pack();
        setLocationRelativeTo(null);

        // สั่งให้อุกกาบาตแต่ละลูกทำงานใน Thread ของตัวเอง
        panel.startAllThreads();
        
        setVisible(true);
    }
}
