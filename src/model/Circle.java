package model;

import java.awt.Color;
import java.awt.Graphics;

import models.CircleBody;
import models.Vector2;
import tools.DrawTools;

public class Circle extends CircleBody {

    private String name;
    private Color color;
    private double lifetime;
    private double lifespan;
    private boolean alive;

    public Circle() {
        lifespan = -1;
        alive = true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getLifetime() {
        return lifetime;
    }

    public void setLifetime(double lifetime) {
        this.lifetime = lifetime;
    }

    public double getLifespan() {
        return lifespan;
    }

    public void setLifespan(double lifespan) {
        this.lifespan = lifespan;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void update(double elapsedTime, Vector2 forces) {
        super.update(elapsedTime, forces);

        lifetime += elapsedTime;
        if (lifespan > -1 && lifetime >= lifespan)
            alive = false;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        DrawTools.fillCircle(g, getRadius(), getPos().getX(),
                getPos().getY());
        g.setColor(Color.black);
        DrawTools.drawCircle(g, getRadius(), getPos().getX(),
                getPos().getY());
        if (name != null) {
            g.setColor(Color.white);
            DrawTools.drawString(g, name, getPos().getX(),
                    getPos().getY());
        }
    }
}