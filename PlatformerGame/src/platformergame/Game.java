/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformergame;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Game {
    private Player player;
    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private ArrayList<Node> platforms = new ArrayList<>();
    private ArrayList<Node> collectibles = new ArrayList<>();
    private Rectangle bg;
    private int levelWidth;
    private String[] levelMap;
    private Entity entityCreator;
    private Pane appPane, gamePane, uiPane;
    private Point2D nullVector = new Point2D(0,0);
    private int score = 0;
    private TextField scoreboard = new TextField();
    
    public Game(int backgroundWidth, int backgroundHeight, Color backgoundColor, String[] levelMap, Entity entityCreator, Pane appPane, Pane gamePane, Pane uiPane, Player player){
        this.bg = new Rectangle(backgroundWidth, backgroundHeight);
        this.bg.setFill(backgoundColor);
        this.levelWidth = levelMap[0].length() * 60;
        this.levelMap = levelMap;
        this.entityCreator = entityCreator;
        this.appPane = appPane;
        this.gamePane = gamePane;
        this.uiPane = uiPane;
        this.player = player;
    }
    
    public void initContent() throws NullPointerException{
        for (int i=0; i< levelMap.length; i++){
            String line = levelMap[i];
            for (int j=0; j <line.length();j++){
                switch (line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        Node platform = entityCreator.createEntity(j*60, i *60, 60, 60, Color.GREEN, gamePane);
                        platforms.add(platform);
                        break;
                    case '2':
                        Node collectible = entityCreator.createCollectible(j*60+30,i*60+30,30,Color.ORANGE,gamePane);
                        collectibles.add(collectible);
                }
            }
        }
        
        scoreboard.setDisable(true);
        scoreboard.setText(setScore());
        scoreboard.setAlignment(Pos.CENTER_RIGHT);
        uiPane.getChildren().add(scoreboard);
        
        
        player.getPlayerEntity().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth-640){
                gamePane.setLayoutX(-(offset-640));
            }
        });
        appPane.getChildren().addAll(bg, gamePane, uiPane);
    }
    
    public void update(){
        if (isPressed(KeyCode.W) && player.getPlayerEntity().getTranslateY() >= 5){
            jumpPlayer();
        }
        if (isPressed(KeyCode.A) && player.getPlayerEntity().getTranslateX() >=5){
            movePlayerX(-5);
        }
        if (isPressed(KeyCode.D) && player.getPlayerEntity().getTranslateX() + 40 <=levelWidth-5){
            movePlayerX(5);
        }
        if (player.getPlayerVelocity().getY() < 10){
            player.setPlayerVelocity(player.getPlayerVelocity().add(0,1));
        }
        movePlayerY((int)player.getPlayerVelocity().getY());
        System.out.println(player.getPlayerVelocity());
    }
    
    public String setScore(){
        StringBuilder sb = new StringBuilder();
        sb.append("Your score: ");
        sb.append(score);
        return sb.toString();
    }
    
    private void movePlayerX(int value) {
        boolean movingRight = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            //Platforms collision
            for (Node platform : platforms) {
                if (player.getPlayerEntity().getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getPlayerEntity().getTranslateX() + 40 == platform.getTranslateX() && player.getPlayerEntity().getTranslateY() + 40 != platform.getTranslateY()) {
                            return;
                        }
                    }
                    else {
                        if (player.getPlayerEntity().getTranslateX() == platform.getTranslateX() + 60 && player.getPlayerEntity().getTranslateY() + 40 != platform.getTranslateY()) {
                            return;
                        }
                    }
                }
            }
            
            //Collectibles collision
            for (Node collectible : collectibles) {
                if (player.getPlayerEntity().getBoundsInParent().intersects(collectible.getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getPlayerEntity().getTranslateX() + 40 == collectible.getTranslateX()-30 && player.getPlayerEntity().getTranslateY() + 40 != collectible.getTranslateY()) {
                            collectCollectible(collectible);
                            collectibles.remove(collectible);
                            score+=100;
                            scoreboard.setText(setScore());
                            return;
                        }
                    }
                    else {
                        if (player.getPlayerEntity().getTranslateX() == collectible.getTranslateX() + 30 && player.getPlayerEntity().getTranslateY() + 40 != collectible.getTranslateY()) {
                            return;
                        }
                    }
                }
            }
            player.getPlayerEntity().setTranslateX(player.getPlayerEntity().getTranslateX() + (movingRight ? 1 : -1));
        }
    }
    
    private void collectCollectible(Node collectible){
        entityCreator.createCollectible((int)collectible.getTranslateX(),(int)collectible.getTranslateY(),30, Color.ALICEBLUE, gamePane);
        
    }
    
    private void movePlayerY(int value){
        boolean movingDown = value > 0;
        for (int i=0; i < Math.abs(value);i++){
            for (Node platform : platforms){
                if(player.getPlayerEntity().getBoundsInParent().intersects(platform.getBoundsInParent())){
                    if(movingDown){
                        if (player.getPlayerEntity().getTranslateY() + 40 == platform.getTranslateY() && player.getPlayerEntity().getTranslateX() + 40 != platform.getTranslateX()){
                            player.setCanJump(true);
                            return;
                        }
                    }else {
                        if (player.getPlayerEntity().getTranslateY() == platform.getTranslateY() + 60 && player.getPlayerEntity().getTranslateX() + 40 != platform.getTranslateX()) {
                            player.setPlayerVelocity(player.getPlayerVelocity().add(0,5));
                            return;
                        }
                    }
                }
            }
            player.getPlayerEntity().setTranslateY(player.getPlayerEntity().getTranslateY() + (movingDown ? 1 : -1));
        }
    }
    
    private void jumpPlayer(){
        if(player.isCanJump()){
            player.setPlayerVelocity(player.getPlayerVelocity().add(0,-30));
            player.setCanJump(false);
        }
    }
    
    private boolean isPressed(KeyCode key){
        return keys.getOrDefault(key, false);
    }

    public Player getPlayer() {
        return player;
    }

    public HashMap<KeyCode, Boolean> getKeys() {
        return keys;
    }

    public ArrayList<Node> getPlatforms() {
        return platforms;
    }

    public Rectangle getBg() {
        return bg;
    }

    public int getLevelWidth() {
        return levelWidth;
    }
    
}
