import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class windows_start extends JFrame implements ActionListener{
    JTextField textField;
    JButton buttonStart;
    public windows_start() {
        JPanel panelBackground = createPanelBackground();
        JPanel centerContentPanel = createPanelContent();
        JLabel labelTitle = createLabelTitle();
        textField = createTextField();
        buttonStart = createButtonStart();
        JButton buttonStart = createButtonStart();

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

    private JPanel createPanelBackground() 
    {
        JPanel panelProgramPanel = new JPanel();
        panelProgramPanel.setBackground(Color.BLACK);
        return panelProgramPanel;
    }

    private JPanel createPanelContent() 
    {
        JPanel panelContent = new JPanel();
        panelContent.setBackground(Color.BLACK);
        panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
        return panelContent;
    }

    private JLabel createLabelTitle() 
    {
        JLabel labelTitle = new JLabel("How many meteorites do you want?");
        labelTitle.setForeground(Color.WHITE);
        labelTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
        labelTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        return labelTitle;
    }

    private JTextField createTextField() 
    {
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
                    //เมื่อกดปุ่ม Enter ให้ทำการกดปุ่ม Start
                if (e.getKeyChar() == KeyEvent.VK_ENTER) 
                {
                    buttonStart.doClick();
                }
            }
        });
        return textField;
    }

    private JButton createButtonStart() 
    {
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

    private void setIcon()
    {
        ImageIcon icon = new ImageIcon("texture/icon.png");
        setIconImage(icon.getImage());
    }

    public void actionPerformed (ActionEvent e) 
    {
        
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
        this.dispose();
        new windows_game(count);
    }

}