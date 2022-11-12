package com.sensor.app.controller;

import com.sensor.app.models.CRUDS.CRUDHumedad;
import com.sensor.app.models.CRUDS.CRUDTemperatura;
import com.sensor.app.views.VistaRegistro;
import com.sensor.graphics.controllers.ControllerGraphics;
import com.sensor.graphics.models.Humidity;
import com.sensor.graphics.models.Temperature;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.util.ArrayList;

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
    

    public ControllerRegistros() {
        vistaRegistro = new VistaRegistro();
        crudH = CRUDHumedad.getInstance();
        crudT = CRUDTemperatura.getInstance();
        tablaT = (DefaultTableModel) vistaRegistro.jTable1.getModel();
        tablaH = (DefaultTableModel) vistaRegistro.jTable3.getModel();
        events();
    }

    public static ControllerRegistros getInstance() {
        if (instance == null) {
            instance = new ControllerRegistros();
        }
        return instance;
    }

    public void showView() {
        vistaRegistro.setVisible(true);
        agregarDatosTabla();
    }

    public void agregarDatosTabla() {
        tablaT.setRowCount(0);
        //System.out.println(LocalDate.now());
        for (Object modelo : crudT.selectAll()) {
            Temperature t = (Temperature) modelo;
            if (t.getDate().getDayOfMonth() == LocalDate.now().getDayOfMonth()) {
                tablaT.addRow(new Object[]{
                    t.getDate().getYear() + " / " + t.getDate().getDayOfMonth() + " / " + t.getDate().getDayOfMonth(),
                    t.getDate().getHour() + ":" + t.getDate().getMinute() + ":" + t.getDate().getSecond(),
                    t.getCelsius()
                });
            }
        }
        tablaH.setRowCount(0);
        for (Object modelo : crudH.selectAll()) {
            Humidity h = (Humidity) modelo;
            if (h.getDate().getDayOfMonth() == LocalDate.now().getDayOfMonth()) {
                tablaH.addRow(new Object[]{
                    h.getDate().getYear() + " / " + h.getDate().getDayOfMonth() + " / " + h.getDate().getDayOfMonth(),
                    h.getDate().getHour() + ":" + h.getDate().getMinute() + ":" + h.getDate().getSecond(),
                    h.getPercentage()
                });
            }
        }
    }

    public void events() {
        vistaRegistro.getjDateChooser1().getDateEditor().addPropertyChangeListener((PropertyChangeEvent evt) -> {
            if ("date".equals(evt.getPropertyName())) {
                fechaC = vistaRegistro.getjDateChooser1().getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                tablaT.setRowCount(0);
                System.out.println(LocalDate.now());
                for (Object modelo : crudT.selectAll()) {
                    Temperature t = (Temperature) modelo;
                    if (t.getDate().getDayOfMonth() == fechaC.getDayOfMonth()) {
                        tablaT.addRow(new Object[]{
                            t.getDate().getYear() + " / " + t.getDate().getDayOfMonth() + " / " + t.getDate().getDayOfMonth(),
                            t.getDate().getHour() + ":" + t.getDate().getMinute() + ":" + t.getDate().getSecond(),
                            t.getCelsius()
                        });
                    }
                }
                tablaH.setRowCount(0);
                for (Object modelo : crudH.selectAll()) {
                    Humidity h = (Humidity) modelo;
                    if (h.getDate().getDayOfMonth() == fechaC.getDayOfMonth()) {
                        tablaH.addRow(new Object[]{
                            h.getDate().getYear() + " / " + h.getDate().getDayOfMonth() + " / " + h.getDate().getDayOfMonth(),
                            h.getDate().getHour() + ":" + h.getDate().getMinute() + ":" + h.getDate().getSecond(),
                            h.getPercentage()
                        });
                    }
                }
            }
        });
        vistaRegistro.jButton1.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent ev){
                fechaC = vistaRegistro.getjDateChooser1().getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                ArrayList <Object> tx = CRUDTemperatura.getInstance().selectAll();
                ArrayList <Object> funcT = new ArrayList<>();
                ArrayList <Object> hx = CRUDHumedad.getInstance().selectAll();
                ArrayList <Object> funcH = new ArrayList<>();
                
                tx.forEach(o -> {
                    Temperature t = (Temperature)o;
                    if(t.getDate().getDayOfMonth() == fechaC.getDayOfMonth()){
                        funcT.add(t);
                    }
                });
                hx.forEach(o -> {
                    Humidity h = (Humidity)o;
                    if(h.getDate().getDayOfMonth() == fechaC.getDayOfMonth()){
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
                } else if (!(viewTemperature.jFrame.isVisible() && viewHumidity.jFrame.isVisible())){
                    viewTemperature.throwJFrame();
                    viewHumidity.throwJFrame();
                }
                
                viewTemperature.throwTemperature(funcT);
                viewHumidity.throwHumidity(funcH);
            }
        });
        vistaRegistro.getjComboBox1().addActionListener((ActionEvent e) -> {
            /*if(vistaRegistro.getjComboBox1().getSelectedIndex() == 0){
            LocalDate fechaC = vistaRegistro.getjDateChooser1().getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            tablaT.setRowCount(0);
            for (Object modelo : crudT.selectAll()) {
            ModelTemperatura t = (ModelTemperatura) modelo;
            if(t.getFecha().equals(fechaC.toString()) && t.getHora()){ //ahi falta la condicion de la hora dependiendo de la seleccion del combobox
            tablaT.addRow(new Object[]{t.getFecha(), t.getHora(), t.getTemperatura()});
            }
            //tablaT.addRow(new Object[]{t.getFecha(), t.getHora(), t.getTemperatura()});
            }
            tablaH.setRowCount(0);
            for (Object modelo : crudH.selectAll()) {
            ModelHumedad h = (ModelHumedad) modelo;
            if (h.getFecha().equals(fechaC.toString()) && h.getHora().) {///ahi falta la condicion de la hora dependiendo de la seleccion del combobox
            tablaH.addRow(new Object[]{h.getFecha(), h.getHora(), h.getHumedad()});
            }
            //tablaH.addRow(new Object[]{h.getFecha(), h.getHora(), h.getHumedad()});
            }
            }*/
        });
    }
}
