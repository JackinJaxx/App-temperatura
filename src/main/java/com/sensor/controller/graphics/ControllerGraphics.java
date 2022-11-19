/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sensor.controller.graphics;
        
import com.sensor.view.graphics.JFrameGraphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author KevinCyndaquil
 */
public class ControllerGraphics {

    public static JFrameGraphics jFrame;
    
    private final JLabel jFond;
    private final ImageIcon icon;
    
    public ControllerGraphics(){
        icon = new ImageIcon(getClass().getClassLoader().getResource("Images/fonds/fond02.jpg"));
        jFond = new JLabel();
    }
    
    public Integer throwJFrame() {
        if (jFrame == null) {
            jFrame = new JFrameGraphics();
            jFrame.setDefaultCloseOperation(javax.swing.JFrame.HIDE_ON_CLOSE);
            jFrame.setVisible(true);
            jFrame.setLocationRelativeTo(null);
            
            putFondImage();
            initButtonEvents();
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
            jFrame.jBackground.repaint();
            return 1;
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println("Error al cargar la imagen del jBackground del jFrameGraphics");
            return 0;
        }
    }
    
    public Integer removeJPanel() {
        if (jFrame.jCanvas.getComponents().length == 1) {
            jFrame.jCanvas.remove(0);
            return 1;
        }
        return 0;
    }
    
    public void initButtonEvents() {
        /*jFrame.jButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ev) {
                if (jFrame.jButton1.getText().equals("PARAR")) {
                    jFrame.jButton1.setText("INICIAR");
                    //controllerT.stop();
                } else if (jFrame.jButton1.getText().equals("INICIAR")) {
                    jFrame.jButton1.setText("PARAR");
                    //controllerT.start();
                }
            }
        });*/
    }
}
