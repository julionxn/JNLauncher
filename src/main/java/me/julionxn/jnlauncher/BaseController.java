package me.julionxn.jnlauncher;

import io.github.julionxn.Launcher;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BaseController {

    protected Stage stage;
    protected Launcher launcher;
    private double offsetX = 0;
    private double offsetY = 0;

    public void init(Stage stage, Launcher launcher){
        this.stage = stage;
        this.launcher = launcher;
    }

    protected void setDragable(Pane pane){
        pane.setBackground(null);
        pane.setOnMousePressed(event -> {
            offsetX = event.getScreenX() - stage.getX();
            offsetY = event.getScreenY() - stage.getY();
        });

        pane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - offsetX);
            stage.setY(event.getScreenY() - offsetY);
        });
    }

    protected void setBackground(String resource, int width, int height, Pane mainPane){
        Image image = new Image(String.valueOf(Application.getResource(resource)));
        BackgroundImage bgImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(width, height,
                        false, false,
                        true, true)
        );
        mainPane.setBackground(new Background(bgImage));
    }

    protected void setIcon(String resource, Button button){
        setIcon(resource, button, 24);
    }

    protected void setIcon(String resource, Button button, int fit){
        Image homeIcon = new Image(Application.getResource(resource).toExternalForm());
        ImageView imageView = new ImageView(homeIcon);
        imageView.setFitHeight(fit);
        imageView.setFitWidth(fit);
        button.setGraphic(imageView);
    }

}
