package SpaceMail.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BarrierCannon extends GameObject {
    private int x;
    private int y;
    private ArrayList<Meteor> meteors;
    private BufferedImage img;
    private boolean shoot_pressed;
    private Rectangle hitBox;
    private float angle;

    public BarrierCannon(int x, int y, BufferedImage bImg, float a) {
        this.x = x;
        this.y = y;
        this.img = bImg;
        this.meteors = new ArrayList<>();
        this.hitBox = new Rectangle(x , y, bImg.getWidth(), bImg.getHeight());
        this.shoot_pressed = true;
        this.angle = a;
    }

    public void toggleShootPressed_ON()
    {
        this.shoot_pressed = true;
    }
    public void toggleShootPressed_OFF()
    {
        this.shoot_pressed = false;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    public void update()
    {
        if(TRE.tick % 500 == 0)
        {
            //System.out.println("Meteor being shot.");
            Meteor m = new Meteor(x,y, angle, Resource.getResourceImage("meteor"));
            this.meteors.add(m);
            TRE t = null;
            assert t != null;
            t.add_meteor(m);
        }
        this.meteors.forEach(meteor -> meteor.update());
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        this.meteors.forEach(meteor -> meteor.drawImage(g));
    }
}
