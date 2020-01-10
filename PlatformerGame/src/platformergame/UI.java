/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformergame;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author lukas
 */
public class UI {
    Pane pane;
    TextField scoreboard = new TextField(); 
    TextField healthboard = new TextField();
    
    public void setScoreboard(Pane pane, RectangleObject player){
        scoreboard.setDisable(true);
        setScore(player);
        scoreboard.setAlignment(Pos.CENTER_RIGHT);
        scoreboard.setMaxWidth(200);
        pane.getChildren().add(scoreboard);
    }
    
    public void setHealthboard(Pane pane, RectangleObject player){
        healthboard.setDisable(true);
        setHealth(player);
        healthboard.setAlignment(Pos.CENTER_RIGHT);
        healthboard.setTranslateX(scoreboard.getMaxWidth());
        healthboard.setMaxWidth(200);
        pane.getChildren().add(healthboard);
    }
    public void setHealth(RectangleObject player){
        StringBuilder sb = new StringBuilder();
        sb.append("Your health: ");
        sb.append(player.getHealth());
        healthboard.setText(sb.toString());
    }
    public void setScore(RectangleObject player){    
        StringBuilder sb = new StringBuilder();
        sb.append("Your score: ");
        sb.append(player.getScore());
        scoreboard.setText(sb.toString());
    }
}
