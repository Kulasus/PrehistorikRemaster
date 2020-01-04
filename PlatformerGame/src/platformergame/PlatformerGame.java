/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package platformergame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;


public class PlatformerGame extends Application {

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();
    private Entity entityCreator = new Entity();
    private Point2D playerVelocity = new Point2D(0, 0);
    private Player player = new Player(0, 600, 40, 40, Color.DARKBLUE, gameRoot, playerVelocity, entityCreator);
    private Game game = new Game(1280, 720, Color.AQUA, LevelData.LEVEL1, entityCreator, appRoot, gameRoot, uiRoot, player);
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        game.initContent();
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> game.getKeys().put(event.getCode(), true));
        scene.setOnKeyReleased(event -> game.getKeys().put(event.getCode(), false));
        primaryStage.setTitle("Prehistorik");
        primaryStage.setScene(scene);
        primaryStage.show();

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
