/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformergame;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 *
 * @author lukas
 */
public class CollectibleObject extends GameObject{
    int r;
    private Circle entity;
    public CollectibleObject(int x, int y, int r, ImagePattern imgPattern, Pane pane, Point2D velocity){
        super(x,y,imgPattern,velocity);
        this.r = r;
        Circle collectible = new Circle(r);
        collectible.setTranslateX(x);
        collectible.setTranslateY(y);
        collectible.setFill(imgPattern);
        this.entity = collectible;
        pane.getChildren().add(this.entity);
    }

    public Circle getEntity() {
        return entity;
    }
    
    
    
}
