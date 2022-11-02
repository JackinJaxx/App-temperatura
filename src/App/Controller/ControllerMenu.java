package App.Controller;

import App.View.ViewMenu;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControllerMenu {
    private ViewMenu viewMenu;

    public ControllerMenu() {
        viewMenu = new ViewMenu();
        initEvents();
    }

    public void initEvents() {
        eventsTermometro();
        eventsLabelButtons();
        viewMenu.labelGitHub.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/jackinjaxx"));
                } catch (java.io.IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        viewMenu.labelTwitter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://twitter.com/jackinjaxx01"));
                } catch (java.io.IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    public void eventsLabelButtons() {
        viewMenu.labelButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewMenu.labelButton1.setIcon(new ImageIcon("Resources/Images/buttonMenuHover.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                viewMenu.labelButton1.setIcon(new ImageIcon("Resources/Images/buttonMenuPush.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewMenu.labelButton1.setIcon(new ImageIcon("Resources/Images/buttonMenuActive.png"));
            }
        });
        viewMenu.labelButton1.addFocusListener(new FocusListener() {
            boolean isFocus = false;

            @Override
            public void focusGained(FocusEvent e) {
                if (isFocus) {
                    viewMenu.labelButton1.setIcon(new ImageIcon("Resources/Images/buttonMenuHover.png"));

                } else {
                    isFocus = true;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                viewMenu.labelButton1.setIcon(new ImageIcon("Resources/Images/buttonMenuActive.png"));
            }
        });

        viewMenu.labelButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewMenu.labelButton2.setIcon(new ImageIcon("Resources/Images/button2MenuHover.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                viewMenu.labelButton2.setIcon(new ImageIcon("Resources/Images/button2MenuPush.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewMenu.labelButton2.setIcon(new ImageIcon("Resources/Images/button2MenuActive.png"));
            }
        });
        viewMenu.labelButton2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                viewMenu.labelButton2.setIcon(new ImageIcon("Resources/Images/button2MenuHover.png"));
            }

            @Override
            public void focusLost(FocusEvent e) {
                viewMenu.labelButton2.setIcon(new ImageIcon("Resources/Images/button2MenuActive.png"));
            }
        });
    }

    public void eventsTermometro(){
        ImageIcon[] images = new ImageIcon[41];
        int j = 0;
        for (int i = -30; i <= 50; i=i+2) {
            images[j] = new ImageIcon("Resources/Images/termometro/termometro_" + i + ".png");
            images[j].setDescription(String.valueOf(i));
            j++;
        }
        //for every 2 seconds
        Timer timer = new Timer(2000, e -> {
            int temp = Math.round(Float.parseFloat((viewMenu.jTTemperatura.getText())));
            if(!(temp % 2 == 0)){
                temp = temp + 1;
            }
            System.out.println(temp);
            for (ImageIcon image : images) {
                if (image.getDescription().equals(String.valueOf(temp))) {
                    viewMenu.labelTermometro.setIcon(image);
                }
            }
        });
        timer.start();
    }
}
