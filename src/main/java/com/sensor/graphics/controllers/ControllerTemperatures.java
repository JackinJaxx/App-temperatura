/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensor.graphics.controllers;

import com.sensor.graphics.models.Temperature;
import com.sensor.graphics.views.JPanelTemperature;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.Timer;
import com.sensor.graphics.models.abstracs.CatalogGraphics;
import com.sensor.graphics.views.JFrameGraphics;

/**
 *
 * @author KevinCyndaquil
 */
public class ControllerTemperatures extends ControllerGraphics implements CatalogGraphics {

    private JPanelTemperature jPanel;
    private final ArrayList<Temperature> function;
    private static Timer timer;

    private final int SECONDS = 2000;
    private int MIN;
    private int ACT_MIN;
    private int ACT_i;

    private int CARTESIAN_X;
    private int CARTESIAN_Y;
    private int CARTESIAN_WIDTH;

    private float SECOND_X;
    private float RANGE_X;
    private float HOUR_X;
    private float CELSIUS_Y;

    public ControllerTemperatures(JFrameGraphics jFrame) {
        this.jFrame = jFrame;
        this.function = new ArrayList<>();
        this.timer = new Timer(SECONDS, this);

    }

    @Override
    public Integer throwJPanel(int witdh, int height) {
        if (jPanel == null) {
            
            jPanel = new JPanelTemperature(this, witdh, height);
            jFrame.jCanvas.add(jPanel);
            
            CARTESIAN_X = 30;
            CARTESIAN_Y = (int) (((float) jPanel.getHeight() / (float) Temperature.RANGE) * ((Temperature.MIN < 0) ? Temperature.MAX : Temperature.RANGE));
            CARTESIAN_WIDTH = this.jPanel.getWidth() - this.CARTESIAN_X;

            SECOND_X = (float) CARTESIAN_WIDTH / 60.0f;
            RANGE_X = (float) CARTESIAN_WIDTH / 360.0f;
            HOUR_X = (float) CARTESIAN_WIDTH / 24.0f;

            CELSIUS_Y = (float) jPanel.getHeight() / (float) Temperature.RANGE;

            ACT_i = 0;
            MIN = LocalDateTime.now().getMinute();

            start();
            return 1;
        } else {
            return 0;
        }
    }
    
    @Override
    public Integer throwJPanel(int witdh, int height, ArrayList <Object> function) {
        if (jPanel == null) {
            jPanel = new JPanelTemperature(this, witdh, height, function);
            jFrame.jCanvas.add(jPanel);

            CARTESIAN_X = 30;
            CARTESIAN_Y = (int) (((float) jPanel.getHeight() / (float) Temperature.RANGE) * ((Temperature.MIN < 0) ? Temperature.MAX : Temperature.RANGE));
            CARTESIAN_WIDTH = this.jPanel.getWidth() - this.CARTESIAN_X;

            SECOND_X = (float) CARTESIAN_WIDTH / 60.0f;
            RANGE_X = (float) CARTESIAN_WIDTH / 360.0f;
            HOUR_X = (float) CARTESIAN_WIDTH / 24.0f;

            CELSIUS_Y = (float) jPanel.getHeight() / (float) Temperature.RANGE;

            ACT_i = 0;
            MIN = LocalDateTime.now().getMinute();

            start();
            return 1;
        } else {
            return 0;
        }
    }
    
    

