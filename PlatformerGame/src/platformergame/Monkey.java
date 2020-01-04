
package platformergame;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author lukas
 */
public class Monkey {
    private Point2D monkeyVelocity;
    private boolean canJump = true;
    private Node monkeyEntity;
    
    
    public Monkey(int x, int y, int h, int w, Color color, Pane pane, Point2D velocity, Entity entityCreator){
        this.monkeyEntity = entityCreator.createEntity(x, y, w, h, color, pane);
        this.monkeyVelocity = velocity;
    }

    public void setMonkeyVelocity(Point2D playerVelocity) {
        this.monkeyVelocity = playerVelocity;
    }
    
    public Point2D getMonkeyVelocity() {
        return monkeyVelocity;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public Node getMonkeyEntity() {
        return monkeyEntity;
    }
}
