package com.sensor.app.controllers;

<<<<<<< HEAD:src/main/java/com/sensor/app/controllers/ControllerMenu.java
import com.sensor.app.models.CRUDDB;
import com.sensor.app.models.ModeloSensor;
import com.sensor.app.models.ReadArduino;
import com.sensor.app.views.ViewMenu;
import com.sensor.graphics.controllers.ControllerGraphics;
=======
import App.Model.CRUDS.CRUDHumedad;
import App.Model.CRUDS.CRUDTemperatura;
import App.Model.ModelHumedad;
import App.Model.ModelTemperatura;
import App.View.ViewMenu;
>>>>>>> eea6d66 (backup):src/App/Controller/ControllerMenu.java

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControllerMenu {
<<<<<<< HEAD:src/main/java/com/sensor/app/controllers/ControllerMenu.java

    private final ViewMenu viewMenu;
    private final ReadArduino readArduino;
    private ControllerGraphics viewTemperature;
    private ControllerGraphics viewHumidity;
=======
    private ViewMenu viewMenu;

    private ControllerRegistros controllerRegistros;
>>>>>>> eea6d66 (backup):src/App/Controller/ControllerMenu.java

    public ControllerMenu() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        viewMenu = new ViewMenu();//instancia la vistaMenu
        initEvents();
    }

    public void actualizaTermometro() {
        ImageIcon[] images = new ImageIcon[41];
        int j = 0;
        for (int i = -30; i <= 50; i = i + 2) {
            images[j] = new ImageIcon("src/main/resources/Images/termometro/termometro_" + i + ".png");
            images[j].setDescription(String.valueOf(i));
            j++;
        }
        CRUDTemperatura crudT = CRUDTemperatura.getInstance();
        CRUDHumedad crudH = CRUDHumedad.getInstance();
        //esto pasa cada 2 segundos
        Timer timer = new Timer(2000, e -> {
<<<<<<< HEAD:src/main/java/com/sensor/app/controllers/ControllerMenu.java
            boolean variacion;//si es true significa que la temperatura ha variado
            //0.5 0, 0.6 1
            int temperatura = Math.round((float) readArduino.select().get(0));//temperatura redondeada
            int humedad = Math.round((float) readArduino.select().get(1)); //humedad redonedada
=======
            //int temperatura = Math.round((float)readArduino.select().get(0));//temperatura redondeada
            //int humedad = Math.round((float) readArduino.select().get(1)); //humedad redonedada
            String sentenciaT = "SELECT * FROM humedad ORDER BY fecha,hora DESC LIMIT 1";
            ModelTemperatura modelTemperatura = (ModelTemperatura) crudT.selectLast().get(0);
            int temperatura =  Math.round(modelTemperatura.getTemperatura());
>>>>>>> eea6d66 (backup):src/App/Controller/ControllerMenu.java


            String sentenciaH = "SELECT * FROM temperatura ORDER BY fecha,hora DESC LIMIT 1";
            ModelHumedad modelHumedad = (ModelHumedad) crudH.selectLast().get(0);

            viewMenu.jTTemperatura.setText(String.valueOf(modelTemperatura.getTemperatura()));
            viewMenu.jTHumedad.setText("Humedad: " + modelHumedad.getHumedad() + "%");
            //temperatura = temperatura >= -30 && temperatura <= 50 ? temperatura : temperatura < -30 ? -30 : 50;
            if (!(temperatura % 2 == 0)) {
                temperatura = temperatura + 1;
            }
            for (ImageIcon image : images) {//esto pone el valor de la imagen correspondiente a la temperatura
                if (image.getDescription().equals(String.valueOf(temperatura))) {
                    viewMenu.labelTermometro.setIcon(image);
                }
            }
            //**hasta aca termina el control del terometro en la vistaMenu
        });
        timer.start();
    }
    public void initEvents() {
        actualizaTermometro();//tiene la logica para pedir datos cada 2 segundos y actualizar la imagen correspondiente
        eventsLabelButtons();
        viewMenu.labelGitHub.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt) {
                try {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/jackinjaxx"));
                } catch (java.io.IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        viewMenu.labelTwitter.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
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
        viewMenu.labelButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewMenu.labelButton1.setIcon(new ImageIcon("src/main/resources/Images/buttonMenuHover.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
<<<<<<< HEAD:src/main/java/com/sensor/app/controllers/ControllerMenu.java
                viewMenu.labelButton1.setIcon(new ImageIcon("src/main/resources/Images/buttonMenuPush.png"));

                if (viewTemperature == null && viewHumidity == null) {
                    viewTemperature = new ControllerGraphics();
                    viewHumidity = new ControllerGraphics();
                    
                    viewTemperature.throwJFrame();
                    viewTemperature.throwTemperature();
                    viewHumidity.throwJFrame();
                    viewHumidity.throwHumidity();
                } else if (!(viewTemperature.jFrame.isVisible() && viewHumidity.jFrame.isVisible())){
                    viewTemperature.throwJFrame();
                    viewHumidity.throwJFrame();
                }
=======
                viewMenu.labelButton1.setIcon(new ImageIcon("Resources/Images/buttonMenuPush.png"));

>>>>>>> eea6d66 (backup):src/App/Controller/ControllerMenu.java
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewMenu.labelButton1.setIcon(new ImageIcon("src/main/resources/Images/buttonMenuActive.png"));
            }
        });
        viewMenu.labelButton1.addFocusListener(new FocusListener() {
            boolean isFocus = false;

            @Override
            public void focusGained(FocusEvent e) {
                if (isFocus) {
                    viewMenu.labelButton1.setIcon(new ImageIcon("src/main/resources/Images/buttonMenuHover.png"));

                } else {
                    isFocus = true;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                viewMenu.labelButton1.setIcon(new ImageIcon("src/main/resources/Images/buttonMenuActive.png"));
            }
        });

        viewMenu.labelButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewMenu.labelButton2.setIcon(new ImageIcon("src/main/resources/Images/button2MenuHover.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
<<<<<<< HEAD:src/main/java/com/sensor/app/controllers/ControllerMenu.java
                viewMenu.labelButton2.setIcon(new ImageIcon("src/main/resources/Images/button2MenuPush.png"));
=======
                viewMenu.labelButton2.setIcon(new ImageIcon("Resources/Images/button2MenuPush.png"));
                controllerRegistros = ControllerRegistros.getInstance();
                controllerRegistros.showView();
>>>>>>> eea6d66 (backup):src/App/Controller/ControllerMenu.java
            }

            @Override
            public void mouseExited(MouseEvent e) {
                viewMenu.labelButton2.setIcon(new ImageIcon("src/main/resources/Images/button2MenuActive.png"));
            }
        });
        viewMenu.labelButton2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                viewMenu.labelButton2.setIcon(new ImageIcon("src/main/resources/Images/button2MenuHover.png"));
            }

            @Override
            public void focusLost(FocusEvent e) {
                viewMenu.labelButton2.setIcon(new ImageIcon("src/main/resources/Images/button2MenuActive.png"));
            }
        });
    }

}
