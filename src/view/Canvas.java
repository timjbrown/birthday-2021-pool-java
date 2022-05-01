package view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Inputs;
import model.Settings;
import model.World;
import models.Vector2;

@SuppressWarnings("serial")
public class Canvas extends JPanel {

    private World world;

    public Canvas() {
        this.setPreferredSize(new Dimension(Settings.canvasRect.intWidth(),
                Settings.canvasRect.intHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        world.draw(g, Settings.canvasRect.intWidth(),
                Settings.canvasRect.intHeight());
        if (Inputs.mouseHeld) {
            g.setColor(world.cue.getColor());
            Vector2 mouse = Inputs.mouseCurrentPos;
            g.drawLine(world.cue.getPos().intX(), world.cue.getPos().intY(),
                    mouse.intX(), mouse.intY());
        }
    }

    public void setWorld(World world) {
        this.world = world;
    }
}