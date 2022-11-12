/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensor.graphics.controllers;

import com.sensor.app.models.CRUDS.CRUDHumedad;
import com.sensor.graphics.models.abstracs.CatalogGraphics;
import com.sensor.graphics.models.Humidity;
import com.sensor.graphics.views.JFrameGraphics;
import com.sensor.graphics.views.JPanelHumidity;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *
 * @author KevinCyndaquil
 */
public class ControllerHumidity extends ControllerGraphics implements CatalogGraphics {

    private JPanelHumidity jPanel;
    private final ArrayList<Humidity> function;
    private static Timer timer;

    private final int SECONDS = 2000;
    private int MIN;
    private int ACT_MIN;
    private int ACT_SEC;
    private int ACT_i;

    private final int CARTESIAN_X = 30;
    private int CARTESIAN_Y;
    private int CARTESIAN_WIDTH;

    private float SECOND_X;
    private float RANGE_X;
    private float HOUR_X;
    private float PERCENTAGE_Y;

    public ControllerHumidity(JFrameGraphics jFrame) {
        this.jFrame = jFrame;
        this.function = new ArrayList<>();
        timer = new Timer(SECONDS, this);
    }

    @Override
    public Integer throwJPanel(int witdh, int height) {
        if (jPanel == null) {
            jPanel = new JPanelHumidity(this, witdh, height);
            System.out.println(jPanel);
            jFrame.jCanvas.add(jPanel);

            CARTESIAN_Y = (int) (((float) jPanel.getHeight() / (float) Humidity.RANGE) * ((Humidity.MIN < 0) ? Humidity.MAX : Humidity.RANGE));
            CARTESIAN_WIDTH = this.jPanel.getWidth() - CARTESIAN_X;

            SECOND_X = (float) CARTESIAN_WIDTH / 60.0f;
            RANGE_X = (float) CARTESIAN_WIDTH / 360.0f;
            HOUR_X = (float) CARTESIAN_WIDTH / 24.0f;

            PERCENTAGE_Y = (float) jPanel.getHeight() / (float) Humidity.RANGE;

            ACT_i = 0;
            MIN = LocalDateTime.now().getMinute();

            start();
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Integer throwJPanel(int witdh, int height, ArrayList<Object> function) {
        if (jPanel == null) {
            jPanel = new JPanelHumidity(this, witdh, height, function);
            jFrame.jCanvas.add(jPanel);

            CARTESIAN_Y = (int) (((float) jPanel.getHeight() / (float) Humidity.RANGE) * ((Humidity.MIN < 0) ? Humidity.MAX : Humidity.RANGE));
            CARTESIAN_WIDTH = this.jPanel.getWidth() - CARTESIAN_X;

            SECOND_X = (float) CARTESIAN_WIDTH / 60.0f;
            RANGE_X = (float) CARTESIAN_WIDTH / 360.0f;
            HOUR_X = (float) CARTESIAN_WIDTH / 24.0f;

            PERCENTAGE_Y = (float) jPanel.getHeight() / (float) Humidity.RANGE;

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
            Humidity h = (Humidity) object;

            h.x = (int) (SECOND_X * h.getDate().getSecond() + CARTESIAN_X);
            h.y = jPanel.getHeight() - (int) (PERCENTAGE_Y * (h.getPercentage().get() - Humidity.MIN));

            return 1;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Integer setRangeCoordinates(Object object) {
        try {
            Humidity h = (Humidity) object;

            if ((h.getDate().getHour() / 6) < 1) {
                h.x = (int) (RANGE_X * (h.getDate().getHour() * 60) + h.getDate().getMinute() + CARTESIAN_X);
            } else if ((h.getDate().getHour() / 6) < 2) {
                h.x = (int) (RANGE_X * ((h.getDate().getHour() - 6) * 60) + h.getDate().getMinute() + CARTESIAN_X);
            } else if ((h.getDate().getHour() / 6) < 3) {
                h.x = (int) (RANGE_X * ((h.getDate().getHour() - 12) * 60) + h.getDate().getMinute() + CARTESIAN_X);
            } else if ((h.getDate().getHour() / 6) < 4) {
                h.x = (int) (RANGE_X * ((h.getDate().getHour() - 18) * 60) + h.getDate().getMinute() + CARTESIAN_X);
            }
            h.y = jPanel.getHeight() - (int) (PERCENTAGE_Y * (h.getPercentage().get() - Humidity.MIN));

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

            for (Object hx : function) {
                ((Humidity) hx).x = (int) (HOUR_X * ((Humidity) hx).getDate().getHour() + CARTESIAN_X);
                ((Humidity) hx).y = jPanel.getHeight() - (int) (PERCENTAGE_Y * (((Humidity) hx).getPercentage().get() - Humidity.MIN));
                prom += (float) (((Humidity) (hx)).y);
                count++;
            }
            for (Object hx : function) {
                ((Humidity) hx).y = (int) (prom / count);
            }
            return 1;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Integer paintLine(Graphics g, Object object1, Object object2) {
        try {
            Humidity h1 = (Humidity) object1;
            Humidity h2 = (Humidity) object2;

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
            Humidity h = (Humidity) object;

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
            Humidity h = (Humidity) CRUDHumedad.getInstance().selectLast();
            function.add(h);

            h.x = (int) (SECOND_X * ACT_SEC + CARTESIAN_X);
            h.y = jPanel.getHeight() - (int) (PERCENTAGE_Y * (h.getPercentage().get() - Humidity.MIN));

            //SI HEMOS LLEGADO AL BORDE / SI YA HA PASADO EL MINUTO MAXIMO
            if (ACT_MIN > MIN || (ACT_MIN == 0 && MIN == 59)) {
                MIN = ACT_MIN;
                ACT_i = (function.size() - 1);
            }

            for (int i = ACT_i; i < function.size(); i++) {
                paintHelpLine(g, function.get(i + 1));
                paintLine(g, function.get(i), function.get(i + 1));
            }
            return 1;
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("");
            return null;
        }
    }

    @Override
    public Integer paintGraphic(Graphics g, ArrayList<Object> function) {
        try {
            LocalDateTime h0 = ((Humidity) function.get(0)).getDate();
            LocalDateTime hn = ((Humidity) function.get(function.size() - 1)).getDate();

            if (h0.getDayOfMonth() == hn.getDayOfMonth()) {
                if ((hn.getHour() - h0.getHour()) >= 0 && (hn.getHour() - h0.getHour()) < 6) {
                    if (h0.getHour() == hn.getHour() && h0.getMinute() == hn.getMinute()) {
                        function.stream().forEach((Object hx) -> setSecondCoordinates(hx));
                        System.out.println("MINUTO");
                    } else {
                        function.stream().forEach((Object hx) -> setRangeCoordinates(hx));
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
            System.out.println("");
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
        jPanel.repaint();
    }
}
