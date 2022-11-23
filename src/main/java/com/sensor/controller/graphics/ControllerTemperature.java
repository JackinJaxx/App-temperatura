/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensor.controller.graphics;

import com.sensor.controller.app.ControllerRegistros;
import static com.sensor.controller.app.ControllerRegistros.horaFin;
import static com.sensor.controller.app.ControllerRegistros.horaInicio;
import static com.sensor.controller.graphics.ControllerGraphics.jFrame;
import com.sensor.model.app.CRUDS.CRUDTemperatura;
import com.sensor.model.app.ModelTemperature;
import com.sensor.view.graphics.JPanelTemperature;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.Timer;
import com.sensor.model.graphics.abstracs.CatalogGraphics;
import com.sensor.view.graphics.JFrameGraphics;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author KevinCyndaquil
 */
public class ControllerTemperature extends ControllerGraphics implements CatalogGraphics {

    private JPanelTemperature jPanel;
    public static ArrayList<Object> function = null;
    private static Timer timer;

    private final int SECONDS = 2000;
    private int MIN;
    private int ACT_MIN;
    private int ACT_SEC;
    private int ACT_i;

    private int CARTESIAN_X;
    private int CARTESIAN_Y;
    private int CARTESIAN_WIDTH;

    private float SECOND_X;
    private float RANGE_X;
    private float HOUR_X;
    private float CELSIUS_Y;

    public ControllerTemperature() {
        super();
        this.timer = new Timer(SECONDS, this);
    }

    public ControllerTemperature(JFrameGraphics jFrame) {
        super();
        this.timer = new Timer(SECONDS, this);
    }

    public void initVariables() {
        CARTESIAN_X = 30;
        CARTESIAN_Y = (int) (((float) jPanel.getHeight() / (float) ModelTemperature.RANGE) * ((ModelTemperature.MIN < 0) ? ModelTemperature.MAX : ModelTemperature.RANGE));
        CARTESIAN_WIDTH = this.jPanel.getWidth() - this.CARTESIAN_X;

        SECOND_X = (float) CARTESIAN_WIDTH / 60.0f;
        RANGE_X = (float) CARTESIAN_WIDTH / 24.0f;
        HOUR_X = (float) CARTESIAN_WIDTH / 24.0f;
        CELSIUS_Y = (float) jPanel.getHeight() / (float) ModelTemperature.RANGE;

        ACT_i = 0;
        MIN = LocalDateTime.now().getMinute();
    }

    @Override
    public Integer throwJPanel() {
        if (jPanel == null) {
            jPanel = new JPanelTemperature(this, jFrame.jCanvas.getWidth(), jFrame.jCanvas.getHeight());
            jFrame.setTitle("GRAPHIC TEMPERATURE");
            removeJPanel();
            jFrame.jCanvas.add(jPanel); //Añadimos el panel al jFrame
            
            function = new ArrayList <Object>();
            
            initVariables();
            return 1;
        }
        return 0;
    }

    @Override
    public Integer throwJPanel(ArrayList<Object> function) {
        if (function != null) {
            if (jPanel == null) {
                jPanel = new JPanelTemperature(this, jFrame.jCanvas.getWidth(), jFrame.jCanvas.getHeight());
                initVariables();
            }
            jFrame.setTitle("GRAPHIC TEMPERATURE");
            removeJPanel();
            jFrame.jCanvas.add(jPanel);
            this.function = function;

            if (horaInicio == null && horaFin == null) {
                setHoursCoordinates();
            } else {
                setRangeCoordinates();
            }

            jFrame.jCanvas.repaint();
            return 1;
        }
        return 0;
    }

