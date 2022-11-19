package com.sensor.controller.app;

import com.sensor.controller.graphics.ControllerGraphics;
import com.sensor.model.app.CRUDS.CRUDHumedad;
import com.sensor.model.app.CRUDS.CRUDTemperatura;
import com.sensor.model.app.ModelHumidity;
import com.sensor.view.app.VistaRegistro;
import com.sensor.model.app.ModelTemperature;
import com.sensor.controller.graphics.ControllerHumidity;
import com.sensor.controller.graphics.ControllerTemperature;

import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Clase que se encarga de controlar la vista Registros
 *
 * @author KevinCyndaquil, JackinJaxx, Wuicho24
 * @version 1.0
 */
public final class ControllerRegistros {

    public static ControllerRegistros instance;
    public static LocalTime horaInicio = null;
    public static LocalTime horaFin = null;
    public static LocalDate fechaCalendar;

    private final VistaRegistro vistaRegistro;
    private final CRUDHumedad crudH;
    private final CRUDTemperatura crudT;
    private final DefaultTableModel tablaT;
    private final DefaultTableModel tablaH;

    public static Integer controllerGraphic = null;
    private ControllerTemperature viewTemperature;
    private ControllerHumidity viewHumidity;

    /**
     * Constructor de la clase.Esta instancia la vista Registros
     */
    public ControllerRegistros() {
        vistaRegistro = new VistaRegistro();
        crudH = CRUDHumedad.getInstance();
        crudT = CRUDTemperatura.getInstance();
        tablaT = (DefaultTableModel) vistaRegistro.jTable1.getModel();
        tablaH = (DefaultTableModel) vistaRegistro.jTable3.getModel();
        fechaCalendar = vistaRegistro.getjDateChooser1().getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        //setFond();
        events();
    }

    /**
     * Metodo que implementa el patron singleton para que solo exista una
     * instancia de esta clase
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

                //vistaRegistro.repaint();
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
     * Metodo que se encarga de agregar los datos de la base de datos a las
     * tablas
     *
     * @param rangoHora All, 0:00-5:59, 6:00-11:59, 12:00-17:59, 18:00-23:59
     */
    public void agregarDatosTabla(String rangoHora) {
        boolean bandera = rangoHora.equals("Todos");
        if (!bandera) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH"
                    + ":mm");
            System.out.println(rangoHora);
            horaInicio = LocalTime.parse(rangoHora.split(" a ")[0], formatter);
            System.out.println(horaInicio);
            horaFin = LocalTime.parse(rangoHora.split(" a ")[1], formatter);
            System.out.println(horaFin);
        } else {
            horaInicio = null;
            horaFin = null;
        }
        tablaT.setRowCount(0);
        //fechaCalendar = vistaRegistro.getjDateChooser1().getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        for (Object modelo : crudT.selectAll()) {
            ModelTemperature t = (ModelTemperature) modelo;
            if (bandera) {
                if (t.getDate().toLocalDate().equals(fechaCalendar)) {
                    tablaT.addRow(new Object[]{
                        t.getDate().getYear() + " / " + t.getDate().getDayOfMonth() + " / " + t.getDate().getDayOfMonth(),
                        t.getDate().getHour() + ":" + t.getDate().getMinute() + ":" + t.getDate().getSecond(),
                        t.getCelsius()
                    });
                }
            } else if (t.getDate().toLocalDate().equals(fechaCalendar) && (t.getDate().toLocalTime().isAfter(horaInicio) && t.getDate().toLocalTime().isBefore(horaFin))) {
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
                if (h.getDate().toLocalDate().equals(fechaCalendar)) {
                    tablaH.addRow(new Object[]{
                        h.getDate().getYear() + " / " + h.getDate().getDayOfMonth() + " / " + h.getDate().getDayOfMonth(),
                        h.getDate().getHour() + ":" + h.getDate().getMinute() + ":" + h.getDate().getSecond(),
                        h.getPercentage()
                    });
                }
            } else if (h.getDate().toLocalDate().equals(fechaCalendar) && (h.getDate().toLocalTime().isAfter(horaInicio) && h.getDate().toLocalTime().isBefore(horaFin))) {
                tablaH.addRow(new Object[]{
                    h.getDate().getYear() + " / " + h.getDate().getDayOfMonth() + " / " + h.getDate().getDayOfMonth(),
                    h.getDate().getHour() + ":" + h.getDate().getMinute() + ":" + h.getDate().getSecond(),
                    h.getPercentage()
                });
            }
        }
    }

    /**
     * Metodo que se encarga de agregar los eventos a los componentes de la
     * vista Registros
     */
    public void events() {
        vistaRegistro.getjDateChooser1().getDateEditor().addPropertyChangeListener((PropertyChangeEvent evt) -> {
            if ("date".equals(evt.getPropertyName())) {
                fechaCalendar = vistaRegistro.getjDateChooser1().getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                agregarDatosTabla(vistaRegistro.getjComboBox1().getSelectedItem().toString());
                //JOptionPane.showMessageDialog(vistaRegistro, "AQUI ta la fecha " + fechaCalendar);
            }
        });
        vistaRegistro.jButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                try {
                    if (controllerGraphic == null || controllerGraphic == 1) {
                        ArrayList<Object> tx = CRUDTemperatura.getInstance().selectDate(fechaCalendar, fechaCalendar);
                        if (viewTemperature == null) {
                            viewTemperature = new ControllerTemperature();
                        }
                        
                        viewTemperature.throwJFrame();
                        viewTemperature.throwJPanel(tx);
                    } else if (controllerGraphic == 0) {
                        ArrayList<Object> hx = CRUDHumedad.getInstance().selectDate(fechaCalendar, fechaCalendar);
                        if (viewHumidity == null) {
                            viewHumidity = new ControllerHumidity();
                        }
                        
                        viewHumidity.throwJFrame();
                        viewHumidity.throwJPanel(hx);
                    }
                } catch (NullPointerException e) {
                    System.out.println(e);
                    JOptionPane.showMessageDialog(vistaRegistro, "NO HAY DATOS QUE LEER SIN LA BASE DE DATOS");
                }

            }
        });
        vistaRegistro.getjComboBox1().addActionListener((ActionEvent e) -> {
            agregarDatosTabla(vistaRegistro.getjComboBox1().getSelectedItem().toString());
        });
        vistaRegistro.jButton2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                try {
                    if (controllerGraphic == null || controllerGraphic == 1) {
                        controllerGraphic = 0;
                        vistaRegistro.jButton2.setText("CAMBIAR A TEMPERATURA");
                        ControllerGraphics.jFrame.setVisible(false);
                        
                    } else {
                        controllerGraphic = 1;
                        vistaRegistro.jButton2.setText("CAMBIAR A HUMEDAD");
                        ControllerGraphics.jFrame.setVisible(false);
                        
                    }
                } catch (NullPointerException ex) {
                    System.out.println("Hay pedillos");
                }
            }
        });
    }
}
