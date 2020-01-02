/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformergame;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author lukas
 */
public class Entity {
    public Node createEntity(int x, int y, int w, int h, Color color, Pane pane){
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        pane.getChildren().add(entity);
        return entity;
    }
    public Node createCollectible(int x, int y, int r, Color color, Pane pane){
        Circle collectible = new Circle(r);
        collectible.setTranslateX(x);
        collectible.setTranslateY(y);
        collectible.setFill(color);
        pane.getChildren().add(collectible);
        return collectible;
    }
}
