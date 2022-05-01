package controller;

import model.Settings;
import model.World;
import view.Window;

public class Controller {

    private Window window;
    private World world;

    public Controller(Window window, World world) {
        this.window = window;
        this.world = world;
    }

    public void play() {
        window.getCanvas().setWorld(world);

        MyMouseListener myMouseListener = new MyMouseListener(world);
        window.getCanvas().addMouseListener(myMouseListener);
        window.getCanvas().addMouseMotionListener(myMouseListener);
        window.getCanvas().addMouseWheelListener(myMouseListener);

        double previous = System.currentTimeMillis() / 1000.0;
        double lag = 0;
        while (true) {
            double current = System.currentTimeMillis() / 1000.0;
            double elapsed = current - previous;
            previous = current;
            lag += elapsed;

            // processInput();
            while (lag >= Settings.FRAME_TIME) {
                world.step(Settings.FRAME_TIME);
                lag -= Settings.FRAME_TIME;
            }
            window.getCanvas().repaint();
        }
    }
}
