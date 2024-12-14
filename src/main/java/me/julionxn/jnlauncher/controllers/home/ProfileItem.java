package me.julionxn.jnlauncher.controllers.home;

import io.github.julionxn.Launcher;
import io.github.julionxn.instance.MinecraftInstance;
import io.github.julionxn.instance.MinecraftOptions;
import io.github.julionxn.instance.PlayerInfo;
import io.github.julionxn.profile.Profile;
import io.github.julionxn.profile.TempProfile;
import io.github.julionxn.profile.URLProfile;
import io.github.julionxn.version.MinecraftVersion;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import me.julionxn.jnlauncher.Application;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Optional;

public class ProfileItem {

    private static final Color DEFAULT_COLOR = new Color(0.403921568627451f, 0.4588235294117647f, 0.4196078431372549f, 0.5f);
    private static final Color HIGHLIGHT_COLOR = new Color(0.6352941176470588f, 0.1725490196078431f, 0.1607843137254902f, 0.5f);
    private static final Image NO_IMAGE_PROFILE;
    static {
        NO_IMAGE_PROFILE = new Image(String.valueOf(Application.getResource("img/defaultProfile.png")));
    }

    private final Stage stage;
    private final URLProfile urlProfile;
    private final String name;
    private final String description;
    private final @Nullable Path iconPath;
    private final Launcher launcher;
    private final PlayerInfo playerInfo;

    private final StackPane stackPane;

    public ProfileItem(Stage stage, URLProfile urlProfile, Launcher launcher, PlayerInfo playerInfo) {
        this.stage = stage;
        this.urlProfile = urlProfile;
        this.name = urlProfile.tempProfile().getProfileName();
        this.description = urlProfile.tempProfile().getDescription();
        this.iconPath = urlProfile.tempProfile().getTempImagePath().orElse(null);
        this.launcher = launcher;
        this.playerInfo = playerInfo;
        this.stackPane = buildStackPane();
    }

    private StackPane buildStackPane() {
        StackPane stackPane = new StackPane();
        Rectangle rectangle = new Rectangle(100, 140);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        rectangle.setFill(DEFAULT_COLOR);
        Image image = NO_IMAGE_PROFILE;
        if (iconPath != null) {
            image = new Image(iconPath.toUri().toString());
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        imageView.setPreserveRatio(false);
        Rectangle clip = new Rectangle(80, 80);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        imageView.setClip(clip);
        Label label = new Label(name);
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(imageView, label);
        vbox.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(rectangle, vbox);
        stackPane.setOnMouseEntered(e -> {
            stackPane.setStyle("-fx-cursor: hand;");
            rectangle.setFill(HIGHLIGHT_COLOR);
        });
        stackPane.setOnMouseExited(e -> {
            stackPane.setStyle("-fx-cursor: default;");
            rectangle.setFill(DEFAULT_COLOR);
        });
        stackPane.setOnMouseClicked(e -> {
            stage.setIconified(true);
            Thread gameThread = new Thread(this::startInstance, "GameThread");
            gameThread.start();
        });
        return stackPane;
    }

    private void startInstance(){
        MinecraftVersion minecraftVersion = urlProfile.minecraftVersion();
        Optional<MinecraftVersion> version = launcher.getVersionsController().installVersion(minecraftVersion, (status, progress) -> {});
        if (version.isEmpty()) return;
        TempProfile tempProfile = urlProfile.tempProfile();
        Optional<Profile> profile = tempProfile.save();
        if (profile.isEmpty()) return;
        MinecraftOptions minecraftOptions = new MinecraftOptions();
        MinecraftInstance instance = new MinecraftInstance(launcher, version.get(), minecraftOptions, profile.get(), playerInfo);
        instance.run();
    }

    public StackPane getStackPane(){
        return stackPane;
    }

}
