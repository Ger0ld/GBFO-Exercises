package clickTargetGame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;

// Keyboard events
public class ClickTargetGame extends Application {

    private final int MAX_TIME = 5;
    private int points;
    private long lastClick;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Click the Target!");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(500, 500);
        //Image restart = new Image("restart.png");

        root.getChildren().add(canvas);

        Circle targetData = new Circle(100, 100, 32);
        points = 0;

        theScene.setOnMouseClicked(
                e -> {
                    lastClick = System.nanoTime();
                    if (targetData.contains(e.getX(), e.getY())) {
                        double distance = Math.sqrt(Math.pow(e.getX() - targetData.getCenterX(), 2) + Math.pow(e.getY() - targetData.getCenterY(), 2));

                        if (distance <= 4) {
                            points += 3;
                        } else if (distance <= 17) {
                            points += 2;
                        } else {
                            points++;
                        }

                        double x = 50 + 400 * Math.random();
                        double y = 50 + 400 * Math.random();
                        targetData.setCenterX(x);
                        targetData.setCenterY(y);
                    } else
                        points = 0;
                });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
        gc.setFont(theFont);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        //Image bullseye = new Image( "/bullseye.png" );
        Image bullseye = new Image(getClass().getResourceAsStream("./bullseye.png"));

        new AnimationTimer() {
            public void handle(long currentNanoTime) {

                double timediff = (currentNanoTime - lastClick) / 1000000000.0;
                if (lastClick == 0) {
                    timediff = 0;
                }
                if (timediff >= MAX_TIME) {
                    timediff = MAX_TIME;
                    stop();

                    //POPUP
                    Popup gameEndStats = new Popup();
                    Label scoreLabel = new Label("Final points: " + points);
                    scoreLabel.setFont(new Font(20));
                    Button restartButton = new Button("restart");
                    restartButton.setOnAction(event -> {
                        gameEndStats.hide();
                        points = 0;
                        lastClick = 0;
                        start();
                    });
                    VBox box = new VBox(scoreLabel, restartButton);
                    box.setStyle("-fx-background-color: #e7e4e4; -fx-padding: 10; -fx-alignment: Center; -fx-spacing: 10; -fx-border-color: #000000; -fx-border-width: 2");
                    gameEndStats.getContent().add(box);
                    gameEndStats.show(theStage);
                }
                // Clear the canvas
                gc.setFill(new Color(0.85, 0.85, 1.0, 1.0));
                gc.fillRect(0, 0, 512, 512);

                gc.drawImage(bullseye,
                        targetData.getCenterX() - targetData.getRadius(),
                        targetData.getCenterY() - targetData.getRadius());

                gc.setFill(Color.BLUE);

                String pointsText = String.format("Points: %d%n Time: %.3f", points, MAX_TIME - timediff);
                gc.fillText(pointsText, 360, 36);
                gc.strokeText(pointsText, 360, 36);


            }
        }.start();


        theStage.show();
    }
}
