package com.sensor.app.controller;

import com.sensor.app.models.CRUDS.CRUDHumedad;
import com.sensor.app.models.CRUDS.CRUDTemperatura;
import com.sensor.app.models.ModelHumidity;
import com.sensor.app.views.VistaRegistro;
import com.sensor.graphics.controllers.ControllerGraphics;
import com.sensor.app.models.ModelTemperature;


import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Clase que se encarga de controlar la vista Registros
 *
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 * @version 1.0
 */
public final class ControllerRegistros {
    public static ControllerRegistros instance;

    private final VistaRegistro vistaRegistro;
    private final CRUDHumedad crudH;
    private final CRUDTemperatura crudT;
    private final DefaultTableModel tablaT;
    private final DefaultTableModel tablaH;

    private ControllerGraphics viewTemperature;
    private ControllerGraphics viewHumidity;
    private LocalDate fechaC;


    /**
     * Constructor de la clase.Esta instancia la vista Registros
     */
    public ControllerRegistros() {
        vistaRegistro = new VistaRegistro();
        crudH = CRUDHumedad.getInstance();
        crudT = CRUDTemperatura.getInstance();
        tablaT = (DefaultTableModel) vistaRegistro.jTable1.getModel();
        tablaH = (DefaultTableModel) vistaRegistro.jTable3.getModel();
        events();
    }

    /**
     * Metodo que implementa el patron singleton para que solo exista una instancia de esta clase
     *
     * @return la instancia de la clase
     */
    public static ControllerRegistros getInstance() {
        if (instance == null) {
            instance = new ControllerRegistros();
        }
        return instance;
    }

