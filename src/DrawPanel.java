import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel implements ActionListener {

    private Timer timer = new Timer(Game.FRAME_TIME, this);
    private World world;
    private Point mouse;
    private boolean mouseHeld;

    public DrawPanel() {
        this.world = new World();
        MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
        addMouseListener(myMouseAdapter);
        addMouseMotionListener(myMouseAdapter);
        this.setPreferredSize(
                new Dimension(Game.PANEL_WIDTH, Game.PANEL_HEIGHT));

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        world.step(Game.FRAME_TIME / 1000.0);
        world.draw(g, Game.PANEL_WIDTH, Game.PANEL_HEIGHT);
        if (mouseHeld) {
            g.setColor(world.player.getColor());
            g.drawLine(world.player.getPosition().intX(),
                    world.player.getPosition().intY(), mouse.x, mouse.y);
        }
    }

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            mouseHeld = true;
            mouse = e.getPoint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseHeld = false;
            Vector2d mousePosition = new Vector2d(mouse.x, mouse.y);
            Vector2d sub = world.player.getPosition().subbed(mousePosition);
            world.player.getVelocity().x = sub.x;
            world.player.getVelocity().y = sub.y;
            world.player.getVelocity().mult(10);
            Tools.playSound("hit.wav");
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouse = e.getPoint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            repaint();
        }
    }
}