    @Override
    public Integer setSecondCoordinates(Object object) {
        try {
            Temperature t = (Temperature) object;

            t.x = (int) (SECOND_X * t.getDate().getSecond() + CARTESIAN_X);
            t.y = jPanel.getHeight() - (int) (CELSIUS_Y * (t.getCelsius().get() - Temperature.MIN));

            return 1;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Integer setRangeCoordinates(Object object) {
        try {
            Temperature t = (Temperature) object;

            if ((t.getDate().getHour() / 6) < 1) {
                t.x = (int) (RANGE_X * (t.getDate().getHour() * 60) + t.getDate().getMinute() + CARTESIAN_X);
            } else if ((t.getDate().getHour() / 6) < 2) {
                t.x = (int) (RANGE_X * ((t.getDate().getHour() - 6) * 60) + t.getDate().getMinute() + CARTESIAN_X);
            } else if ((t.getDate().getHour() / 6) < 3) {
                t.x = (int) (RANGE_X * ((t.getDate().getHour() - 12) * 60) + t.getDate().getMinute() + CARTESIAN_X);
            } else if ((t.getDate().getHour() / 6) < 4) {
                t.x = (int) (RANGE_X * ((t.getDate().getHour() - 18) * 60) + t.getDate().getMinute() + CARTESIAN_X);
            }
            t.y = jPanel.getHeight() - (int) (CELSIUS_Y * (t.getCelsius().get() - Temperature.MIN));

            return 1;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Integer setHoursCoordinates(ArrayList<Object> function) {
        try {
            float prom = 0;
            int count = 0;

            for (Object tx : function) {
                ((Temperature) tx).x = (int) (HOUR_X * ((Temperature) tx).getDate().getHour() + CARTESIAN_X);
                ((Temperature) tx).y = jPanel.getHeight() - (int) (CELSIUS_Y * (((Temperature) tx).getCelsius().get() - Temperature.MIN));
                prom += (float) (((Temperature) tx).y);
                count++;
            }
            for (Object tx : function) {
                ((Temperature) tx).y = (int) (prom / count);
            }
            return 1;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Integer paintLine(Graphics g, Object object1, Object object2) {
        try {
            Temperature t1 = (Temperature) object1;
            Temperature t2 = (Temperature) object2;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.RED);
            g2d.drawLine(t1.x, t1.y, t2.x, t2.y);

            return 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public Integer paintHelpLine(Graphics g, Object object) {
        try {
            Temperature t = (Temperature) object;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(Color.BLUE);
            g2d.drawLine(t.x, t.y, t.x, CARTESIAN_Y);
            //g2d.drawLine(t.x, t.y, jFrame.CARTESIAN_X, t.y);
            g2d.setColor(Color.RED);
            g2d.fillOval(t.x - 4, t.y - 4, 8, 8);
            
            g2d.setColor(Color.BLACK);
            g2d.drawString("(" + t.x + "," + t.getCelsius().get() + "°)", t.x + 5, t.y + 5);

            return 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public Integer paintGraphic(Graphics g) {
        try {
            Temperature temp = new Temperature(0, (int) (Math.random() * Temperature.RANGE + Temperature.MIN), LocalDateTime.now());
            function.add(temp);

            //SI HEMOS LLEGADO AL BORDE / SI YA HA PASADO EL MINUTO MAXIMO
            if (ACT_MIN > MIN || (ACT_MIN == 0 && MIN == 59)) {
                MIN = ACT_MIN;
                ACT_i = (function.size() - 1);
            }
            function.stream().forEach(t -> setSecondCoordinates(t));

            for (int i = ACT_i; i < function.size(); i++) {
                paintHelpLine(g, function.get(i + 1));
                paintLine(g, function.get(i), function.get(i + 1));

                System.out.println("CELSIUS: " + function.get(i + 1).getCelsius().get());
                System.out.println("X1: " + function.get(i).x + " | Y1: " + function.get(i).y);
                System.out.println("X2: " + function.get(i + 1).x + " | Y2: " + function.get(i + 1).y);
            }
            return 1;
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("");
            return 0;
        }
    }

    @Override
    public Integer paintGraphic(Graphics g, ArrayList<Object> function) {
        try {
            LocalDateTime t0 = ((Temperature) function.get(0)).getDate();
            LocalDateTime tn = ((Temperature) function.get(function.size() - 1)).getDate();

            if (t0.getDayOfMonth() == tn.getDayOfMonth()) {
                if ((tn.getHour() - t0.getHour()) >= 0 && (tn.getHour() - t0.getHour()) < 6) {
                    if (t0.getHour() == tn.getHour() && t0.getMinute() == tn.getMinute()) {
                        function.stream().forEach((Object tx) -> setSecondCoordinates(tx));
                        System.out.println("MINUTO");
                    } else {
                        function.stream().forEach((Object tx) -> setRangeCoordinates(tx));
                        System.out.println("RANGO");
                    }
                } else {
                    setHoursCoordinates(function);
                    System.out.println("DÍAS");
                }
            } else {
                System.out.println("**No puede graficarse la function fuera de tiempo**");
                return 0;
            }

            for (int i = 0; i < function.size(); i++) {
                paintHelpLine(g, function.get(i + 1));
                paintLine(g, function.get(i), function.get(i + 1));
            }

            return 1;
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            return null;
        }
    }

    @Override
    public Integer paintCartesian(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(new Color(0, 255, 0, 75));
        for (int x = 0; x <= jPanel.getWidth(); x += SECOND_X) {
            g2d.drawLine(x, 0, x, jPanel.getHeight());
        }
        for (int y = 0; y <= jPanel.getHeight(); y += (5 * CELSIUS_Y)) {
            g2d.drawLine(0, y, jPanel.getWidth(), y);
        }

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, CARTESIAN_Y, jPanel.getWidth(), CARTESIAN_Y);
        g2d.drawLine(CARTESIAN_X, 0, CARTESIAN_X, jPanel.getHeight());
        return 1;
    }

    @Override
    public Integer start() {
        timer.start();
        return 1;
    }

    @Override
    public Integer stop() {
        timer.stop();
        return 1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ACT_MIN = LocalDateTime.now().getMinute();

        jPanel.repaint();
    }
}
