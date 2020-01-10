/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformergame;

import java.io.File;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;


public class PlatformerGame extends Application {

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();
    private Point2D playerVelocity = new Point2D(0, 0);
    private RectangleObject player = new RectangleObject(0, 600, 40, 40, new ImagePattern(new Image("sprites/player_normal.png")), gameRoot, playerVelocity,10);
    private boolean gameRuns = true;
    private MediaPlayer musicPlayer = new MediaPlayer(new Media(new File("src/music/music_1.mp3").toURI().toString()));
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        Game game = new Game(1280, 720, Color.AQUA, new LevelData("src/leveldata/level1.txt"), appRoot, gameRoot, uiRoot, player, primaryStage);
        game.initContent();
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> game.getKeys().put(event.getCode(), true));
        scene.setOnKeyReleased(event -> game.getKeys().put(event.getCode(), false));
        primaryStage.setTitle("Prehistorik");
        primaryStage.setScene(scene);
        primaryStage.setMaxWidth(1280);
        primaryStage.setMaxHeight(750);
        primaryStage.show();
        musicPlayer.play();


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                game.update();
            }
        };
        timer.start();
        
        
    }
    public static void main(String[] args) {
        launch(args);
    }
}
