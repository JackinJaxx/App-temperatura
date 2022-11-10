package com.sensor.app;

<<<<<<< HEAD:src/main/java/com/sensor/app/App.java
import com.sensor.app.controllers.ControllerMenu;
=======
import App.Controller.ControllerMenu;
import App.Model.Threads.HiloConnectArduinoDB;
>>>>>>> eea6d66 (backup):src/App/App.java

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        Runnable runApplication = () -> {
            new HiloConnectArduinoDB().start();
            new ControllerMenu();
        };
        SwingUtilities.invokeLater(runApplication);

    }
}
