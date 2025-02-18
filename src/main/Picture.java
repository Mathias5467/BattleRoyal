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
    public Picture(int  x, int y, int width, int height, String picturePath){
        this.picturePath = picturePath;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.picture = new ImageIcon(this.picturePath);
    }

    public void changeCords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void changeImage(String newPath) {
        this.picturePath = newPath;
        this.picture = new ImageIcon(this.picturePath);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.picture.getImage() ,this.x, this.y, this.width, this.height,null);
    }
}
