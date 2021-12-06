package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.World;
import view.Window;

public class Controller implements ActionListener {

    private Timer drawTimer = new Timer(Settings.FRAME_TIME, this);
    private Window window;
    private World world;

    public Controller(Window window, World world) {
        this.world = world;
        this.window = window;

        this.window.getDrawPanel().setWorld(world);

        MyMouseListener myMouseListener = new MyMouseListener(world);
        window.getDrawPanel().addMouseListener(myMouseListener);
        window.getDrawPanel().addMouseMotionListener(myMouseListener);
        window.getDrawPanel().addMouseWheelListener(myMouseListener);

        drawTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == drawTimer) {
            world.step(Settings.FRAME_TIME / 1000.0);
            window.getDrawPanel().repaint();
        }
    }
}
