package SpaceMail.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Space extends GameObject {
    int x, y;
    BufferedImage spaceImg;
    private Rectangle hitbox;

    public Space(int x, int y, BufferedImage spaceImg) {
        this.x = x;
        this.y = y;
        this.spaceImg = spaceImg;
        this.hitbox = new Rectangle(0,0,0,0);
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getHitBox() {
       return hitbox.getBounds();
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.spaceImg, x, y, null);
    }
}
