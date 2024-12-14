package me.julionxn.jnlauncher.controllers;

import io.github.julionxn.cache.UserInfo;
import io.github.julionxn.instance.PlayerInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import me.julionxn.jnlauncher.Application;
import me.julionxn.jnlauncher.BaseController;
import me.julionxn.jnlauncher.controllers.login.State;
import net.lenni0451.commons.httpclient.HttpClient;
import net.raphimc.minecraftauth.MinecraftAuth;
import net.raphimc.minecraftauth.step.java.session.StepFullJavaSession;
import net.raphimc.minecraftauth.step.msa.StepMsaDeviceCode;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.UUID;

public class LoginViewController extends BaseController implements Initializable {

    @FXML private AnchorPane mainPane;
    @FXML private AnchorPane dragable;
    @FXML private Button closeBtn;
    @FXML private Button loginBtn;
    @FXML private Button enterBtn;
    @FXML private Button logoutBtn;
    @FXML private ImageView headView;
    private UserInfo userInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBackground("img/LoginBackground.png", 700, 400, mainPane);
        setDragable(dragable);
        setButtonsEffects();
        Rectangle clip = new Rectangle(40, 40);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        headView.setClip(clip);
        setIcon("img/logout.png", logoutBtn);
        setIcon("img/close-mini.png", closeBtn);
        setState(State.LOGIN);
    }

    private void setButtonsEffects(){
        closeBtn.setOnMouseEntered(e -> {
            closeBtn.setStyle("-fx-cursor: hand;");
            closeBtn.getStyleClass().add("login-close-btn-hovered");
        });
        closeBtn.setOnMouseExited(e -> {
            closeBtn.getStyleClass().remove("login-close-btn-hovered");
            closeBtn.setStyle("-fx-cursor: defaul;");
        });
        addSidebarEffects(loginBtn);
        addSidebarEffects(enterBtn);
        logoutBtn.setOnMouseEntered(e -> {
            logoutBtn.getStyleClass().add("close-btn-hovered");
            closeBtn.setStyle("-fx-cursor: hand;");
        });
        logoutBtn.setOnMouseExited(e -> {
            logoutBtn.getStyleClass().remove("close-btn-hovered");
            closeBtn.setStyle("-fx-cursor: defaul;");
        });
    }

    private void addSidebarEffects(Button button){
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-cursor: hand; -fx-font-size: 18px");
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-cursor: defaul; -fx-font-size: 18px");
        });
    }

    public void loadUserInfo(UserInfo userInfo){
        this.userInfo = userInfo;
        Path imagePath = userInfo.headImage();
        if (imagePath != null){
            headView.setImage(new Image(imagePath.toString()));
        }
        setState(State.ENTER);
    }

    private void setState(State state){
        if (state == State.LOGIN){
            loginBtn.setVisible(true);
            enterBtn.setVisible(false);
            logoutBtn.setVisible(false);
            headView.setVisible(false);
        } else {
            loginBtn.setVisible(false);
            enterBtn.setVisible(true);
            logoutBtn.setVisible(true);
            headView.setVisible(true);
        }
    }

    @FXML
    private void login() throws Exception {
        HttpClient httpClient = MinecraftAuth.createHttpClient();
        StepFullJavaSession.FullJavaSession javaSession = MinecraftAuth.JAVA_DEVICE_CODE_LOGIN.getFromInput(httpClient, new StepMsaDeviceCode.MsaDeviceCodeCallback(msaDeviceCode -> {
            String verificationUri = msaDeviceCode.getDirectVerificationUri();
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.browse(new URI(verificationUri));
                } else {
                    System.out.println("Desktop is not supported on this platform.");
                }
            } catch (IOException | java.net.URISyntaxException e) {
                e.printStackTrace();
            }
        }));
        String name = javaSession.getMcProfile().getName();
        UUID uuid = javaSession.getMcProfile().getId();
        String token = javaSession.getMcProfile().getMcToken().getAccessToken();
        String skinUrl = javaSession.getMcProfile().getSkinUrl();
        long expirationDate = javaSession.getPlayerCertificates().getExpireTimeMs();
        PlayerInfo playerInfo = new PlayerInfo(name, uuid.toString(), token);
        Path imagePath = launcher.getCacheController().saveHeadImage(uuid.toString()).orElse(null);
        UserInfo userInfo = new UserInfo(playerInfo, skinUrl, expirationDate, imagePath);
        loadUserInfo(userInfo);
        launcher.getCacheController().storeUserInfo(userInfo);
    }

    @FXML
    private void logout(){
        setState(State.LOGIN);
        launcher.getCacheController().clearUserInfo();
    }

    @FXML
    private void enter(){
        Application.openView(stage, launcher, "presentation-view.fxml", (controller -> {
            PresentationViewController viewController = (PresentationViewController) controller;
            viewController.setUserInfo(userInfo);
        }), null);
    }

    @FXML
    private void close(){
        stage.close();
    }

}