/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformergame;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        Rectangle rect = new Rectangle(1,1);
        rect.setTranslateX(x);
        rect.setTranslateY(y);
        pane.getChildren().addAll(collectible,rect);
        return collectible;
    }
    public void createScoreBoard(Pane pane){
        Label scoreboard = new Label();
    }
}
