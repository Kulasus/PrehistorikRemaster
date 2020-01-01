/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformergame;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author lukas
 */
public class PlatformerGame extends Application {
   
    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    
    private ArrayList<Node> platforms = new ArrayList<Node>();
    
    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();
    
    private Node player;
    private Point2D playerVelocity = new Point2D(0,0);
    private boolean canJump = true;
    
    private int levelWidth;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        initContent();
        
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(),true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        primaryStage.setTitle("Prehistorik");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        AnimationTimer timer = new AnimationTimer(){
            @Override
            public void handle(long now){
                update();
            }
        };
        timer.start();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private void jumpPlayer(){
        if(canJump){
            playerVelocity = playerVelocity.add(0,-30);
            canJump = false;
        }
    }
    
    private void movePlayerX(int value){
        boolean movingRight = value > 0;
        
        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : platforms) {
                if(player.getBoundsInParent().intersects(platform.getBoundsInParent())){
                    if(movingRight){
                        if(player.getTranslateX() + 40 == platform.getTranslateX()){
                            return;
                        }
                    }
                    else{
                        if(player.getTranslateX() == platform.getTranslateX() +60){
                            return;
                        }
                    }
                }
            }
            player.setTranslateX(player.getTranslateX() + (movingRight ? 1 : -1));
        }
    }
    
    private void movePlayerY(int value){
        boolean movingDown = value > 0;

            for (int i = 0; i < Math.abs(value); i++) {
                for (Node platform : platforms) {
                    if(player.getBoundsInParent().intersects(platform.getBoundsInParent())){
                        if(movingDown){
                            if(player.getTranslateY() + 40 == platform.getTranslateY()){
                                player.setTranslateY(player.getTranslateY()-1);
                                canJump = true;
                                return;
                            }
                        }
                        else{
                            if(player.getTranslateY() == platform.getTranslateY() +60){
                                return;
                            }
                        }
                    }
                }
                player.setTranslateX(player.getTranslateY() + (movingDown ? 1 : -1));
            }
    }
    
    private void update(){
        if(isPressed(KeyCode.W) && player.getTranslateY() >= 5){
            jumpPlayer();
        }
        if(isPressed(KeyCode.A) && player.getTranslateX() >= 5){
            movePlayerX(-5);
        }
        if(isPressed(KeyCode.D) && player.getTranslateX() * 40 <= levelWidth - 5){
            movePlayerX(5);
        }
        if(playerVelocity.getY() < 10){
            playerVelocity = playerVelocity.add(0,1);
        }
        movePlayerY((int)playerVelocity.getY());
    }
    
    private void initContent() {
        Rectangle background = new Rectangle(1200,720);
        levelWidth = LevelData.LEVEL1[0].length() * 60; //60 is blocksize
        
        for (int i = 0; i < LevelData.LEVEL1.length; i++) {
            String line = LevelData.LEVEL1[i];
            for (int j = 0; j < line.length(); j++) {
                switch(line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        Node platform = createEntity(j*60, i*60, 60, Color.BROWN);
                        platforms.add(platform);
                        break;
                }
            }  
        }
        player = createEntity(0,600,40,40,Color.BLUE)
        player.translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if(offset > 640 && offset < levelWidth - 640){
                gameRoot.setLayoutX(-(offset - 640));
            }
        });
        appRoot.getChildren().addAll(background,gameRoot,uiRoot);
    }

    private Node createEntity(int x, int y, int w, int h, Color color){
        Rectangle entity = new Rectangle(w,h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        
        gameRoot.getChildren().add(entity);
        return entity;        
    }
    
    private boolean isPressed(KeyCode keyCode) {
        return keys.getOrDefault(keyCode, false);
    }
    
}