    /**
     * Metodo que se encarga de mostrar la vista
     */
    public void showView() {
        new Thread(() -> {
            vistaRegistro.setVisible(true);
            while (vistaRegistro.isVisible()) {
                vistaRegistro.repaint();
                agregarDatosTabla(vistaRegistro.getjComboBox1().getSelectedItem().toString());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * Metodo que se encarga de agregar los datos de la base de datos a las tablas
     *
     * @param rangoHora All, 0:00-5:59, 6:00-11:59, 12:00-17:59, 18:00-23:59
     */
    public void agregarDatosTabla(String rangoHora) {
        LocalTime horaInicio = null;
        LocalTime horaFin = null;
        boolean bandera = rangoHora.equals("All");
        if (!bandera) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
            System.out.println(rangoHora);
            horaInicio = LocalTime.parse(rangoHora.split("-")[0],formatter);
            System.out.println(horaInicio);
            horaFin = LocalTime.parse(rangoHora.split("-")[1],formatter);
            System.out.println(horaFin);
        }
        tablaT.setRowCount(0);
        fechaC = vistaRegistro.getjDateChooser1().getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        for (Object modelo : crudT.selectAll()) {
            ModelTemperature t = (ModelTemperature) modelo;
            if (bandera) {
                if (t.getDate().toLocalDate().equals(fechaC)) {
                    tablaT.addRow(new Object[]{
                            t.getDate().getYear() + " / " + t.getDate().getDayOfMonth() + " / " + t.getDate().getDayOfMonth(),
                            t.getDate().getHour() + ":" + t.getDate().getMinute() + ":" + t.getDate().getSecond(),
                            t.getCelsius()
                    });
                }
            } else if (t.getDate().toLocalDate().equals(fechaC) && (t.getDate().toLocalTime().isAfter(horaInicio) && t.getDate().toLocalTime().isBefore(horaFin))) {
                tablaT.addRow(new Object[]{
                        t.getDate().getYear() + " / " + t.getDate().getDayOfMonth() + " / " + t.getDate().getDayOfMonth(),
                        t.getDate().getHour() + ":" + t.getDate().getMinute() + ":" + t.getDate().getSecond(),
                        t.getCelsius()
                });
            }
        }
        tablaH.setRowCount(0);
        for (Object modelo : crudH.selectAll()) {
            ModelHumidity h = (ModelHumidity) modelo;
            if (bandera) {
                if (h.getDate().toLocalDate().equals(fechaC)) {
                    tablaH.addRow(new Object[]{
                            h.getDate().getYear() + " / " + h.getDate().getDayOfMonth() + " / " + h.getDate().getDayOfMonth(),
                            h.getDate().getHour() + ":" + h.getDate().getMinute() + ":" + h.getDate().getSecond(),
                            h.getPercentage()
                    });
                }
            } else if (h.getDate().toLocalDate().equals(fechaC) && (h.getDate().toLocalTime().isAfter(horaInicio) && h.getDate().toLocalTime().isBefore(horaFin))) {
                tablaH.addRow(new Object[]{
                        h.getDate().getYear() + " / " + h.getDate().getDayOfMonth() + " / " + h.getDate().getDayOfMonth(),
                        h.getDate().getHour() + ":" + h.getDate().getMinute() + ":" + h.getDate().getSecond(),
                        h.getPercentage()
                });
            }
        }
    }

    /**
     * Metodo que se encarga de agregar los eventos a los componentes de la vista Registros
     */
    public void events() {
        vistaRegistro.getjDateChooser1().getDateEditor().addPropertyChangeListener((PropertyChangeEvent evt) -> {
            if ("date".equals(evt.getPropertyName())) {
                agregarDatosTabla(vistaRegistro.getjComboBox1().getSelectedItem().toString());
            }
        });
        vistaRegistro.jButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                fechaC = vistaRegistro.getjDateChooser1().getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                ArrayList<Object> tx = CRUDTemperatura.getInstance().selectAll();
                ArrayList<Object> funcT = new ArrayList<>();
                ArrayList<Object> hx = CRUDHumedad.getInstance().selectAll();
                ArrayList<Object> funcH = new ArrayList<>();

                tx.forEach(o -> {
                    ModelTemperature t = (ModelTemperature) o;
                    if (t.getDate().getDayOfMonth() == fechaC.getDayOfMonth()) {
                        funcT.add(t);
                    }
                });
                hx.forEach(o -> {
                    ModelHumidity h = (ModelHumidity) o;
                    if (h.getDate().getDayOfMonth() == fechaC.getDayOfMonth()) {
                        funcH.add(h);
                    }
                });

                if (viewTemperature == null && viewHumidity == null) {
                    viewTemperature = new ControllerGraphics();
                    viewHumidity = new ControllerGraphics();
                    viewTemperature.throwJFrame();
                    //viewTemperature.throwTemperature(funcT);
                    viewHumidity.throwJFrame();
                    //viewHumidity.throwHumidity(funcH);
                } else if (!(viewTemperature.jFrame.isVisible() && viewHumidity.jFrame.isVisible())) {
                    viewTemperature.throwJFrame();
                    viewHumidity.throwJFrame();
                }

                viewTemperature.throwTemperature(funcT);
                viewHumidity.throwHumidity(funcH);
            }
        });
        vistaRegistro.getjComboBox1().addActionListener((ActionEvent e) -> {
            if (vistaRegistro.getjComboBox1().getSelectedIndex() == 0) {
                agregarDatosTabla(vistaRegistro.getjComboBox1().getSelectedItem().toString());
            }
            if (vistaRegistro.getjComboBox1().getSelectedIndex() == 1) {
                agregarDatosTabla(vistaRegistro.getjComboBox1().getSelectedItem().toString());
            }
            if (vistaRegistro.getjComboBox1().getSelectedIndex() == 2) {
                agregarDatosTabla(vistaRegistro.getjComboBox1().getSelectedItem().toString());
            }
            if (vistaRegistro.getjComboBox1().getSelectedIndex() == 3) {
                agregarDatosTabla(vistaRegistro.getjComboBox1().getSelectedItem().toString());
            }
            if (vistaRegistro.getjComboBox1().getSelectedIndex() == 4) {
                agregarDatosTabla(vistaRegistro.getjComboBox1().getSelectedItem().toString());
            }
        });
    }
}
