package com.comicmunchies.ip;

/**
 * Created by Simon.Edwardsson on 10/19/13.
 */
public class Panel {
    private Image image;

    public Panel(Image image, int minX, int minY) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
}
