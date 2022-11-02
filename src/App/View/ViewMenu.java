package App.View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ViewMenu extends JFrame {

    private JPanel panelMenu;
    private Font fontPrincipal, fontTitulo,fontComentarios;
    public JLabel labelGitHub;
    public JLabel labelTwitter;
    public JLabel labelTierra;

    public ViewMenu() {
        setTitle("Menu");

        createFonts();
        createJPanels();
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
        labelBienvenido.setSize(120,15);
        labelBienvenido.setLocation((panelMenu.getWidth() - labelBienvenido.getWidth()) / 2, 20);
        labelBienvenido.setFont(fontComentarios);
        labelBienvenido.setForeground(Color.white);
        panelMenu.add(labelBienvenido);

        JLabel labelControlTH = new JLabel("CONTROL DE TEMPERATURA Y HUMEDAD");
        labelControlTH.setSize(481,20);
        labelControlTH.setLocation((panelMenu.getWidth() - labelControlTH.getWidth()) / 2, 45);
        labelControlTH.setFont(fontTitulo);
        labelControlTH.setForeground(Color.white);
        panelMenu.add(labelControlTH);

        JLabel labelButton1 = new JLabel();
        labelButton1.setIcon(new ImageIcon("Resources/Images/buttonMenuActive.png"));
        labelButton1.setBounds(580, 90, labelButton1.getIcon().getIconWidth(), labelButton1.getIcon().getIconHeight());
        panelMenu.add(labelButton1);

        JLabel labelButton2 = new JLabel();
        labelButton2.setIcon(new ImageIcon("Resources/Images/button2MenuActive.png"));
        labelButton2.setBounds(580, 342, labelButton2.getIcon().getIconWidth(), labelButton2.getIcon().getIconHeight());
        panelMenu.add(labelButton2);

        //************************************Promociones************************************

        labelGitHub = new JLabel();
        labelGitHub.setIcon(new ImageIcon("Resources/Images/github.png"));
        labelGitHub.setBounds(4, 155, labelGitHub.getIcon().getIconWidth(), labelGitHub.getIcon().getIconHeight());
        panelMenu.add(labelGitHub);

        labelTwitter = new JLabel();
        labelTwitter.setIcon(new ImageIcon("Resources/Images/twitter.png"));
        labelTwitter.setBounds(4, 195, labelTwitter.getIcon().getIconWidth(), labelTwitter.getIcon().getIconHeight());
        panelMenu.add(labelTwitter);

        labelTierra = new JLabel();
        labelTierra.setIcon(new ImageIcon("Resources/Images/tierra.png"));
        labelTierra.setBounds(4, 235, labelTierra.getIcon().getIconWidth(), labelTierra.getIcon().getIconHeight());
        panelMenu.add(labelTierra);

        JLabel labelFondo = new JLabel();
        labelFondo.setIcon(new ImageIcon("Resources/Images/fondoMenu.png"));
        labelFondo.setBounds(0, 0, labelFondo.getIcon().getIconWidth(), labelFondo.getIcon().getIconHeight());
        panelMenu.add(labelFondo);


    }

    public void createFonts() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources/Fonts/Roboto/Roboto-Regular.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources/Fonts/Roboto/Roboto-Black.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources/Fonts/Cascadia Code/CascadiaCode.ttf")));

        } catch (IOException | FontFormatException e) {
            System.out.println("Error al cargar la fuente");
        }
        fontPrincipal = new Font("Roboto", Font.BOLD, 12);
        fontTitulo = new Font("Roboto Black", Font.PLAIN, 25);
        fontComentarios = new Font("Cascadia Code", Font.PLAIN, 20);
    }
}
