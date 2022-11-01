package App.View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ViewMenu extends JFrame {

    private JPanel panelMenu;
    private Font fontPrincipal, fontTitulo,fontComentarios;

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
        panelMenu.setBackground(Color.white);
        panelMenu.setLayout(null);
        add(panelMenu);
    }

    public void createJLabels() {
        JLabel labelMenu = new JLabel("BIENVENIDO");
        labelMenu.setSize(160,25);
        labelMenu.setLocation((panelMenu.getWidth() - labelMenu.getWidth()) / 2, 20);
        labelMenu.setFont(fontTitulo);
        panelMenu.add(labelMenu);
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
        fontTitulo = new Font("Roboto Black", Font.PLAIN, 27);
        fontComentarios = new Font("Cascadia Code", Font.PLAIN, 20);
    }
}
