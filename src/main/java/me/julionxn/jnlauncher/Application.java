package me.julionxn.jnlauncher;

import io.github.julionxn.Launcher;
import io.github.julionxn.LauncherData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.julionxn.jnlauncher.controllers.LoginViewController;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class Application extends javafx.application.Application {

    private static Launcher LAUNCHER;

    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(getResource("Poppins-Regular.ttf").toExternalForm(), -1);
        FXMLLoader fxmlLoader = new FXMLLoader(getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginViewController controller = fxmlLoader.getController();
        controller.init(stage, LAUNCHER);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("JNLauncher");
        stage.setScene(scene);
        stage.setResizable(false);
        LAUNCHER.getCacheController().getUserInfo().ifPresent(controller::loadUserInfo);
        stage.show();
    }

    public static void main(String[] args) {
        LauncherData launcherData = new LauncherData("JNLauncher", "0.1.0", "C:/");
        LAUNCHER = new Launcher(launcherData);
        LAUNCHER.start();
        launch();
    }

    public static URL getResource(String path){
        return Application.class.getResource(path);
    }

    public static void openView(Stage currentStage, Launcher launcher, String resource, @Nullable Consumer<BaseController> beforeInit, @Nullable Consumer<BaseController> afterInit){
        currentStage.close();
        try {
            FXMLLoader loader = new FXMLLoader(Application.getResource(resource));
            Parent root = loader.load();
            BaseController controller = loader.getController();
            Stage stage = new Stage();
            if (beforeInit != null){
                beforeInit.accept(controller);
            }
            controller.init(stage, launcher);
            if (afterInit != null){
                afterInit.accept(controller);
            }
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("JNLauncher");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }



}