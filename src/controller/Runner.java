package controller;

import javax.swing.JOptionPane;

import model.World;
import view.Window;

public class Runner {

    public static void main(String[] args) {
        String name = JOptionPane
                                 .showInputDialog("What is your name?")
                                 .toUpperCase();

        Window window = new Window();
        World world = new World(name);
        Controller controller = new Controller(window, world);

        window.pack();
        window.setVisible(true);
    }
}
