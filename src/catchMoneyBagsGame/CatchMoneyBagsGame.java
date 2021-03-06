package catchMoneyBagsGame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;

// Collect the Money Bags!
public class CatchMoneyBagsGame extends Application {

    final int SPEED = 500;
    private int score;
    private long lastNanoTime;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Collect the Money Bags!");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(512, 512);
        root.getChildren().add(canvas);

        ArrayList<String> input = new ArrayList<>();

        theScene.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    if (!input.contains(code))
                        input.add(code);
                });

        theScene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    input.remove(code);
                });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Font theFont = Font.font("Helvetica", FontWeight.BOLD, 24);
        gc.setFont(theFont);
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        Sprite briefcase = new Sprite();
        briefcase.setImage("./briefcase.png");
        briefcase.setPosition(200, 0);

        ArrayList<Sprite> moneybagList = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            Sprite moneybag = new Sprite();
            moneybag.setImage("./moneybag.png");
            double px = 350 * Math.random() + 50;
            double py = 350 * Math.random() + 50;
            moneybag.setPosition(px, py);
            moneybagList.add(moneybag);
        }

        lastNanoTime = System.nanoTime();

        score = 0;

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                lastNanoTime = currentNanoTime;

                // game logic
                briefcase.setVelocity(0, 0);
                if (input.contains("LEFT"))
                    briefcase.addVelocity(-SPEED, 0);
                if (input.contains("RIGHT"))
                    briefcase.addVelocity(SPEED, 0);
                if (input.contains("UP"))
                    briefcase.addVelocity(0, -SPEED);
                if (input.contains("DOWN"))
                    briefcase.addVelocity(0, SPEED);

                briefcase.update(elapsedTime);

                // collision detection

                Iterator<Sprite> moneybagIter = moneybagList.iterator();
                while (moneybagIter.hasNext()) {
                    Sprite moneybag = moneybagIter.next();
                    if (briefcase.intersects(moneybag)) {
                        moneybagIter.remove();
                        score++;
                    }
                }

                // render

                gc.clearRect(0, 0, 512, 512);
                briefcase.render(gc);

                for (Sprite moneybag : moneybagList)
                    moneybag.render(gc);

                String pointsText = "Cash: $" + (100 * score);
                gc.fillText(pointsText, 360, 36);
                gc.strokeText(pointsText, 360, 36);
            }
        }.start();

        theStage.show();
    }
}

