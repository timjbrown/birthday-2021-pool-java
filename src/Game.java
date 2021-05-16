import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Game {
    public static int PANEL_WIDTH = 400;
    public static int PANEL_HEIGHT = 800;
    public static int FRAME_TIME = 1000 / 100;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Birthday 2021");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(
                new Dimension(PANEL_WIDTH + 15, PANEL_HEIGHT + 40));
        frame.setLayout(new BorderLayout());

        DrawPanel drawPanel = new DrawPanel();

        frame.add(drawPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }
}
