package com.exalttech.trex.ui.controllers.Dashboard;

import com.exalttech.trex.core.RPCMethods;
import com.exalttech.trex.ui.PortsManager;
import com.exalttech.trex.ui.controllers.MainViewController;
import com.exalttech.trex.ui.models.Port;
import com.exalttech.trex.ui.views.StatsInfoView;
import com.exalttech.trex.ui.views.services.RefreshingService;
import com.exalttech.trex.ui.views.statistics.StatsLoader;
import com.exalttech.trex.ui.views.statistics.StatsTableGenerator;
import com.exalttech.trex.util.Constants;
import com.exalttech.trex.util.Util;
import com.google.inject.Injector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class DashboardTabStreams extends BorderPane {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MainViewController.class.getName());

    private RPCMethods serverRPCMethods;
    private PortsManager portManager;

    Stage currentStage;
    RefreshingService readingStatService = new RefreshingService();

    public DashboardTabStreams(Injector injector, RPCMethods serverRPCMethods, Stage stage) {
        this.serverRPCMethods = serverRPCMethods;
        this.portManager = PortsManager.getInstance();
        this.currentStage = stage;

        FXMLLoader fxmlLoader = injector.getInstance(FXMLLoader.class);
        fxmlLoader.setLocation(getClass().getResource("/fxml/Dashboard/DashboardStreams.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (Exception e) {
            LOG.error("Failed to load fxml file: " + e.getMessage());
        }

        initializze();
    }

    /**
     *
     */
    //@Override
    public void initializze() {
        initializeReadingStats();
        initCloseAndSize();
    }

    /**
     * Initialize reading stats thread
     */
    private void initializeReadingStats() {
        readingStatService = new RefreshingService();
        readingStatService.setPeriod(Duration.seconds(Constants.REFRESH_TWO_INTERVAL_SECONDS));
        readingStatService.setOnSucceeded((WorkerStateEvent event) -> {
            ;
        });
        readingStatService.start();
    }

    /**
     * Init stage
     */
    public void initCloseAndSize() {
        currentStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, (window) -> {
            if (readingStatService.isRunning()) {
                readingStatService.cancel();
            }
            Util.optimizeMemory();
        });

        // add size listener
        currentStage.getScene().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                ;
            }
        });

        // add size listener
        currentStage.getScene().heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                ;
            }
        });
    }

}