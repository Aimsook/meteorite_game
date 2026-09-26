import java.awt.*;
import java.awt.image.BufferedImage;

public class Meteorite implements Runnable {
    double x, y;
    double xSpeed, ySpeed;
    BufferedImage image;

    boolean isExploding = false;
    boolean isDead = false;
    int explosionTimer = 0;

    private int meteoriteWidth;
    private int meteoriteHeight;
    private BufferedImage explosionImage;
    private BouncingMeteorite panel;
    private Thread thread;

    // คอนสตรักเตอร์: กำหนดค่าเริ่มต้นพิกัด ความเร็ว รูปภาพ และพาเนลเกมสำหรับอุกกาบาต
    public Meteorite(double x, double y, double xSpeed, double ySpeed, BufferedImage image, int meteoriteWidth, int meteoriteHeight, BufferedImage explosionImage, BouncingMeteorite panel) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.image = image;
        this.meteoriteWidth = meteoriteWidth;
        this.meteoriteHeight = meteoriteHeight;
        this.explosionImage = explosionImage;
        this.panel = panel;
    }

    // เมธอดสำหรับเริ่มการทำงานของ Thread ประจำตัวอุกกาบาตลูกนี้
    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    // เมธอดทำงานของ Thread: ลูปอัพเดทตำแหน่งเรื่อยๆ จนกว่าอุกกาบาตจะถูกทำลาย
    @Override
    public void run() {
        while (!isDead) {
            update(panel.getWidth(), panel.getHeight());
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // เมธอดสั่งให้อุกกาบาตระเบิด: เปลี่ยนสถานะและหยุดการเคลื่อนที่
    public void explode() {
        if (!isExploding) {
            isExploding = true;
            xSpeed = 0;
            ySpeed = 0;
        }
    }

    // เมธอดอัพเดทตำแหน่ง: คำนวณพิกัด x, y ตรวจสอบการชนขอบจอเพื่อเด้งกลับและเพิ่มความเร็ว
    public void update(int panelWidth, int panelHeight) {
        // หากอยู่ในสถานะระเบิด ให้นับเวลาถอยหลังจนกว่าจะตาย (หายไป)
        if (isExploding) {
            explosionTimer++;
            if (explosionTimer >= 180) {
                isDead = true;
            }
            return;
        }

        double MAX_SPEED = 15.0;
        x += xSpeed;
        y += ySpeed;

        // ตรวจสอบการชนขอบซ้ายหรือขวา
        if (x <= 0) {
            x = 0;
            xSpeed = -xSpeed * 1.05;
        } else if (x + meteoriteWidth >= panelWidth) {
            x = panelWidth - meteoriteWidth;
            xSpeed = -xSpeed * 1.05;
        }

        // ตรวจสอบการชนขอบบนหรือล่าง
        if (y <= 0) {
            y = 0;
            ySpeed = -ySpeed * 1.05;
        } else if (y + meteoriteHeight >= panelHeight) {
            y = panelHeight - meteoriteHeight;
            ySpeed = -ySpeed * 1.05;
        }

        // จำกัดความเร็วสูงสุดแนวแกน X
        if (xSpeed > MAX_SPEED) {
            xSpeed = MAX_SPEED;
        } else if (xSpeed < -MAX_SPEED) {
            xSpeed = -MAX_SPEED;
        }

        // จำกัดความเร็วสูงสุดแนวแกน Y
        if (ySpeed > MAX_SPEED) {
            ySpeed = MAX_SPEED;
        } else if (ySpeed < -MAX_SPEED) {
            ySpeed = -MAX_SPEED;
        }
    }

    // เมธอดวาดอุกกาบาต: วาดรูประเบิดหรือรูปอุกกาบาตปกติลงบนหน้าจอ
    public void draw(Graphics g) {
        BufferedImage currentImg;
        if (isExploding) {
            currentImg = explosionImage;
        } else {
            currentImg = image;
        }
        g.drawImage(currentImg, (int) x, (int) y, meteoriteWidth, meteoriteHeight, null);
    }
}
