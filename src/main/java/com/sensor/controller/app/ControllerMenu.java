package com.sensor.controller.app;

import static com.sensor.controller.app.ControllerRegistros.controllerGraphic;
import com.sensor.model.app.CRUDS.CRUDHumedad;
import com.sensor.model.app.CRUDS.CRUDTemperatura;
import com.sensor.model.app.CRUDS.ReadArduino;
import com.sensor.model.app.ModelHumidity;
import com.sensor.view.app.ViewMenu;
import com.sensor.controller.graphics.ControllerGraphics;
import com.sensor.model.app.ModelTemperature;
import com.sensor.controller.graphics.ControllerHumidity;
import com.sensor.controller.graphics.ControllerTemperature;

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
    private ControllerTemperature viewTemperature;
    private ControllerHumidity viewHumidity;
    
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
     * Metodo que se encarga de actualizar el termometro que esta en la vista
     * dependiendo del ultimo valor en la base de datos
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
     * Metodo que se encarga de inicializar los eventos de los labels y botones
     * de la vista
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

                try {
                    if (controllerGraphic == null || controllerGraphic == 1) {

                        if (viewTemperature == null) {
                            viewTemperature = new ControllerTemperature();
                        }
                        
                        viewTemperature.throwJFrame();
                        viewTemperature.throwJPanel();
                        viewTemperature.start();
                    }
                    if (controllerGraphic == 0) {
                        if (viewHumidity == null) {
                            viewHumidity = new ControllerHumidity();
                        }
                        
                        viewHumidity.throwJFrame();
                        viewHumidity.throwJPanel();
                        viewHumidity.start();
                    }
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(viewMenu, "NO HAY DATOS QUE LEER SIN EL ARDUINO");
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
