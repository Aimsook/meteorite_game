import javax.swing.*;

public class windows_game extends JFrame {
    private BouncingMeteorite panel;
    private GameThread gameThread;

    public windows_game(int numMeteorites) {
        panel = new BouncingMeteorite(numMeteorites);
        gameThread = new GameThread(panel);

        setTitle("Meteorite Game");
        setIconImage(new ImageIcon("texture/icon.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        pack();
        setLocationRelativeTo(null);

        panel.startAllThreads();
        gameThread.start();
        setVisible(true);
    }
}
