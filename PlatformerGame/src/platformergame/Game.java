/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformergame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Game {
    private RectangleObject player;
    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private ArrayList<RectangleObject> platforms = new ArrayList<>();
    private ArrayList<CollectibleObject> collectibles = new ArrayList<>();
    private ArrayList<RectangleObject> monkeys = new ArrayList<>();
    private Rectangle bg;
    private int levelWidth;
    private String[] levelMap;
    private Pane appPane, gamePane, uiPane;
    private Point2D nullVector = new Point2D(0,0);
    private UI ui;
    private Random rand = new Random();
    
    public Game(int backgroundWidth, int backgroundHeight, Color backgroundColor, String[] levelMap, Pane appPane, Pane gamePane, Pane uiPane, RectangleObject player){
        this.levelWidth = levelMap[0].length() * 60;
        this.levelMap = levelMap;
        this.appPane = appPane;
        this.gamePane = gamePane;
        this.uiPane = uiPane;
        this.player = player;
        this.bg = new Rectangle(backgroundWidth,backgroundHeight);
        this.bg.setFill(backgroundColor);
        this.appPane.getChildren().add(bg);
        this.ui = new UI();
    }
    public void initContent() throws NullPointerException{
        for (int i=0; i< levelMap.length; i++){
            String line = levelMap[i];
            for (int j=0; j <line.length();j++){
                switch (line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        RectangleObject platform = new RectangleObject(j*60, i *60, 60, 60, Color.GREEN, gamePane, nullVector);
                        platforms.add(platform);
                        break;
                    case '2':
                        CollectibleObject collectible = new CollectibleObject(j*60+30,i*60+30,30,Color.ORANGE,gamePane,nullVector);
                        collectibles.add(collectible);
                        break;
                    case '3':
                        monkeys.add(new RectangleObject(j*60,i*60,60,60,Color.RED,gamePane,new Point2D(0, 0)));
                }
            }
        }
        
        ui.setScoreboard(uiPane);
        ui.setHealthboard(uiPane);
        
        player.getEntity().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth-640){
                gamePane.setLayoutX(-(offset-640));
            }
        });
        appPane.getChildren().addAll(gamePane, uiPane);
    }
    public void update(){
        if (isPressed(KeyCode.W) && player.getEntity().getTranslateY() >= 5){
            jumpPlayer();
        }
        if (isPressed(KeyCode.A) && player.getEntity().getTranslateX() >=5){
            movePlayerX(-5);
        }
        if (isPressed(KeyCode.D) && player.getEntity().getTranslateX() + 40 <=levelWidth-5){
            movePlayerX(5);
        }
        if (player.getVelocity().getY() < 10){
            player.setVelocity(player.getVelocity().add(0,1));
        }
        movePlayerY((int)player.getVelocity().getY());

        for (RectangleObject monkey : monkeys) {
            monkeyJump(monkey);
            if (monkey.getVelocity().getY() < 10){
                monkey.setVelocity(monkey.getVelocity().add(0,1));
            }
            moveMonkeyY((int)monkey.getVelocity().getY(),monkey);
        }
    }
    private void movePlayerX(int value) {
        boolean movingRight = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            //Platforms collision
            for (RectangleObject platform : platforms) {
                if (player.getEntity().getBoundsInParent().intersects(platform.getEntity().getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getEntity().getTranslateX() + 40 == platform.getEntity().getTranslateX() && player.getEntity().getTranslateY() + 40 != platform.getEntity().getTranslateY()) {
                            return;
                        }
                    }
                    else {
                        if (player.getEntity().getTranslateX() == platform.getEntity().getTranslateX() + 60 && player.getEntity().getTranslateY() + 40 != platform.getEntity().getTranslateY()) {
                            return;
                        }
                    }
                }
            }
            
            //Collectibles collision
            for (CollectibleObject collectible : collectibles) {
                if (player.getEntity().getBoundsInParent().intersects(collectible.getEntity().getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getEntity().getTranslateX() + 40 == collectible.getEntity().getTranslateX()-30 && player.getEntity().getTranslateY() + 40 != collectible.getEntity().getTranslateY()) {
                            collectCollectible(collectible);
                            ui.setScore(100);
                            return;
                        }
                    }
                    else {
                        if (player.getEntity().getTranslateX() == collectible.getEntity().getTranslateX() + 30 && player.getEntity().getTranslateY() + 40 != collectible.getEntity().getTranslateY()) {
                            collectCollectible(collectible);
                            ui.setScore(100);
                            return;
                        }
                    }
                }
            }
            
            
            //Monkey collision
            for (RectangleObject monkey : monkeys) {
                if (player.getEntity().getBoundsInParent().intersects(monkey.getEntity().getBoundsInParent())) {
                    if (movingRight) {
                        if (player.getEntity().getTranslateX() + 40 == monkey.getEntity().getTranslateX() && player.getEntity().getTranslateY() + 40 != monkey.getEntity().getTranslateY()) {
                            ui.setHealth(1);
                            jumpPlayer();
                            return;
                        }
                    }
                    else {
                        if (player.getEntity().getTranslateX() == monkey.getEntity().getTranslateX() + 60 && player.getEntity().getTranslateY() + 40 != monkey.getEntity().getTranslateY()) {
                            ui.setHealth(1);
                            jumpPlayer();
                            return;
                        }
                    }
                }
            }
            
            player.getEntity().setTranslateX(player.getEntity().getTranslateX() + (movingRight ? 1 : -1));
        }
    }
    
    private void collectCollectible(CollectibleObject collectible){
        CollectibleObject newCol = new CollectibleObject((int)collectible.getEntity().getTranslateX(),(int)collectible.getEntity().getTranslateY(),30, (Color)bg.getFill() , gamePane, nullVector);
        player.getEntity().toFront();
        collectibles.remove(collectible);
    }
    
    private void movePlayerY(int value){
        boolean movingDown = value > 0;
        for (int i=0; i < Math.abs(value);i++){
            //Platforms collision
            for (RectangleObject platform : platforms){
                if(player.getEntity().getBoundsInParent().intersects(platform.getEntity().getBoundsInParent())){
                    if(movingDown){
                        if (player.getEntity().getTranslateY() + 40 == platform.getEntity().getTranslateY() && player.getEntity().getTranslateX() + 40 != platform.getEntity().getTranslateX() && player.getEntity().getTranslateX() != platform.getEntity().getTranslateX()+60){
                            player.setCanJump(true);
                            return;
                        }
                    }else {
                        if (player.getEntity().getTranslateY() == platform.getEntity().getTranslateY() + 60 && player.getEntity().getTranslateX() + 40 != platform.getEntity().getTranslateX() && player.getEntity().getTranslateX() != platform.getEntity().getTranslateX()+60) {
                            player.setVelocity(player.getVelocity().add(0,5));
                            return;
                        }
                    }
                }
            }
            //Colectiblles collision
            for (CollectibleObject collectible : collectibles){
                if(player.getEntity().getBoundsInParent().intersects(collectible.getEntity().getBoundsInParent())){
                    if(movingDown){
                        if (player.getEntity().getTranslateY() + 40 == collectible.getEntity().getTranslateY()-30 && player.getEntity().getTranslateX() + 40 != collectible.getEntity().getTranslateX()){
                            collectCollectible(collectible);
                            collectibles.remove(collectible);
                            ui.setScore(100);
                            player.setCanJump(true);
                            return;
                        }
                    }else {
                        if (player.getEntity().getTranslateY() == collectible.getEntity().getTranslateY() + 30 && player.getEntity().getTranslateX() + 40 != collectible.getEntity().getTranslateX()) {
                            collectCollectible(collectible);
                            collectibles.remove(collectible);
                            ui.setScore(100);
                            player.setVelocity(player.getVelocity().add(0,5));
                            return;
                        }
                    }
                }
            }
            
            //Monkey collision
            for (RectangleObject monkey : monkeys){
                if(player.getEntity().getBoundsInParent().intersects(monkey.getEntity().getBoundsInParent())){
                    if(movingDown){
                        if (player.getEntity().getTranslateY() + 40 == monkey.getEntity().getTranslateY() && player.getEntity().getTranslateX() + 40 != monkey.getEntity().getTranslateX()){
                            ui.setHealth(1);
                            player.setVelocity(player.getVelocity().add(0,-30));
                            return;
                        }
                    }else {
                        if (player.getEntity().getTranslateY() == monkey.getEntity().getTranslateY() + 60 && player.getEntity().getTranslateX() + 40 != monkey.getEntity().getTranslateX()) {
                            ui.setHealth(1);
                            player.setVelocity(player.getVelocity().add(0,20));
                            return;
                        }
                    }
                }
            }
            player.setCanJump(false);
            player.getEntity().setTranslateY(player.getEntity().getTranslateY() + (movingDown ? 1 : -1));
        }
    }
    
    private void jumpPlayer(){
        if(player.isCanJump()){
            player.setVelocity(player.getVelocity().add(0,-30));
            player.setCanJump(false);
        }
    }
    
    private boolean isPressed(KeyCode key){
        return keys.getOrDefault(key, false);
    }

    public GameObject getPlayer() {
        return player;
    }

    public HashMap<KeyCode, Boolean> getKeys() {
        return keys;
    }

    public ArrayList<RectangleObject> getPlatforms() {
        return platforms;
    }

    public Rectangle getBg() {
        return bg;
    }

    public int getLevelWidth() {
        return levelWidth;
    }    

    private void moveMonkeyY(int value, RectangleObject monkey) {
        boolean movingDown = value >= 0;
        for (int i=0; i < Math.abs(value);i++){
            //Platforms collision
            for (RectangleObject platform : platforms){
                //System.out.println(monkey.toString() + "crashed");
                if(monkey.getEntity().getBoundsInParent().intersects(platform.getEntity().getBoundsInParent())){
                    if(movingDown){
                        if (monkey.getEntity().getTranslateY() + 60 == platform.getEntity().getTranslateY() && monkey.getEntity().getTranslateX() + 60 != platform.getEntity().getTranslateX()){
                            monkey.setCanJump(true);
                            return;
                        }
                    }else {
                        if (monkey.getEntity().getTranslateY() == platform.getEntity().getTranslateY() + 60 && monkey.getEntity().getTranslateX() + 60 != platform.getEntity().getTranslateX()) {
                            monkey.setVelocity(monkey.getVelocity().add(0,5));
                            return;
                        }
                    }
                }
            }
            monkey.setCanJump(false);
            monkey.getEntity().setTranslateY(monkey.getEntity().getTranslateY() + (movingDown ? 1 : -1));
        }   
    }

    private void monkeyJump(GameObject monkey) {
        if(rand.nextInt(100)==1){
            System.out.println(monkey.toString() + "jumped");
            if(monkey.isCanJump()){
                monkey.setVelocity(monkey.getVelocity().add(0,-40));
                monkey.setCanJump(false);
            }
        }
    }
}