    @Override
    public Integer setSecondCoordinates() {
        try {
            ModelTemperature temp = (ModelTemperature) CRUDTemperatura.getInstance().selectLast();

            temp.x = (int) (SECOND_X * ACT_SEC + CARTESIAN_X);
            temp.y = jPanel.getHeight() - (int) (CELSIUS_Y * (temp.getACelsius().get() - ModelTemperature.MIN));
            function.add(temp);

            //SI HEMOS LLEGADO AL BORDE / SI YA HA PASADO EL MINUTO MAXIMO
            if (ACT_MIN > MIN || (ACT_MIN == 0 && MIN == 59)) {
                MIN = ACT_MIN;
                ACT_i = (function.size() - 1);
            }
            return 1;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Integer setRangeCoordinates() {
        try {
            int min = 15;
            LocalTime horaFin = ControllerRegistros.horaFin;
            LocalDate fechaCalendar = ControllerRegistros.fechaCalendar;

            for (Object obj : function) {
                ModelTemperature t = (ModelTemperature) obj;

                while (true) {
                    LocalTime h1 = ControllerRegistros.horaInicio.plusMinutes(min - 15);
                    LocalTime h2 = ControllerRegistros.horaInicio.plusMinutes(min);

                    if (t.getDate().toLocalTime().isAfter(h1)) {
                        if (t.getDate().toLocalTime().isBefore(h2) || t.getDate().toLocalTime().equals(h2)) {
                            int prom = CRUDTemperatura.getInstance().getAverage(fechaCalendar, h1, h2);

                            t.x = Math.round(RANGE_X * (min / 15) + CARTESIAN_X);
                            t.y = jPanel.getHeight() - Math.round(CELSIUS_Y * (prom - ModelTemperature.MIN));
                            System.out.println(prom);

                            break;
                        } else {
                            if (h2.isBefore(horaFin) || h2.equals(horaFin)) {
                                min += 15;
                            } else {
                                t.x = null;
                                t.y = null;

                                break;
                            }
                        }
                    } else {
                        t.x = null;
                        t.y = null;

                        break;
                    }
                }
            }
            return 1;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Integer setHoursCoordinates() {
        try {
            LocalDate fechaCalendar = ControllerRegistros.fechaCalendar;
            int hours = ((ModelTemperature) function.get(0)).getDate().getHour();

            for (Object obj : function) {
                ModelTemperature t = (ModelTemperature) obj;

                while (true) {
                    if (t.getDate().toLocalDate().isEqual(fechaCalendar)) {
                        if (t.getDate().getHour() == hours) {
                            LocalTime hour = LocalTime.of(t.getDate().getHour(), 0);
                            int prom = CRUDTemperatura.getInstance().getAverage(fechaCalendar, hour, hour.plusHours(1));

                            t.x = Math.round(HOUR_X * hours) + CARTESIAN_X;
                            t.y = jPanel.getHeight() - Math.round(CELSIUS_Y * (prom - ModelTemperature.MIN));
                            System.out.println(prom);
                            break;
                        } else {
                            hours++;
                        }
                    } else {
                        t.x = null;
                        t.y = null;

                        break;
                    }
                }
            }

            return 1;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Integer paintLine(Graphics g, Object object1, Object object2) {
        try {
            ModelTemperature t1 = (ModelTemperature) object1;
            ModelTemperature t2 = (ModelTemperature) object2;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.RED);
            g2d.drawLine(t1.x, t1.y, t2.x, t2.y);

            System.out.println("---------GRAPHICS---------");
            System.out.println("CELSIUS -> " + t2.getACelsius().get());
            System.out.println("P = (" + t2.x + ", " + t2.y + ")");

            return 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public Integer paintHelpLine(Graphics g, Object object) {
        try {
            ModelTemperature t = (ModelTemperature) object;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(Color.BLUE);
            g2d.drawLine(t.x, t.y, t.x, CARTESIAN_Y);
            g2d.setColor(Color.RED);
            g2d.fillOval(t.x - 4, t.y - 4, 8, 8);

            g2d.setColor(Color.BLACK);

            if (t.x == (int) (SECOND_X * ACT_SEC + CARTESIAN_X)) {
                g2d.drawString("(" + t.x + "," + t.getACelsius().get() + "°)", t.x + 5, t.y + 5);
            }

            return 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public Integer paintGraphic(Graphics g) {
        try {
            for (int i = ACT_i; i < function.size(); i++) {
                paintHelpLine(g, function.get(i + 1));
                paintLine(g, function.get(i), function.get(i + 1));
            }
            return 1;
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("");
            return 0;
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
        ACT_SEC = LocalDateTime.now().getSecond();

        setSecondCoordinates();
        jPanel.repaint();
    }
}
