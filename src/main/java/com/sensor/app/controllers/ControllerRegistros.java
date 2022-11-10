package App.Controller;


import App.Model.CRUDS.CRUDHumedad;
import App.Model.CRUDS.CRUDTemperatura;
import App.Model.ModelHumedad;
import App.Model.ModelTemperatura;
import App.View.VistaRegistro;

import javax.swing.table.DefaultTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.Date;

public class ControllerRegistros {
    static ControllerRegistros instance;
    private VistaRegistro vistaRegistro;
    private CRUDHumedad crudH;
    private CRUDTemperatura crudT;
    DefaultTableModel tablaT, tablaH;
    private ControllerRegistros() {
        vistaRegistro = new VistaRegistro();
        crudH = CRUDHumedad.getInstance();
        crudT = CRUDTemperatura.getInstance();
        tablaT = (DefaultTableModel) vistaRegistro.getjTable1().getModel();
        tablaH = (DefaultTableModel) vistaRegistro.getjTable3().getModel();

    }
    public static ControllerRegistros getInstance() {
        if (instance == null) {
            instance = new ControllerRegistros();
        }
        return instance;
    }

    public void showView(){
        vistaRegistro.setVisible(true);
        agregarDatosTabla();
    }

    public void agregarDatosTabla(){
        tablaT.setRowCount(0);
        System.out.println(LocalDate.now());
        for (Object modelo : crudT.selectAll()) {
            ModelTemperatura modelT = (ModelTemperatura) modelo;
            tablaT.addRow(new Object[]{modelT.getFecha(), modelT.getHora(), modelT.getTemperatura()});
        }
        tablaH.setRowCount(0);
        for (Object modelo : crudH.selectAll()) {
            ModelHumedad modelH = (ModelHumedad) modelo;
            tablaH.addRow(new Object[]{modelH.getFecha(), modelH.getHora(), modelH.getHumedad()});
        }
    }
}
