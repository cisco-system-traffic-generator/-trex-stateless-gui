package com.exalttech.trex.ui.controllers.daemon;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

public class ConfigTreeView extends TreeView {
    private UserConfigModel userConfigModel;
    private Runnable configUpdateCallback;
    private String title;
    public ConfigTreeView(UserConfigModel metadata, Runnable configUpdateCallback) {
        this(metadata, configUpdateCallback, "TRex config");
    }

    public ConfigTreeView(UserConfigModel metadata, Runnable configUpdateCallback, String title) {

        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);

        this.userConfigModel = metadata;
        this.configUpdateCallback = configUpdateCallback;
        this.title = title;
        rebuildFromValueData();
    }

    public void rebuildFromValueData() {
        this.setRoot(new TreeItem(this.title));

        for (ConfigNode configNode : this.userConfigModel.getValueFields()) {
            TreeItem newItem = new ConfigTreeItem(configNode, configUpdateCallback);
            this.getRoot().getChildren().add(newItem);
        }

        this.getRoot().setExpanded(true);
    }
}

