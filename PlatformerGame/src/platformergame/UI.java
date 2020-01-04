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
    int score = 0;
    int health = 3;
    TextField scoreboard = new TextField(); 
    TextField healthboard = new TextField();
    
    public void setScoreboard(Pane pane){
        scoreboard.setDisable(true);
        setScore(score);
        scoreboard.setAlignment(Pos.CENTER_RIGHT);
        scoreboard.setMaxWidth(200);
        pane.getChildren().add(scoreboard);
    }
    
    public void setHealthboard(Pane pane){
        healthboard.setDisable(true);
        setHealth(0);
        healthboard.setAlignment(Pos.CENTER_RIGHT);
        healthboard.setTranslateX(scoreboard.getMaxWidth());
        healthboard.setMaxWidth(200);
        pane.getChildren().add(healthboard);
    }
    public void setHealth(int points){
        health -= points;
        StringBuilder sb = new StringBuilder();
        sb.append("Your health: ");
        sb.append(health);
        healthboard.setText(sb.toString());
    }
    
    
    public void setScore(int points){    
        score += points;
        StringBuilder sb = new StringBuilder();
        sb.append("Your score: ");
        sb.append(score);
        scoreboard.setText(sb.toString());
    }
    public int getScore(){return score;}
    public int getHealth(){return health;}
}
