package main;

import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Battle Royal");
        window.setIconImage(new ImageIcon("res/logo.png").getImage());
        window.setResizable(false);
        Game game = new Game();
        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        game.start();
    }
}