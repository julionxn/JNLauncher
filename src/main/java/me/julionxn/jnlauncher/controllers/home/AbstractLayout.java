package me.julionxn.jnlauncher.controllers.home;

import io.github.julionxn.Launcher;
import io.github.julionxn.cache.UserInfo;
import io.github.julionxn.profile.URLProfiles;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public abstract class AbstractLayout {

    protected final Stage stage;
    protected final AnchorPane base;
    protected final URLProfiles profiles;
    protected final UserInfo userInfo;
    protected final Launcher launcher;

    public AbstractLayout(Stage stage, AnchorPane baseLayout, URLProfiles profiles, UserInfo userInfo, Launcher launcher){
        this.stage = stage;
        this.base = baseLayout;
        this.profiles = profiles;
        this.userInfo = userInfo;
        this.launcher = launcher;
    }

    abstract public void show();
    abstract public void hide();

}
