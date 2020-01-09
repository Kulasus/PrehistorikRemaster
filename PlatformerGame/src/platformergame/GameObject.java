/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformergame;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 *
 * @author lukas
 */
public class GameObject {
    private Point2D velocity;
    private boolean canJump = true;
    private int x;
    private int y;
    private Color color;
    
    public GameObject(int x, int y, Color color, Point2D velocity){
        this.velocity = velocity;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public boolean isCanJump() {
        return canJump;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
