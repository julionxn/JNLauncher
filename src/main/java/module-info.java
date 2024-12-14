module me.julionxn.jnlauncher {
    requires javafx.controls;
    requires javafx.fxml;
    requires minecraftlaunchercore;
    requires httpclient;
    requires MinecraftAuth;
    requires org.jetbrains.annotations;
    requires java.desktop;


    opens me.julionxn.jnlauncher to javafx.fxml;
    exports me.julionxn.jnlauncher;
    exports me.julionxn.jnlauncher.controllers;
    opens me.julionxn.jnlauncher.controllers to javafx.fxml;
}