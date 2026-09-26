import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BouncingMeteorite extends JPanel {
    private int WIDTH = 900;
    private int HEIGHT = 600;
    private int Meteorite_WIDTH = 80;
    private int Meteorite_HEIGHT = 80;

    private BufferedImage[] IMAGES = loadImages();
    private static BufferedImage EXPLOSION_IMAGE = loadExplosionImage();

    private List<Meteorite> meteorites = new ArrayList<>();

    // คอนสตรักเตอร์: ตั้งค่าหน้าจอและสร้างอุกกาบาตตามจำนวนที่ผู้ใช้กำหนด พร้อมสุ่มตำแหน่งและความเร็ว
    public BouncingMeteorite(int numMeteorites) {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);

        Random rand = new Random();

        for (int i = 0; i < numMeteorites; i++) {
            double x = rand.nextInt(WIDTH - Meteorite_WIDTH);
            double y = rand.nextInt(HEIGHT - Meteorite_HEIGHT);

            double xSpeed = 2.0 + rand.nextDouble() * 3.0;
            double ySpeed = 2.0 + rand.nextDouble() * 3.0;

            if (rand.nextBoolean()) {
                xSpeed = xSpeed;
            } else {
                xSpeed = -xSpeed;
            }

            if (rand.nextBoolean()) {
                ySpeed = ySpeed;
            } else {
                ySpeed = -ySpeed;
            }

            BufferedImage image = IMAGES[rand.nextInt(IMAGES.length)];
            meteorites.add(new Meteorite(x, y, xSpeed, ySpeed, image, Meteorite_WIDTH, Meteorite_HEIGHT, EXPLOSION_IMAGE, this));
        }
    }

    // เมธอดสั่งทำงาน: เรียกให้อุกกาบาตแต่ละลูกเริ่มต้น Thread ของตัวเอง
    public void startAllThreads() {
        for (int i = 0; i < meteorites.size(); i++) {
            Meteorite m = meteorites.get(i);
            m.startThread();
        }
    }

    // เมธอดโหลดรูปภาพ: โหลดไฟล์รูปภาพอุกกาบาต 5 แบบจากโฟลเดอร์ texture
    private static BufferedImage[] loadImages() {
        BufferedImage[] images = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            String path = "texture/meteorite_" + (i + 1) + ".png";
            try {
                images[i] = ImageIO.read(new File(path));
            } catch (IOException e) {
                throw new RuntimeException("เกิดข้อผิดพลาดร้ายแรง: ไม่สามารถโหลดรูปภาพ " + path, e);
            }
        }
        return images;
    }

    // เมธอดโหลดรูปภาพ: โหลดไฟล์รูปภาพเอฟเฟกต์ระเบิด
    private static BufferedImage loadExplosionImage() {
        try {
            return ImageIO.read(new File("texture/explosion.png"));
        } catch (IOException e) {
            throw new RuntimeException("เกิดข้อผิดพลาดร้ายแรง: ไม่สามารถโหลดรูปภาพ explosion.png", e);
        }
    }

    // เมธอดตรวจสอบการชน: ลบลูกที่ตายแล้ว และเช็คการชนกันระหว่างอุกกาบาตแต่ละคู่
    public void checkCollisions() {
        for (int i = meteorites.size() - 1; i >= 0; i--) {
            Meteorite m = meteorites.get(i);
            if (m.isDead) {
                meteorites.remove(i);
            }
        }

        for (int i = 0; i < meteorites.size(); i++) {
            for (int j = i + 1; j < meteorites.size(); j++) {
                Meteorite m1 = meteorites.get(i);
                Meteorite m2 = meteorites.get(j);

                // ข้ามการเช็คถ้าลูกใดลูกหนึ่งกำลังระเบิดอยู่
                if (m1.isExploding || m2.isExploding) {
                    continue;
                }

                if (checkCollision(m1, m2)) {
                    resolveCollision(m1, m2);
                }
            }
        }
    }

    // เมธอดตรวจสอบกรอบการชน: เช็คว่าภาพอุกกาบาต 2 ลูกซ้อนทับกันหรือไม่
    private boolean checkCollision(Meteorite m1, Meteorite m2) {
        return m1.x < m2.x + Meteorite_WIDTH &&
            m1.x + Meteorite_WIDTH > m2.x &&
            m1.y < m2.y + Meteorite_HEIGHT &&
            m1.y + Meteorite_HEIGHT > m2.y;
    }

    // เมธอดจัดการเมื่อชนกัน: เปรียบเทียบความเร็วรวม ลูกที่ช้ากว่าจะถูกทำให้ระเบิด
    private void resolveCollision(Meteorite m1, Meteorite m2) {
        double tempXSpeed = m1.xSpeed;
        double tempYSpeed = m1.ySpeed;

        double m2tempXSpeed = m2.xSpeed;
        double m2tempYSpeed = m2.ySpeed;

        // แปลงความเร็วให้เป็นค่าบวก (Absolute Value)
        if (tempXSpeed < 0) {
            tempXSpeed = -tempXSpeed;
        }
        if (tempYSpeed < 0) {
            tempYSpeed = -tempYSpeed;
        }
        if (m2tempXSpeed < 0) {
            m2tempXSpeed = -m2tempXSpeed;
        }
        if (m2tempYSpeed < 0) {
            m2tempYSpeed = -m2tempYSpeed;
        }

        double m1_real_speed = tempXSpeed + tempYSpeed;
        double m2_real_speed = m2tempXSpeed + m2tempYSpeed;

        // ลูกที่ช้ากว่าจะระเบิด
        if (m1_real_speed > m2_real_speed) {
            m2.explode();
        } else if (m1_real_speed < m2_real_speed) {
            m1.explode();
        }
    }

    // เมธอดวาดหน้าจอ: เคลียร์หน้าจอและสั่งให้อุกกาบาตทุกลูกวาดตัวเอง
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Meteorite m : meteorites) {
            m.draw(g);
        }
    }
}