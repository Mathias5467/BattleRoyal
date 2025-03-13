package main;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Picture {
    private ImageIcon picture;
    private String picturePath;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isVisible;
    public Picture(int  x, int y, int width, int height, String picturePath) {
        this.picturePath = picturePath;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.picture = new ImageIcon(this.picturePath);
        this.isVisible = true;
    }

    public void changeCords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void changeImage(String newPath) {
        this.picturePath = newPath;
        this.picture = new ImageIcon(this.picturePath);
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        if (this.isVisible) {
            g2.drawImage(this.picture.getImage(), this.x, this.y, this.width, this.height, null);
        }
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }
}
