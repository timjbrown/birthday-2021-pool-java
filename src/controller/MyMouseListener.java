package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import engine.Inputs;
import model.World;
import models.Vector2;
import tools.SoundTools;

public class MyMouseListener extends MouseAdapter {

    private World world;

    public MyMouseListener(World world) {
        this.world = world;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Inputs.mouseHeld = true;
        Inputs.mousePressedPos = new Vector2(e.getPoint());
        Inputs.mouseCurrentPos = new Vector2(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Inputs.mouseHeld = false;
        Inputs.mouseCurrentPos = new Vector2(e.getPoint());
        Vector2 mouseToCue = world.cue.getPos().sub(Inputs.mouseCurrentPos);
        world.cue.setVel(mouseToCue.mul(10));
        SoundTools.playSound("hit.wav");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Inputs.mouseCurrentPos = new Vector2(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Inputs.mouseCurrentPos = new Vector2(e.getPoint());
    }
}
