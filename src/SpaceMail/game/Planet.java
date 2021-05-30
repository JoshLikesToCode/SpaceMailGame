package SpaceMail.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Planet extends GameObject {
    int x, y;
    BufferedImage planetImage;
    private Rectangle hitBox;

    public Planet(int x, int y, BufferedImage planetImage) {
        this.x = x;
        this.y = y;
        this.planetImage = planetImage;
        this.hitBox = new Rectangle(x, y, planetImage.getWidth(), planetImage.getHeight());
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.planetImage, x, y, null);
        //g2.setColor(Color.CYAN);
        //g2.drawRect(x,y,this.planetImage.getWidth(), this.planetImage.getHeight());
    }
}
