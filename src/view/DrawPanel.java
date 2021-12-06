package view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import controller.Settings;
import engine.Inputs;
import model.World;
import models.Vector2;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel {

    private World world;

    public DrawPanel() {
        this.setPreferredSize(new Dimension(Settings.drawRect.intWidth(),
                Settings.drawRect.intHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        world.draw(g, Settings.drawRect.intWidth(),
                Settings.drawRect.intHeight());
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