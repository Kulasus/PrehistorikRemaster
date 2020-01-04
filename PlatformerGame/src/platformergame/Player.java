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
public class Player{
    private Point2D playerVelocity;
    private boolean canJump = true;
    private Node playerEntity;
    
    
    public Player(int x, int y, int h, int w, Color color, Pane pane, Point2D velocity, Entity entityCreator){
        this.playerEntity = entityCreator.createEntity(x, y, w, h, color, pane);
        this.playerVelocity = velocity;
    }

    public void setPlayerVelocity(Point2D playerVelocity) {
        this.playerVelocity = playerVelocity;
    }
    
    public Point2D getPlayerVelocity() {
        return playerVelocity;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public Node getPlayerEntity() {
        return playerEntity;
    }
}
