package SpaceMail.game;

import SpaceMail.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Meteor extends GameObject {
    int x;
    int y;

    int vx;
    int vy;
    float angle;
    int R = 5; // effects speed of bullet
    BufferedImage meteorImg;
    Rectangle hitBox;
    private final float ROTATIONSPEED = 3f;
    private boolean draw_img;
    private boolean UpPressed;
    private boolean DownPressed;
    int tick = 400;


    public Meteor(int x, int y, float angle, BufferedImage bulletImage) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.meteorImg = bulletImage;
        this.hitBox = new Rectangle(x, y, this.meteorImg.getWidth(), this.meteorImg.getHeight());
        this.draw_img = true;
        this.UpPressed = true;
        this.DownPressed = true;
    }

    public boolean isUpPressed() {
        return UpPressed;
    }

    public boolean isDownPressed() {
        return DownPressed;
    }

    public void setUpPressed(boolean upPressed) {
        UpPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        DownPressed = downPressed;
    }

    public boolean isDraw_img() {
        return draw_img;
    }

    public void setDraw_img(boolean draw_img) {
        this.draw_img = draw_img;
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }



    public void moveForward()
    {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x,y);
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    public void checkBorder()
    {
        if (x < 30) {
            x = 30;
            //x = TRE.SCREEN_WIDTH / 4;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
            //y = TRE.SCREEN_HEIGHT / 2;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    public void update()
    {
        Random rand = new Random();
        this.tick--;
        int randomNum = rand.nextInt((5 - 1) + 1) + 1;

        if(TRE.tick % 5 == 0)
        {
            if(this.isUpPressed())
                this.moveForward();
        }
        if(TRE.tick % 15 == 0)
        {
            if(this.isDownPressed())
                this.moveBackwards();
            this.rotateLeft();
        }
        if(TRE.tick % 20 == 0)
        {
            this.rotateRight();
            this.rotateLeft();
            if(this.isUpPressed())
                this.moveForward();
        }
        switch(randomNum)
        {
            case 1:
                this.rotateLeft();
                break;
            case 2:
                this.rotateRight();
                this.moveBackwards();
                break;
            case 3:
                this.moveBackwards();
                this.moveForward();
                this.moveForward();
                break;
            case 4:
                this.rotateRight();
                this.rotateLeft();
                this.rotateRight();
                this.moveForward();
                break;
            case 5:
                this.moveForward();
                this.moveBackwards();
                this.moveForward();
                break;
        }
        if(this.tick == 0)
            this.setDraw_img(false);
    }

    public void drawImage(Graphics g)
    {
        if(this.draw_img) {
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.meteorImg.getWidth() / 2.0, this.meteorImg.getHeight() / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.meteorImg, rotation, null);
            // draw something that represents the rectangle hitbox (BUT IS NOT THE HITBOX)
            //g2d.setColor(Color.BLUE);
            //g2d.drawRect(x, y, this.meteorImg.getWidth(), this.meteorImg.getHeight());
        }
        else
        {
            this.hitBox.setBounds(0,0,0,0);
        }
    }
}