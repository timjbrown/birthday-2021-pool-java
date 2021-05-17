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
            g.setColor(world.cue.color);
            g.drawLine(world.cue.pos.intX(), world.cue.pos.intY(), mouse.x,
                    mouse.y);
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
            Vector2 mousePosition = new Vector2(mouse.x, mouse.y);
            world.cue.vel = world.cue.pos.copy().sub(mousePosition).mul(10);
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