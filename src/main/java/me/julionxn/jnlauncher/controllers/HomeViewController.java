package me.julionxn.jnlauncher.controllers;

import io.github.julionxn.cache.UserInfo;
import io.github.julionxn.profile.URLProfiles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import me.julionxn.jnlauncher.Application;
import me.julionxn.jnlauncher.BaseController;
import me.julionxn.jnlauncher.controllers.home.AbstractLayout;
import me.julionxn.jnlauncher.controllers.home.HomeLayout;
import me.julionxn.jnlauncher.controllers.home.NotisLayout;
import me.julionxn.jnlauncher.controllers.home.SettingsLayout;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class HomeViewController extends BaseController implements Initializable {

    @FXML private AnchorPane mainPane;
    @FXML private AnchorPane dragable;
    @FXML private AnchorPane baseLayout;
    @FXML private Button close;
    @FXML private Button mini;
    @FXML private Button home;
    @FXML private Button notis;
    @FXML private Button settings;
    @FXML private ImageView headView;
    @FXML private Button logout;
    private UserInfo userInfo;
    private URLProfiles urlProfiles;
    private AbstractLayout currentLayout;
    private HomeLayout homeLayout;
    private NotisLayout notisLayout;
    private SettingsLayout settingsLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBackground("img/HomeBackground.png", 900, 600, mainPane);
        setDragable(dragable);
        setButtonsEffects();
        Rectangle clip = new Rectangle(40, 40);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        headView.setClip(clip);
        setIcons();
    }

    private void setIcons(){
        setIcon("img/home.png", home);
        setIcon("img/notis.png", notis);
        setIcon("img/settings.png", settings);
        setIcon("img/logout.png", logout);
        setIcon("img/close.png", close, 20);
        setIcon("img/mini.png", mini, 20);
    }

    private void setButtonsEffects(){
        close.setOnMouseEntered(e -> {
            close.setStyle("-fx-cursor: hand;");
            close.getStyleClass().add("close-btn-hovered");
        });
        close.setOnMouseExited(e -> {
            close.getStyleClass().remove("close-btn-hovered");
            close.setStyle("-fx-cursor: defaul;");
        });
        mini.setOnMouseEntered(e -> {
            mini.getStyleClass().add("sidebar-btn-hovered");
            mini.setStyle("-fx-cursor: hand;");
        });
        mini.setOnMouseExited(e -> {
            mini.getStyleClass().remove("sidebar-btn-hovered");
            mini.setStyle("-fx-cursor: defaul;");
        });
        addSidebarEffects(home);
        addSidebarEffects(notis);
        addSidebarEffects(settings);
        logout.setOnMouseEntered(e -> {
            logout.getStyleClass().add("close-btn-hovered");
            logout.setStyle("-fx-cursor: hand;");
        });
        logout.setOnMouseExited(e -> {
            logout.getStyleClass().remove("close-btn-hovered");
            logout.setStyle("-fx-cursor: defaul;");
        });
    }

    private void addSidebarEffects(Button button){
        button.setOnMouseEntered(e -> {
            button.getStyleClass().add("sidebar-btn-hovered");
            button.setStyle("-fx-cursor: hand;");
        });
        button.setOnMouseExited(e -> {
            button.getStyleClass().remove("sidebar-btn-hovered");
            button.setStyle("-fx-cursor: defaul;");
        });
    }

    public void setData(UserInfo userInfo, URLProfiles urlProfiles){
        this.userInfo = userInfo;
        this.urlProfiles = urlProfiles;
        homeLayout = new HomeLayout(stage, baseLayout, urlProfiles, userInfo, launcher);
        notisLayout = new NotisLayout(stage, baseLayout, urlProfiles, userInfo, launcher);
        settingsLayout = new SettingsLayout(stage, baseLayout, urlProfiles, userInfo, launcher);
        Path imagePath = userInfo.headImage();
        if (imagePath != null){
            headView.setImage(new Image(imagePath.toString()));
        }
        setLayout(homeLayout);
    }

    private void setLayout(AbstractLayout layout){
        if (currentLayout != null){
            currentLayout.hide();
        }
        layout.show();
        currentLayout = layout;
    }

    @FXML
    private void close(){
        stage.close();
    }

    @FXML
    private void mini(){
        stage.setIconified(true);
    }

    @FXML
    private void home(){
        setLayout(homeLayout);
    }

    @FXML
    private void notis(){
        setLayout(notisLayout);
    }

    @FXML
    private void settings(){
        setLayout(settingsLayout);
    }

    @FXML
    private void logout(){
        Application.openView(stage, launcher, "login-view.fxml", null, null);
    }

}
