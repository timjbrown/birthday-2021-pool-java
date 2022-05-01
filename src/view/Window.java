package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import model.Settings;

public class Window extends JFrame {

    private Canvas canvas;

    public Window() {
        setTitle("Birthday 2021");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(Settings.canvasRect.intWidth() + 5,
                Settings.canvasRect.intHeight() + 30));
        setResizable(false);
        setLayout(new BorderLayout());

        canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
