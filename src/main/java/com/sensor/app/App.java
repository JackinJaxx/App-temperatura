package com.sensor.app;

import com.sensor.app.controllers.ControllerMenu;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        Runnable runApplication = () -> new ControllerMenu();
        SwingUtilities.invokeLater(runApplication);

    }
}
