package SpaceMail.game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 *
 * @author anthony-pc
 */
public class SpaceShipControl implements KeyListener {

    private PlayerSpaceShip space_ship;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int shoot;

    public SpaceShipControl(PlayerSpaceShip space_ship, int up, int down, int left, int right, int shoot) {
        this.space_ship = space_ship;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
    }


    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            this.space_ship.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.space_ship.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.space_ship.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.space_ship.toggleRightPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == up) {
            this.space_ship.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.space_ship.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.space_ship.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.space_ship.unToggleRightPressed();
        }
    }
}
