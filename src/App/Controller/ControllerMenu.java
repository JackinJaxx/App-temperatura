package App.Controller;

import App.Model.CRUDDB;
import App.Model.ModeloSensor;
import App.Model.ReadArduino;
import App.View.ViewMenu;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class ControllerMenu {
    private ViewMenu viewMenu;
    private ReadArduino readArduino;

    public ControllerMenu() {
        viewMenu = new ViewMenu();//instancia la vistaMenu
        readArduino = new ReadArduino();
        initEvents();
    }

    public void actualizaTermometro() {
        AtomicInteger temperaturaVariacion = new AtomicInteger();//
        AtomicInteger humedadVariacion = new AtomicInteger();//

        ImageIcon[] images = new ImageIcon[41];
        int j = 0;
        for (int i = -30; i <= 50; i = i + 2) {
            images[j] = new ImageIcon("Resources/Images/termometro/termometro_" + i + ".png");
            images[j].setDescription(String.valueOf(i));
            j++;
        }

        //esto pasa cada 2 segundos
        Timer timer = new Timer(2000, e -> {
            boolean variacion;//si es true significa que la temperatura ha variado
            //0.5 0, 0.6 1
            int temperatura = Math.round((float)readArduino.select().get(0));//temperatura redondeada
            int humedad = Math.round((float) readArduino.select().get(1)); //humedad redonedada

            viewMenu.jTTemperatura.setText(String.valueOf(readArduino.select().get(0)));
            viewMenu.jTHumedad.setText("Humedad: " + readArduino.select().get(1) + "%");

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

            variacion = temperatura != temperaturaVariacion.get() || humedad != humedadVariacion.get();//
            if (variacion) {
                //si la temperaturaVariacion cambia se guarda en la base de datos
                System.out.println("\n" + CRUDDB.getInstance().insert(getSensorData(temperatura, humedad)));//manda ala bd
                temperaturaVariacion.set(temperatura);
                humedadVariacion.set(humedad);
            }
        });
        timer.start();
    }

    public ModeloSensor getSensorData(int temperatura, int humedad) {
        return new ModeloSensor(temperatura, humedad, LocalDate.now().toString(),
                LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).toString()
        );
    }

    public void initEvents() {
        actualizaTermometro();//tiene la logica para pedir datos cada 2 segundos y actualizar la imagen correspondiente
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

    public void eventsLabelButtons() {
        viewMenu.labelButton1.addMouseListener(new MouseAdapter() {
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

}
