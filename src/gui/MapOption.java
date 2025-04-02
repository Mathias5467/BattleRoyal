package gui;

import main.Picture;

public class MapOption {
    private Picture biom1;
    private Picture biom2;
    private Picture biom3;
    public MapOption() {
        this.biom1 = new Picture(100, 255, 400, 200, "res/back/show/Forest.png");
        this.biom2 = new Picture(400, 250, 400, 200, "res/back/show/Forest.png");
        this.biom3 = new Picture(700, 250, 400, 200, "res/back/show/Forest.png");
    }
}
