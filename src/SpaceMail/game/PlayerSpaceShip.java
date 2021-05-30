package SpaceMail.game;



import SpaceMail.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author anthony-pc
 */
public class PlayerSpaceShip extends GameObject {

    private int x;
    private int y;

    private int vx;
    private int vy;
    private float angle;

    private int gas;

    private final int R = 2;
    private final float ROTATIONSPEED = 3.0f;

    private int health;

    private Rectangle hitBox;

    private BufferedImage img;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean move_up;
    private boolean move_down;
    private boolean move_left;
    private boolean move_right;
    private boolean has_landed;


    PlayerSpaceShip(int x, int y, int vx, int vy, int angle, int g, BufferedImage img) {
        this.health = 100;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.gas = g;
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        this.move_down = true;
        this.move_left = true;
        this.move_right = true;
        this.move_up = true;
    }

    public boolean isHas_landed() {
        return has_landed;
    }

    public void setHas_landed(boolean has_landed) {
        this.has_landed = has_landed;
    }

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public boolean isMove_up() {
        return move_up;
    }

    public boolean isMove_down() {
        return move_down;
    }

    public boolean isMove_left() {
        return move_left;
    }


    public boolean isMove_right() {
        return move_right;
    }

    public int getHealth() {
        return health;
    }

    public void setMove_up(boolean move_up) {
        this.move_up = move_up;
    }

    public void setMove_down(boolean move_down) {
        this.move_down = move_down;
    }

    public void setMove_left(boolean move_left) {
        this.move_left = move_left;
    }

    public void setMove_right(boolean move_right) {
        this.move_right = move_right;
    }

    public Rectangle getHitBox() {
        return hitBox.getBounds();
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public boolean isUpPressed() {
        return UpPressed;
    }

    public boolean isDownPressed() {
        return DownPressed;
    }

    @Override
    public void update() {
        if(this.gas >= 100)
            this.gas = 100;
        else if(this.gas <= 0)
            this.gas = 0;

        if (this.UpPressed && move_up) {
            this.moveForwards();
        }
        if (this.DownPressed && move_down) {
            this.moveBackwards();
        }

        if (this.LeftPressed && move_left) {
            this.rotateLeft();
        }
        if (this.RightPressed && move_right) {
            this.rotateRight();
        }
    }


    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        this.hitBox.setLocation(x, y);
    }


    private void checkBorder() {
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVx() {
        return vx;
    }

    public int getVy() {
        return vy;
    }

    public float getAngle() {
        return angle;
    }


    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        // draw something that represents the rectangle hitbox (BUT IS NOT THE HITBOX)
        //g2d.setColor(Color.CYAN);
        //g2d.drawRect(x, y, this.img.getWidth(), this.img.getHeight());
    }
}