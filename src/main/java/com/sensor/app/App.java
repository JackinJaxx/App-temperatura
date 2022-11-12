package com.sensor.app;

import com.sensor.app.controller.ControllerMenu;
import com.sensor.app.models.Threads.HiloConnectArduinoDB;
import javax.swing.*;
/**
 * Inicio del programa
 * @version 1.0
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 */

public class App {
    public static void main(String[] args) {
        Runnable runApplication = () -> {
            new HiloConnectArduinoDB().start();
            new ControllerMenu();
        };
        SwingUtilities.invokeLater(runApplication);

    }
}
