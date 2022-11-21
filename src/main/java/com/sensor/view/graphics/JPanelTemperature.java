/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sensor.view.graphics;

import com.sensor.controller.graphics.ControllerTemperature;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author KevinCyndaquil
 */
public class JPanelTemperature extends javax.swing.JPanel {

    private ArrayList<Object> function;
    protected final ControllerTemperature controller;

    /**
     * Creates new form JPanelTemperaturas
     *
     * @param controller
     * @param width
     * @param height
     */
    public JPanelTemperature(ControllerTemperature controller, int width, int height) {
        initComponents();

        this.setBounds(0, 0, width, height);
        this.controller = controller;
        this.function = null;
    }

    public ArrayList<Object> getFunction() {
        return function;
    }
    
    public void setFunction(ArrayList<Object> function){
        this.function = function;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        //Aqui sacamos lo artistico
        controller.paintCartesian(g);
        controller.paintGraphic(g);
        /*if (ControllerTemperature.function == null) {
            
        } else {
            controller.paintGraphic(g, new ArrayList<>(function));
        }*/

    }
}