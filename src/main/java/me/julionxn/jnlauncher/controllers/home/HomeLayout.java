package me.julionxn.jnlauncher.controllers.home;

import io.github.julionxn.Launcher;
import io.github.julionxn.cache.UserInfo;
import io.github.julionxn.profile.URLProfile;
import io.github.julionxn.profile.URLProfiles;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HomeLayout extends AbstractLayout {

    private final List<ProfileItem> items = new ArrayList<>();

    public HomeLayout(Stage stage, AnchorPane baseLayout, URLProfiles profiles, UserInfo userInfo, Launcher launcher) {
        super(stage, baseLayout, profiles, userInfo, launcher);
        if (profiles != null && !profiles.getAllProfiles().isEmpty()){
            createItems();
        }
    }

    private void createItems(){
        for (URLProfile urlProfile : profiles.getAllProfiles()) {
            ProfileItem profileItem = new ProfileItem(stage, urlProfile, launcher, userInfo.playerInfo());
            items.add(profileItem);
        }
    }

    @Override
    public void show() {
        int initialX = 74;
        int initialY = 34;
        int horizontalSpacing = 46;
        int verticalSpacing = 28;
        int itemsPerRow = 5;
        for (int i = 0; i < items.size(); i++) {
            int row = i / itemsPerRow;
            int col = i % itemsPerRow;
            double x = initialX + col * (horizontalSpacing + 100);
            double y = initialY + row * (verticalSpacing + 140);
            StackPane stackPane = items.get(i).getStackPane();
            AnchorPane.setLeftAnchor(stackPane, x);
            AnchorPane.setTopAnchor(stackPane, y);
            base.getChildren().add(stackPane);
        }
    }

    @Override
    public void hide() {
        base.getChildren().clear();
    }
}
