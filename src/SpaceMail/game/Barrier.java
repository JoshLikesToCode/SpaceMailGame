package SpaceMail.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Barrier {
    int x, y;
    private Rectangle hitBox;
    private BufferedImage barrierImg;

    public Barrier(int x, int y, BufferedImage bImg)
    {
        this.x = x;
        this.y = y;
        this.barrierImg = bImg;
        this.hitBox = new Rectangle(x,y,bImg.getWidth(),bImg.getHeight());
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    public void drawImage(Graphics g)
    {
        Graphics g2 = (Graphics2D) g;
        g2.drawImage(this.barrierImg, x, y, null);
        //g2.setColor(Color.GREEN);
        //g2.drawRect(x,y,this.barrierImg.getWidth(),this.barrierImg.getHeight());
    }
}
