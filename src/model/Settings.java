package model;

import java.awt.Color;

import models.Rectangle;
import models.Vector2;

public class Settings {
    public static final Rectangle canvasRect = new Rectangle(0, 0, 400, 800);
    public static final double FRAME_TIME = 1 / 60.0;
    public static final int ballRadius = 10;
    public static final Color bgColor = new Color(6, 145, 84);
    public static final Vector2 cueStartPos = new Vector2(
            canvasRect.getWidth() / 2.0, canvasRect.getHeight() * .75);
    public static final Vector2 forces = Vector2.zero();
    public static double wallRestitution = 1;
    public static double ballRestitution = 1;
    public static double friction = 1000;
}
