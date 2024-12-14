package me.julionxn.jnlauncher.controllers;

import io.github.julionxn.Launcher;
import io.github.julionxn.cache.UserInfo;
import io.github.julionxn.profile.URLProfiles;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import me.julionxn.jnlauncher.Application;
import me.julionxn.jnlauncher.BaseController;

import java.net.URL;
import java.util.ResourceBundle;

public class PresentationViewController extends BaseController implements Initializable {

    @FXML private AnchorPane mainPane;
    private UserInfo userInfo;
    private URLProfiles urlProfiles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBackground("img/Plaque.png", 400, 400, mainPane);
        mainPane.setCursor(Cursor.WAIT);
    }

    @Override
    public void init(Stage stage, Launcher launcher) {
        super.init(stage, launcher);
        Thread profilesThread = new Thread(this::loadProfiles, "ProfileLoaderThread");
        profilesThread.start();
    }

    public void setUserInfo(UserInfo userInfo){
        this.userInfo = userInfo;
    }

    private void loadProfiles(){
        long currentTime = System.currentTimeMillis();
        urlProfiles = launcher.getProfilesController().getProfilesFrom("http://localhost/Testing/");
        long elapsedTime = System.currentTimeMillis() - currentTime;
        int toElapse = 5000;
        if (elapsedTime < toElapse){
            long sleepTime = toElapse - elapsedTime;
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Platform.runLater(this::done);
    }

    private void done(){
        Application.openView(stage, launcher, "home-view.fxml", null, controller -> {
            HomeViewController viewController = (HomeViewController) controller;
            viewController.setData(userInfo, urlProfiles);
        });
    }

}