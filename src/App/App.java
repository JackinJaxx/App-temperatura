package App;

import App.Controller.ControllerMenu;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        Runnable runApplication = () -> new ControllerMenu();
        SwingUtilities.invokeLater(runApplication);
        System.out.println("hola guichito");
    }
}
