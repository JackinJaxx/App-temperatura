/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensor.controller.graphics;

import com.sensor.controller.app.ControllerRegistros;
import static com.sensor.controller.app.ControllerRegistros.horaFin;
import static com.sensor.controller.app.ControllerRegistros.horaInicio;
import static com.sensor.controller.graphics.ControllerGraphics.jFrame;
import static com.sensor.controller.graphics.ControllerTemperature.function;
import com.sensor.model.app.CRUDS.CRUDHumedad;
import com.sensor.model.app.CRUDS.CRUDTemperatura;
import com.sensor.model.app.ModelHumidity;
import com.sensor.model.graphics.abstracs.CatalogGraphics;
import com.sensor.view.graphics.JFrameGraphics;
import com.sensor.view.graphics.JPanelHumidity;
import com.sensor.view.graphics.JPanelTemperature;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *
 * @author KevinCyndaquil
 */
public class ControllerHumidity extends ControllerGraphics implements CatalogGraphics {

    private JPanelHumidity jPanel;
    public static ArrayList<Object> function;
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
    private float PERCENTAGE_Y;

    public ControllerHumidity() {
        super();
        timer = new Timer(SECONDS, this);
    }

    public ControllerHumidity(JFrameGraphics jFrame) {
        super();
        timer = new Timer(SECONDS, this);
    }

    public void initVariables() {
        CARTESIAN_X = 30;
        CARTESIAN_Y = (int) (((float) jPanel.getHeight() / (float) ModelHumidity.RANGE) * ((ModelHumidity.MIN < 0) ? ModelHumidity.MAX : ModelHumidity.RANGE));
        CARTESIAN_WIDTH = this.jPanel.getWidth() - CARTESIAN_X;

        SECOND_X = (float) CARTESIAN_WIDTH / 60.0f;
        RANGE_X = (float) CARTESIAN_WIDTH / 24.0f;
        HOUR_X = (float) CARTESIAN_WIDTH / 24.0f;
        PERCENTAGE_Y = (float) jPanel.getHeight() / (float) ModelHumidity.RANGE;

        ACT_i = 0;
        MIN = LocalDateTime.now().getMinute();
    }

    @Override
    public Integer throwJPanel() {
        if (jPanel == null) {
            jPanel = new JPanelHumidity(this, jFrame.jCanvas.getWidth(), jFrame.jCanvas.getHeight());
            jFrame.setTitle("GRAPHIC HUMIDITY");
            removeJPanel();
            jFrame.jCanvas.add(jPanel); //Añadimos el panel al jFrame
            
            function = new ArrayList <Object>();
            
            initVariables();
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Integer throwJPanel(ArrayList<Object> function) {
        if (function != null) {
            if (jPanel == null) {
                jPanel = new JPanelHumidity(this, jFrame.jCanvas.getWidth(), jFrame.jCanvas.getHeight());
                initVariables();
            }
            jFrame.setTitle("GRAPHIC HUMIDITY");
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
            ModelHumidity temp = (ModelHumidity) CRUDHumedad.getInstance().selectLast();

            temp.x = (int) (SECOND_X * ACT_SEC + CARTESIAN_X);
            temp.y = jPanel.getHeight() - (int) (PERCENTAGE_Y * (temp.getPercentage().get() - ModelHumidity.MIN));
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
                ModelHumidity h = (ModelHumidity) obj;

                while (true) {
                    LocalTime h1 = ControllerRegistros.horaInicio.plusMinutes(min - 15);
                    LocalTime h2 = ControllerRegistros.horaInicio.plusMinutes(min);

                    if (h.getDate().toLocalTime().isAfter(h1)) {
                        if (h.getDate().toLocalTime().isBefore(h2)) {
                            int prom = CRUDHumedad.getInstance().getAverage(fechaCalendar, h1, h2);

                            h.x = Math.round(RANGE_X * (min / 15) + CARTESIAN_X);
                            h.y = jPanel.getHeight() - Math.round(PERCENTAGE_Y * (prom - ModelHumidity.MIN));
                            System.out.println(prom);

                            break;
                        } else {
                            if (h2.isBefore(horaFin)) {
                                min += 15;
                            } else {
                                h.x = null;
                                h.y = null;

                                break;
                            }
                        }
                    } else {
                        h.x = null;
                        h.y = null;

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
            int hours = ((ModelHumidity) function.get(0)).getDate().getHour();

            for (Object obj : function) {
                ModelHumidity h = (ModelHumidity) obj;

                while (true) {
                    if (h.getDate().toLocalDate().isEqual(fechaCalendar)) {
                        if (h.getDate().getHour() == hours) {
                            LocalTime hour = LocalTime.of(h.getDate().getHour(), 0);
                            int prom = CRUDHumedad.getInstance().getAverage(fechaCalendar, hour, hour.plusHours(1));

                            h.x = Math.round(HOUR_X * hours) + CARTESIAN_X;
                            h.y = Math.round(PERCENTAGE_Y * prom - ModelHumidity.MIN);

                            break;
                        } else {
                            hours++;
                        }
                    } else {
                        h.x = null;
                        h.y = null;

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
            ModelHumidity h1 = (ModelHumidity) object1;
            ModelHumidity h2 = (ModelHumidity) object2;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.BLUE);
            g2d.drawLine(h1.x, h1.y, h2.x, h2.y);

            System.out.println("H1 = (" + h1.x + ", " + h1.y + ");");
            System.out.println("H2 = (" + h2.x + ", " + h2.y + ");");
            System.out.println("PERCENTAGE = " + h2.getPercentage().get());
            System.out.println(this.jPanel.getWidth());
            System.out.println(CARTESIAN_WIDTH);
            return 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public Integer paintHelpLine(Graphics g, Object object) {
        try {
            ModelHumidity h = (ModelHumidity) object;

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(Color.RED);
            g2d.drawLine(h.x, h.y, h.x, CARTESIAN_Y);
            g2d.setColor(Color.BLUE);
            g2d.fillOval(h.x - 4, h.y - 4, 8, 8);
            g2d.setColor(Color.BLACK);

            if (h.x == (int) (SECOND_X * ACT_SEC + CARTESIAN_X)) {
                g2d.drawString("(" + h.x + "," + h.getPercentage().get() + "°)", h.x + 5, h.y + 5);
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
        for (int y = 0; y <= jPanel.getHeight(); y += (5 * PERCENTAGE_Y)) {
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
