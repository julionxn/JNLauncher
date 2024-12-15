package me.julionxn.jnlauncher.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import me.julionxn.jnlauncher.Application;
import me.julionxn.jnlauncher.BaseController;

import java.net.URL;
import java.util.ResourceBundle;

public class CallbackViewController extends BaseController implements Initializable {

    private static final Color HIGHLIGHT_COLOR = new Color(0.6352941176470588f, 0.1725490196078431f, 0.1607843137254902f, 1f);
    @FXML private AnchorPane mainPane;
    @FXML private ImageView loadingView;
    private Rectangle progressRectangle;
    private Label statusLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLoadingView();
        setBackground("img/CallbackBackground.png", 150, 300, mainPane);
        setDragable(mainPane, false);
        buildScene();
    }

    private void buildScene(){
        Rectangle rectangle = new Rectangle(213, 27);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        rectangle.setLayoutX(44);
        rectangle.setLayoutY(81);

        progressRectangle = new Rectangle(217, 31);
        progressRectangle.setArcWidth(10);
        progressRectangle.setArcHeight(10);
        progressRectangle.setLayoutX(42);
        progressRectangle.setLayoutY(79);
        progressRectangle.setFill(HIGHLIGHT_COLOR);

        statusLabel = new Label("Loading...");
        statusLabel.setPrefWidth(185);
        statusLabel.setPrefHeight(22);
        statusLabel.setLayoutX(58);
        statusLabel.setLayoutY(84);
        statusLabel.setAlignment(javafx.geometry.Pos.CENTER);
        statusLabel.setStyle("-fx-text-fill: #d6d5c9");
        mainPane.getChildren().addAll(progressRectangle, rectangle, statusLabel);
    }

    public void onCallback(String status, float progress){
        statusLabel.setText(status);
        progressRectangle.setWidth((double) 217 * progress);
    }

    private void setLoadingView(){
        Image gif = new Image(Application.getResource("img/loading2.gif").toExternalForm());
        loadingView.setImage(gif);
        loadingView.setVisible(true);
    }

    public void close(){
        stage.close();
    }

}
