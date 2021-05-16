import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Tools {
    public static void drawCenterString(Graphics g, String text, double centerx,
            double centery) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = (int) (centerx - metrics.stringWidth(text) / 2);
        int y = (int) (centery - metrics.getHeight() / 2 + metrics.getAscent());
        g.drawString(text, x, y);
    }

    public static void drawCenteredCircle(Graphics g, double radius,
            double centerx, double centery) {
        int d = (int) (radius * 2);
        int x = (int) (centerx - radius);
        int y = (int) (centery - radius);
        g.drawOval(x, y, d, d);
    }

    public static void fillCenteredCircle(Graphics g, double radius,
            double centerx, double centery) {
        int d = (int) (radius * 2);
        int x = (int) (centerx - radius);
        int y = (int) (centery - radius);
        g.fillOval(x, y, d, d);
    }

    public static void playSound(String filename) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            new File(filename));
                    Clip clip = AudioSystem.getClip();
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
