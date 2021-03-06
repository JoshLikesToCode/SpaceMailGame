package SpaceMail.game;

import java.awt.*;

public abstract class GameObject {
    public abstract void drawImage(Graphics g);
    public abstract void update();
    public abstract Rectangle getHitBox();
}
