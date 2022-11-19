package com.sensor;

import com.sensor.controller.app.ControllerMenu;
import com.sensor.controller.app.HiloConnectArduinoDB;
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
