import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class windows_start extends JFrame implements ActionListener {
    JTextField textField;
    JButton buttonStart;

    // คอนสตรักเตอร์: สร้างหน้าต่างเริ่มต้นเกม ประกอบ UI (ปุ่ม, ช่องข้อความ) เข้าด้วยกัน
    public windows_start() {
        JPanel panelBackground = createPanelBackground();
        JPanel centerContentPanel = createPanelContent();
        JLabel labelTitle = createLabelTitle();
        textField = createTextField();
        buttonStart = createButtonStart();

        setTitle("Meteorite Game");
        setSize(350, 400);
        setPreferredSize(new Dimension(350, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setIcon();

        centerContentPanel.add(labelTitle);
        centerContentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerContentPanel.add(textField);
        centerContentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerContentPanel.add(buttonStart);

        panelBackground.setLayout(new GridBagLayout());
        panelBackground.add(centerContentPanel);

        add(panelBackground, BorderLayout.CENTER);
        setVisible(true);
    }

    // เมธอดสำหรับสร้างพาเนลพื้นหลังสีดำ
    private JPanel createPanelBackground() {
        JPanel panelProgramPanel = new JPanel();
        panelProgramPanel.setBackground(Color.BLACK);
        return panelProgramPanel;
    }

    // เมธอดสำหรับสร้างพาเนลจัดเรียงเนื้อหา UI แนวตั้งตรงกลาง
    private JPanel createPanelContent() {
        JPanel panelContent = new JPanel();
        panelContent.setBackground(Color.BLACK);
        panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
        return panelContent;
    }

    // เมธอดสำหรับสร้างข้อความหัวข้อเกม
    private JLabel createLabelTitle() {
        JLabel labelTitle = new JLabel("How many meteorites do you want?");
        labelTitle.setForeground(Color.WHITE);
        labelTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
        labelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        return labelTitle;
    }

    // เมธอดสำหรับสร้างช่องกรอกข้อมูลจำนวนอุกกาบาต
    private JTextField createTextField() {
        JTextField textField = new JTextField("5");
        textField.setMaximumSize(new Dimension(100, 30));
        textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setBorder(BorderFactory.createLineBorder(Color.RED));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setMargin(new Insets(10, 10, 10, 10));
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // เมื่อกดปุ่ม Enter ให้ทำงานเสมือนการกดปุ่ม Start
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    buttonStart.doClick();
                }
            }
        });
        return textField;
    }

    // เมธอดสำหรับสร้างปุ่ม Start เพื่อเริ่มเกม
    private JButton createButtonStart() {
        JButton buttonStart = new JButton("Start");
        buttonStart.setFont(new Font("Tahoma", Font.PLAIN, 16));
        buttonStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonStart.setBorder(BorderFactory.createLineBorder(Color.RED));
        buttonStart.setFocusPainted(false);
        buttonStart.setMaximumSize(new Dimension(50, 30));
        buttonStart.setMargin(new Insets(10, 10, 10, 10));
        buttonStart.setBackground(Color.WHITE);
        buttonStart.addActionListener(this);
        return buttonStart;
    }

    // เมธอดสำหรับตั้งค่าไอคอนของหน้าต่าง
    private void setIcon() {
        ImageIcon icon = new ImageIcon("texture/icon.png");
        setIconImage(icon.getImage());
    }

    // เมธอดจัดการเหตุการณ์เมื่อมีการกดปุ่ม Start ตรวจสอบความถูกต้องของตัวเลขและเริ่มเกม
    public void actionPerformed(ActionEvent e) {
        int count = 0;
        try {
            count = Integer.parseInt(textField.getText());
            if (count <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // ปิดหน้าต่างเริ่มต้น
        this.dispose();
        // เปิดหน้าต่างเกมหลักพร้อมส่งจำนวนอุกกาบาตเข้าไป
        new windows_game(count);
    }
}