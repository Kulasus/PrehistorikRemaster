/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformergame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Game {
    private RectangleObject player;
    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private ArrayList<RectangleObject> platforms = new ArrayList<>();
    private ArrayList<CollectibleObject> collectibles = new ArrayList<>();
    private ArrayList<RectangleObject> monkeys = new ArrayList<>();
    private Rectangle bg;
    private String[] levelMap;
    private Pane appPane, gamePane, uiPane;
    private Point2D nullVector = new Point2D(0,0);
    private UI ui;
    private Random rand = new Random();
    private boolean gameEnded = false;
    private int backgroundHeight, backgroundWidth, levelWidth, maxScore = 0;
    private Stage primaryStage;
    private RotateTransition monkeyRotator = new RotateTransition();
    
    public Game(int backgroundWidth, int backgroundHeight, Color backgroundColor, String[] levelMap, Pane appPane, Pane gamePane, Pane uiPane, RectangleObject player, Stage primaryStage){
        this.levelWidth = levelMap[0].length() * 60;
        this.backgroundHeight = backgroundHeight;
        this.backgroundWidth = backgroundWidth;
        this.levelMap = levelMap;
        this.appPane = appPane;
        this.gamePane = gamePane;
        this.uiPane = uiPane;
        this.player = player;
        System.out.println(this.player.getHealth());
        this.bg = new Rectangle(backgroundWidth,backgroundHeight);
        this.bg.setFill(backgroundColor);
        this.appPane.getChildren().add(bg);
        this.ui = new UI();
        this.primaryStage = primaryStage;
        this.monkeyRotator.setAxis(Rotate.Z_AXIS);
        this.monkeyRotator.setByAngle(360);
        this.monkeyRotator.setCycleCount(1);
        this.monkeyRotator.setDuration(Duration.millis(1000));  
    }
    public void initContent(){
        gameEnded = false;
        for (int i=0; i< levelMap.length; i++){
            String line = levelMap[i];
            for (int j=0; j <line.length();j++){
                switch (line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                        RectangleObject ground_1 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/ground_1.png")), gamePane, nullVector, 1);
                        platforms.add(ground_1);
                        break;
                    case '2':
                        RectangleObject ground_2 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/ground_2.png")), gamePane, nullVector, 1);
                        platforms.add(ground_2);
                        break;
                    case '3':
                        RectangleObject ground_3 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/ground_3.png")), gamePane, nullVector, 1);
                        platforms.add(ground_3);
                        break;
                    case 'q':
                        RectangleObject cave_ceiling_1 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/cave_ceiling_1.png")), gamePane, nullVector, 1);
                        platforms.add(cave_ceiling_1);
                        break;
                    case 'w':
                        RectangleObject cave_ceiling_2 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/cave_ceiling_2.png")), gamePane, nullVector, 1);
                        platforms.add(cave_ceiling_2);
                        break;
                    case 'e':
                        RectangleObject cave_ceiling_3 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/cave_ceiling_3.png")), gamePane, nullVector, 1);
                        platforms.add(cave_ceiling_3);
                        break;  
                    case 'r':
                        RectangleObject cave_ground_1 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/ground_1.png")), gamePane, nullVector, 1);
                        platforms.add(cave_ground_1);
                        break;
                    case 't':
                        RectangleObject cave_ground_2 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/cave_ground_2.png")), gamePane, nullVector, 1);
                        platforms.add(cave_ground_2);
                        break;
                    case 'z':
                        RectangleObject cave_ground_3 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/cave_ground_3.png")), gamePane, nullVector, 1);
                        platforms.add(cave_ground_3);
                        break;
                    case 'u':
                        RectangleObject cave_ground_4 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/cave_ground_4.png")), gamePane, nullVector, 1);
                        platforms.add(cave_ground_4);
                        break;
                    case 'i':
                        RectangleObject cave_ground_5 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/cave_ground_5.png")), gamePane, nullVector, 1);
                        platforms.add(cave_ground_5);
                        break;
                    case 'o':
                        RectangleObject cave_ground_6 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/cave_ground_6.png")), gamePane, nullVector, 1);
                        platforms.add(cave_ground_6);
                        break;           
                    case 'p':
                        RectangleObject cave_ground_7 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/cave_ground_7.png")), gamePane, nullVector, 1);
                        platforms.add(cave_ground_7);
                        break;
                    case 'a':
                        RectangleObject jungle_mid_1 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/jungle_platform_middle_1.png")), gamePane, nullVector, 1);
                        platforms.add(jungle_mid_1);
                        break;
                    case 's':
                        RectangleObject jungle_mid_2 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/jungle_platform_middle_2.png")), gamePane, nullVector, 1);
                        platforms.add(jungle_mid_2);
                        break;
                    case 'd':
                        RectangleObject jungle_left = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/jungle_platform_left_corner_1.png")), gamePane, nullVector, 1);
                        platforms.add(jungle_left);
                        break;
                    case 'f':
                        RectangleObject jungle_right = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/jungle_platform_right_corner_1.png")), gamePane, nullVector, 1);
                        platforms.add(jungle_right);
                        break;
                    case 'g':
                        RectangleObject jungle_center = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/jungle_platform_middle_3.png")), gamePane, nullVector, 1);
                        platforms.add(jungle_center);
                        break;
                    case '*':
                        RectangleObject tree_1 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/tree_1.png")), gamePane, nullVector, 1);
                        platforms.add(tree_1);
                        break;
                    case '-':
                        RectangleObject tree_2 = new RectangleObject(j*60, i *60, 60, 60, new ImagePattern(new Image("sprites/tree_2.png")), gamePane, nullVector, 1);
                        platforms.add(tree_2);
                        break;
                    case 'C':
                        CollectibleObject collectible = new CollectibleObject(j*60+30,i*60+30,30,new ImagePattern(new Image("sprites/collectible.png")),gamePane,nullVector);
                        collectibles.add(collectible);
                        maxScore+=100;
                        break;
                    case 'M':
                        monkeys.add(new RectangleObject(j*60,i*60,60,60,new ImagePattern(new Image("sprites/monkey_normal.png")),gamePane,new Point2D(0, 0), 20));
                        maxScore+=50;
                }
            }
        }
        
        ui.setScoreboard(uiPane);
        ui.setHealthboard(uiPane, player);
        
        player.getEntity().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth-640){
                gamePane.setLayoutX(-(offset-640));
            }
        });
        appPane.getChildren().addAll(gamePane, uiPane);
    }
    public void initLostEndGame(){
        Label endGameTitleLabel = new Label();
        endGameTitleLabel.setText("You died!");
        endGameTitleLabel.setTextFill(Color.RED);
        endGameTitleLabel.setFont(new Font("Arial",150));
        endGameTitleLabel.setMaxWidth(backgroundWidth/2);
        endGameTitleLabel.setMaxHeight(backgroundHeight/2);
        endGameTitleLabel.setTranslateX(backgroundWidth / 2 - endGameTitleLabel.getMaxWidth() / 2 + Math.abs(gamePane.getLayoutX()));
        endGameTitleLabel.setTranslateY(backgroundHeight / 2 - endGameTitleLabel.getMaxHeight() /4);
        
        gamePane.getChildren().add(endGameTitleLabel);
    }
    public void initWonEndGame(){
        Label endGameTitleLabel = new Label();
        endGameTitleLabel.setText("You won!");
        endGameTitleLabel.setTextFill(Color.RED);
        endGameTitleLabel.setFont(new Font("Arial",150));
        endGameTitleLabel.setMaxWidth(backgroundWidth/2);
        endGameTitleLabel.setMaxHeight(backgroundHeight/2);
        endGameTitleLabel.setTranslateX(backgroundWidth / 2 - endGameTitleLabel.getMaxWidth() / 2 + Math.abs(gamePane.getLayoutX()));
        endGameTitleLabel.setTranslateY(backgroundHeight / 2 - endGameTitleLabel.getMaxHeight() /4);
        
        gamePane.getChildren().add(endGameTitleLabel);
    }
    public void initEndGame(){
        Label endGameScoreLabel = new Label();
        endGameScoreLabel.setText(ui.getFinalScore());
        endGameScoreLabel.setTextFill(Color.YELLOW);
        endGameScoreLabel.setFont(new Font("Arial",50));
        endGameScoreLabel.setMaxHeight(backgroundWidth/20);
        endGameScoreLabel.setTranslateX(Math.abs(gamePane.getLayoutX()));
        endGameScoreLabel.setTranslateY(backgroundHeight - endGameScoreLabel.getMaxHeight());
        
        Button endGameButton = new Button();
        endGameButton.setText("Close");
        endGameButton.setFont(new Font("Arial",35));
        endGameButton.setMaxWidth(backgroundWidth/8);
        endGameButton.setMaxHeight(backgroundHeight/6);
        endGameButton.setTranslateX(backgroundWidth/2 - endGameButton.getMaxWidth() / 2 + Math.abs(gamePane.getLayoutX()));
        endGameButton.setTranslateY(backgroundHeight / 2 + endGameButton.getMaxHeight());
        endGameButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override 
            public void handle(ActionEvent e) {
                primaryStage.close();
            }
        });
        gamePane.getChildren().addAll(endGameScoreLabel, endGameButton);
    }
    public void update(){
        if(!gameEnded){
            //Game ended check
            if(player.getHealth() <= 0){
                initLostEndGame();
                initEndGame();
                gameEnded = true;
            }
            if(maxScore == ui.getScore()){
                initWonEndGame();
                initEndGame();
                gameEnded = true;
            }
            //Keys pressed
            if (isPressed(KeyCode.W) && player.getEntity().getTranslateY() >= 5){
                jumpPlayer();
            }
            if (isPressed(KeyCode.A) && player.getEntity().getTranslateX() >=5){
                movePlayerX(-5);
            }
            if (isPressed(KeyCode.D) && player.getEntity().getTranslateX() + 40 <=levelWidth-5){
                movePlayerX(5);
            }
            if (isPressed(KeyCode.SPACE) && player.getEntity().getTranslateX() >=20 && player.getEntity().getTranslateX() + 40 <= levelWidth-20 && player.isCanJump())
            {
                attackPlayer();
            }
            //Gravity
            if (player.getVelocity().getY() < 10){
                player.setVelocity(player.getVelocity().add(0,1));
            }
            movePlayerY((int)player.getVelocity().getY());
            
            for(RectangleObject monkey : monkeys) {
                monkeyJump(monkey);
                if (monkey.getVelocity().getY() < 10){
                    monkey.setVelocity(monkey.getVelocity().add(0,1));
                }
                moveMonkeyY((int)monkey.getVelocity().getY(),monkey);
            }
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
                            player.setHealth(player.getHealth()+5);
                            ui.setHealth(player);
                            ui.setScore(100);
                            return;
                        }
                    }
                    else {
                        if (player.getEntity().getTranslateX() == collectible.getEntity().getTranslateX() + 30 && player.getEntity().getTranslateY() + 40 != collectible.getEntity().getTranslateY()) {
                            collectCollectible(collectible);
                            ui.setHealth(player);
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
                            player.setHealth(player.getHealth()-1);
                            ui.setHealth(player);
                            jumpPlayer();
                            return;
                        }
                    }
                    else {
                        if (player.getEntity().getTranslateX() == monkey.getEntity().getTranslateX() + 60 && player.getEntity().getTranslateY() + 40 != monkey.getEntity().getTranslateY()) {
                            player.setHealth(player.getHealth()-1);
                            ui.setHealth(player);
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
        collectible.getEntity().setFill((Color)bg.getFill());
        collectibles.remove(collectible);
        player.getEntity().toFront();
        
    }
    
    private void movePlayerY(int value){
        boolean movingDown = value > 0;
        for (int i=0; i < Math.abs(value);i++){
            //Platforms collision
            for (RectangleObject platform : platforms){
                if(player.getEntity().getBoundsInParent().intersects(platform.getEntity().getBoundsInParent())){
                    if(movingDown){
                        if (player.getEntity().getTranslateY() + 40 == platform.getEntity().getTranslateY() && player.getEntity().getTranslateX() + 40 != platform.getEntity().getTranslateX() && player.getEntity().getTranslateX() != platform.getEntity().getTranslateX()+60){
                            player.getEntity().setFill(new ImagePattern(new Image("sprites/player_normal.png")));
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
                            player.getEntity().setFill(new ImagePattern(new Image("sprites/player_normal.png")));
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
                            player.setHealth(player.getHealth()-1);
                            ui.setHealth(player);
                            jumpPlayer();
                            return;
                        }
                    }else {
                        if (player.getEntity().getTranslateY() == monkey.getEntity().getTranslateY() + 60 && player.getEntity().getTranslateX() + 40 != monkey.getEntity().getTranslateX()) {
                            player.setHealth(player.getHealth()-1);
                            ui.setHealth(player);
                            jumpPlayer();
                            return;
                        }
                    }
                }
            }
            player.getEntity().setTranslateY(player.getEntity().getTranslateY() + (movingDown ? 1 : -1));
        }
    }
    
    private void jumpPlayer(){
        if(player.isCanJump()){
            player.setVelocity(player.getVelocity().add(0,-30));
            player.getEntity().setFill(new ImagePattern(new Image("sprites/player_jump.png")));
            player.setCanJump(false);
        }
    }
    
    public boolean isPressed(KeyCode key){
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
                        if (monkey.getEntity().getTranslateY() + 60 == platform.getEntity().getTranslateY() && monkey.getEntity().getTranslateX() + 60 != platform.getEntity().getTranslateX() && monkey.getEntity().getTranslateX() != platform.getEntity().getTranslateX()+60){
                            monkey.getEntity().setFill(new ImagePattern(new Image("sprites/monkey_normal.png")));
                            monkey.setCanJump(true);
                            return;
                        }
                    }else {
                        if (monkey.getEntity().getTranslateY() == platform.getEntity().getTranslateY() + 60 && monkey.getEntity().getTranslateX() + 60 != platform.getEntity().getTranslateX() && monkey.getEntity().getTranslateX() != platform.getEntity().getTranslateX()+60) {
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

    private void monkeyJump(RectangleObject monkey) {
        if(rand.nextInt(100)==1){
            if(monkey.isCanJump()){
                monkey.setVelocity(monkey.getVelocity().add(0,-40));
                monkey.getEntity().setFill(new ImagePattern(new Image("sprites/monkey_jump.png")));
                monkeyRotator.setNode(monkey.getEntity());
                monkeyRotator.play();
                monkey.setCanJump(false);
            }
        }
    }

    private void attackPlayer() {
        for(RectangleObject monkey : monkeys){
            //attack from left
            if(player.getEntity().getTranslateX()+20+40 >= monkey.getEntity().getTranslateX() && player.getEntity().getTranslateX()+20+40 <= monkey.getEntity().getTranslateX()+20){
                monkey.setHealth(monkey.getHealth()-1);
                if(monkey.getHealth() <= 0){
                    monkeyDie(monkey);
                    ui.setScore(50);
                }
            }   
            //attack from right
            else if(player.getEntity().getTranslateX()-20 <= monkey.getEntity().getTranslateX()+60 && player.getEntity().getTranslateX()-20 >= monkey.getEntity().getTranslateX()-20+60){
                monkey.setHealth(monkey.getHealth()-1);
                if(monkey.getHealth() <= 0){
                    monkeyDie(monkey);
                    ui.setScore(50);
                }
            }
        }
    }
    private void monkeyDie(RectangleObject monkey) {
        monkey.getEntity().setFill((Color)bg.getFill());
        monkeys.remove(monkey);
        player.getEntity().toFront();
    }
}
