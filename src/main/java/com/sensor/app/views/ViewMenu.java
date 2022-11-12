package com.sensor.app.views;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public final class ViewMenu extends JFrame {

    private JPanel panelMenu;
    private Font fontPrincipal, fontPrincipal2, fontTitulo, fontComentarios;
    public JLabel labelGitHub, labelTwitter, labelTierra;
    public JLabel labelButton1, labelButton2;
    public JLabel labelTermometro, labelCelciusFarenheitl;
    public JTextField jTTemperatura, jTHumedad;

    public ViewMenu() {
        setTitle("Menu");

        createFonts();
        createJPanels();
        createJtextFields();
        createJLabels();

        setLayout(null);
        setSize(800, 600);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void createJPanels() {
        panelMenu = new JPanel();
        panelMenu.setBounds(0, 0, 800, 600);
        panelMenu.setBackground(Color.getColor("#eff0f3"));
        panelMenu.setLayout(null);
        add(panelMenu);
    }

    public void createJLabels() {

        JLabel labelBienvenido = new JLabel("BIENVENIDO");
        labelBienvenido.setSize(120, 15);
        labelBienvenido.setLocation((panelMenu.getWidth() - labelBienvenido.getWidth()) / 2, 20);
        labelBienvenido.setFont(fontComentarios);
        labelBienvenido.setForeground(Color.white);
        panelMenu.add(labelBienvenido);

        JLabel labelControlTH = new JLabel("CONTROL DE TEMPERATURA Y HUMEDAD");
        labelControlTH.setSize(481, 20);
        labelControlTH.setLocation((panelMenu.getWidth() - labelControlTH.getWidth()) / 2, 45);
        labelControlTH.setFont(fontTitulo);
        labelControlTH.setForeground(Color.white);
        panelMenu.add(labelControlTH);

        labelTermometro = new JLabel();
        labelTermometro.setIcon(new ImageIcon("src/main/resources/Images/termometro/termometro_30.png"));
        labelTermometro.setBounds(100, 140, labelTermometro.getIcon().getIconWidth(), labelTermometro.getIcon().getIconHeight());
        panelMenu.add(labelTermometro);

        labelCelciusFarenheitl = new JLabel("°C");
        labelCelciusFarenheitl.setBounds(350, 300, 48, 48);
        labelCelciusFarenheitl.setFont(fontPrincipal);
        labelCelciusFarenheitl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelMenu.add(labelCelciusFarenheitl);

        labelButton1 = new JLabel();
        labelButton1.setIcon(new ImageIcon("src/main/resources/Images/buttonMenuActive.png"));
        labelButton1.setBounds(580, 90, labelButton1.getIcon().getIconWidth(), labelButton1.getIcon().getIconHeight());
        labelButton1.setFocusable(true);
        panelMenu.add(labelButton1);

        labelButton2 = new JLabel();
        labelButton2.setIcon(new ImageIcon("src/main/resources/Images/button2MenuActive.png"));
        labelButton2.setBounds(580, 342, labelButton2.getIcon().getIconWidth(), labelButton2.getIcon().getIconHeight());
        labelButton2.setFocusable(true);
        panelMenu.add(labelButton2);

        //************************************Decoraciones************************************
        labelGitHub = new JLabel();
        labelGitHub.setIcon(new ImageIcon("src/main/resources/Images/github.png"));
        labelGitHub.setBounds(4, 155, labelGitHub.getIcon().getIconWidth(), labelGitHub.getIcon().getIconHeight());
        panelMenu.add(labelGitHub);

        labelTwitter = new JLabel();
        labelTwitter.setIcon(new ImageIcon("src/main/resources/Images/twitter.png"));
        labelTwitter.setBounds(4, 197, labelTwitter.getIcon().getIconWidth(), labelTwitter.getIcon().getIconHeight());
        panelMenu.add(labelTwitter);

        labelTierra = new JLabel();
        labelTierra.setIcon(new ImageIcon("src/main/resources/Images/tierra.png"));
        labelTierra.setBounds(4, 237, labelTierra.getIcon().getIconWidth(), labelTierra.getIcon().getIconHeight());
        panelMenu.add(labelTierra);

        //************************************Fondo************************************
        JLabel labelFondo = new JLabel();
        labelFondo.setIcon(new ImageIcon("src/main/resources/Images/fondoMenu.png"));
        labelFondo.setBounds(0, 0, labelFondo.getIcon().getIconWidth(), labelFondo.getIcon().getIconHeight());
        panelMenu.add(labelFondo);

    }

    public void createJtextFields() {
        jTTemperatura = new JTextField();
        jTTemperatura.setText("30.0");
        jTTemperatura.setFont(fontPrincipal);
        jTTemperatura.setOpaque(false);
        jTTemperatura.setBorder(null);
        jTTemperatura.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        //jTTemperatura.setFocusable(false);
        jTTemperatura.setBounds(270, 300, 84, 48);

        panelMenu.add(jTTemperatura);

        jTHumedad = new JTextField();
        jTHumedad.setText("Humedad: 60.0%");
        jTHumedad.setFont(fontPrincipal2);
        jTHumedad.setOpaque(false);
        jTHumedad.setBorder(null);
        jTHumedad.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        jTHumedad.setFocusable(false);
        jTHumedad.setBounds(275, 350, 130, 32);
        panelMenu.add(jTHumedad);
    }

    public void createFonts() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Fonts/Roboto/Roboto-Regular.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Fonts/Roboto/Roboto-Black.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Fonts/Cascadia Code/CascadiaCode.ttf")));

        } catch (IOException | FontFormatException e) {
            System.out.println("Error al cargar la fuente");
        }
        fontPrincipal = new Font("Roboto", Font.BOLD, 35);
        fontPrincipal2 = new Font("Roboto", Font.PLAIN, 17);
        fontTitulo = new Font("Roboto Black", Font.PLAIN, 25);
        fontComentarios = new Font("Cascadia Code", Font.PLAIN, 20);
    }
}
