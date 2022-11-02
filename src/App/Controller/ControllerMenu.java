package App.Controller;

import App.View.ViewMenu;

public class ControllerMenu {
    private ViewMenu viewMenu;

    public ControllerMenu() {
        viewMenu = new ViewMenu();
        initEvents();
    }

    public void initEvents() {
        viewMenu.labelGitHub.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/jackinjaxx"));
                } catch (java.io.IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }
}
