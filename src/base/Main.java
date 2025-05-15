package base;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.io.IOException;

/**
 * Hlavná trieda aplikácie {@code base.Main}, ktorá spúšťa hru "Battle Royal".
 * Vytvára okno aplikácie, inicializuje hru a zobrazí ju.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public class Main {
    /**
     * Hlavná metóda programu, ktorá je spustená pri spustení aplikácie.
     */
    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Battle Royal");
        window.setIconImage(new ImageIcon("res/logo.png").getImage());
        window.setResizable(false);
        Game game = new Game();
        window.add(game.getPanel());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        game.start();
    }
}