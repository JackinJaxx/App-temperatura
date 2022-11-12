/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensor.graphics.controllers;
        
import com.sensor.graphics.views.JFrameGraphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author KevinCyndaquil
 */
public class ControllerGraphics {

    public JFrameGraphics jFrame;
    protected static ControllerTemperatures controllerT;
    protected static ControllerHumidity controllerH;
    
    private final JLabel jFond = new JLabel();
    private final ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("Images/fonds/fond02.jpg"));
    
    public Integer throwJFrame() {
        if (jFrame == null) {
            jFrame = new JFrameGraphics();
            jFrame.setVisible(true);
            jFrame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
            jFrame.setLocationRelativeTo(null);
            
            putFondImage();
            initButtonEvents();
            //throwTemperature();
            //throwHumidity();
            return 1;
        } else if (!jFrame.isVisible()) {
            jFrame.setVisible(true);

            return 1;
        } else {
            return 0;
        }
    }
    
    public Integer putFondImage() {
        try {
            jFond.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
            jFond.setIcon(icon);
            jFrame.jBackground.add(jFond);
            return 1;
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println("Error al cargar la imagen del jBackground del jFrameGraphics");
            return 0;
        }
    }
    
    public Integer throwTemperature() {
        if (controllerT == null) {
            controllerT = new ControllerTemperatures(jFrame);
            controllerT.throwJPanel(jFrame.jCanvas.getWidth(), jFrame.jCanvas.getHeight());
            jFrame.setTitle("GRAPHIC TEMPERATURE");
            
            return 1;
        } else {
            return 0;
        }
    }
    public Integer throwTemperature(ArrayList <Object> function) {
        if (controllerT == null) {
            controllerT = new ControllerTemperatures(jFrame);
            controllerT.throwJPanel(jFrame.jCanvas.getWidth(), jFrame.jCanvas.getHeight(), function);
            jFrame.setTitle("GRAPHIC TEMPERATURE");
            
            return 1;
        } else {
            return 0;
        }
    }
    
    public Integer throwHumidity() {
        if (controllerH == null) {
            controllerH = new ControllerHumidity(jFrame);
            controllerH.throwJPanel(jFrame.jCanvas.getWidth(), jFrame.jCanvas.getHeight());
            jFrame.setTitle("GRAPHIC HUMIDITY");
            
            return 1;
        }
        
        return 0;
    }
    
    public Integer throwHumidity(ArrayList <Object> function) {
        if (controllerH == null) {
            controllerH = new ControllerHumidity(jFrame);
            controllerH.throwJPanel(jFrame.jCanvas.getWidth(), jFrame.jCanvas.getHeight(), function);
            jFrame.setTitle("GRAPHIC HUMIDITY");
            
            return 1;
        }
        return 0;
    }

    public void initButtonEvents() {
        jFrame.jButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                if (jFrame.jButton1.getText().equals("PARAR")) {
                    jFrame.jButton1.setText("INICIAR");
                    controllerT.stop();
                } else if (jFrame.jButton1.getText().equals("INICIAR")) {
                    jFrame.jButton1.setText("PARAR");
                    controllerT.start();
                }
            }
        });
        
    }
}
