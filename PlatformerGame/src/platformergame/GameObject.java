/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformergame;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author lukas
 */
public class GameObject {
    private Point2D velocity;
    private boolean canJump = true;
    private Node entity;

    public GameObject(int x, int y, int h, int w, Color color, Pane pane, Point2D velocity, Entity entityCreator){
        this.entity = entityCreator.createEntity(x, y, w, h, color, pane);
        this.velocity = velocity;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public Node getEntity() {
        return entity;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }
    
}
