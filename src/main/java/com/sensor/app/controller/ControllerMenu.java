package com.sensor.app.controller;

import com.sensor.app.models.CRUDS.CRUDHumedad;
import com.sensor.app.models.CRUDS.CRUDTemperatura;
import com.sensor.app.models.CRUDS.ReadArduino;
import com.sensor.app.models.ModelHumidity;
import com.sensor.app.views.ViewMenu;
import com.sensor.graphics.controllers.ControllerGraphics;
import com.sensor.app.models.ModelTemperature;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase que se encarga de controlar la vista del menu
 *
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 * @version 1.0
 * @see ViewMenu
 */
public final class ControllerMenu {

    private final ViewMenu viewMenu;
    private ControllerRegistros controllerRegistros;
    private ControllerGraphics viewTemperature;
    private ControllerGraphics viewHumidity;

    /**
     * Constructor de la clase.Esta instancia la vista del menu
     *
     * @see ViewMenu
     */
    public ControllerMenu() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        viewMenu = new ViewMenu();//instancia la vistaMenu
        initEvents();
    }

    /**
     * Metodo que se encarga de actualizar el termometro que esta en la vista dependiendo del ultimo valor en la base de datos
     *
     * @see CRUDTemperatura
     * @see CRUDHumedad
     */
    public void actualizaTermometro() {
        ImageIcon[] images = new ImageIcon[41];
        CRUDTemperatura crudT = CRUDTemperatura.getInstance();
        CRUDHumedad crudH = CRUDHumedad.getInstance();
        int j = 0;

        for (int i = -30; i <= 50; i = i + 2) {
            images[j] = new ImageIcon("src/main/resources/Images/termometro/termometro_" + i + ".png");
            images[j].setDescription(String.valueOf(i));
            j++;
        }

        //esto pasa cada 2 segundos
        Timer timer = new Timer(1000, e -> {
            ModelTemperature t;
            float temperatura;
            ModelHumidity h;
            float humedad;

            try {
                t = (ModelTemperature) crudT.selectLast();
                temperatura = t.getCelsius();
                h = (ModelHumidity) crudH.selectLast();
                humedad = h.getPercentage().get();
            } catch (NullPointerException ex) {
                temperatura = 0;
                humedad = 0;
            }

            viewMenu.jTTemperatura.setText(String.valueOf(Math.round(temperatura)));
            viewMenu.jTHumedad.setText("Humedad: " + humedad + "%");

            if (!(temperatura % 2 == 0)) {
                temperatura = temperatura + 1;
            }
            for (ImageIcon image : images) {//esto pone el valor de la imagen correspondiente a la temperatura
                if (image.getDescription().equals(String.valueOf(temperatura))) {
                    viewMenu.labelTermometro.setIcon(image);
                }
            }
        });
        timer.start();
    }


    /**
     * Metodo que se encarga de inicializar los eventos de la vista
     *
     * @see ViewMenu
     * @see ControllerMenu#actualizaTermometro()
     * @see ControllerMenu#eventsLabelButtons()
     * @see ControllerRegistros
     * @see ControllerGraphics
     */
    public void initEvents() {
        actualizaTermometro();//tiene la logica para pedir datos cada 1.5 segundos y actualizar la imagen correspondiente
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

    /**
     * Metodo que se encarga de inicializar los eventos de los labels y botones de la vista
     *
     * @see ViewMenu
     * @see ControllerRegistros
     * @see ControllerGraphics
     */
    public void eventsLabelButtons() {
        viewMenu.labelButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewMenu.labelButton1.setIcon(new ImageIcon("src/main/resources/Images/buttonMenuHover.png"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                viewMenu.labelButton1.setIcon(new ImageIcon("src/main/resources/Images/buttonMenuPush.png"));
                if(ReadArduino.getInstance().selectAll().get(0)==null){
                    JOptionPane.showMessageDialog(viewMenu, "No esta conectado el arduino");
                }else{
                    if (viewTemperature == null && viewHumidity == null) {
                        viewTemperature = new ControllerGraphics();
                        viewHumidity = new ControllerGraphics();

                        viewTemperature.throwJFrame();
                        viewTemperature.throwTemperature();
                        viewHumidity.throwJFrame();
                        viewHumidity.throwHumidity();
                    } else if (!(viewTemperature.jFrame.isVisible() && viewHumidity.jFrame.isVisible())) {
                        viewTemperature.throwJFrame();
                        viewHumidity.throwJFrame();
                    }
                }
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
                viewMenu.labelButton2.setIcon(new ImageIcon("src/main/resources/Images/button2MenuPush.png"));
                controllerRegistros = ControllerRegistros.getInstance();
                controllerRegistros.showView();
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
