package com.sensor.view.app;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ViewRegistro extends JFrame {
    private JPanel panelTabla, panelHeader;
    private JLabel jlabelHeader, jlabelIconRegistros, jlabelTabla, jlabelGraficar;
    private JLabel jlabelOpFecha, jlabelOpHora, jlabelOpCambiar,jlabelTemperatura,jlabelHumedad;
    private JTable table;
    private final LocalDate fecha; //Fecha actual
    private final LocalTime hora; //Hora actual
    private final Date fecha2;//fecha para mostrar al principio del JDateChooser
    private JCalendar jCalendar1;
    private JComboBox<String> jComboBox1;

    public ViewRegistro() {
        setTitle("Registros");

        fecha = LocalDate.now(); //Fecha actual
        hora = LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))); //Hora actual CON formato HH:MM asi nos evitamos de poner segundos
        fecha2 = Date.from(fecha.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()); //conversion de LocalDate a Date para el JDateChooser
        System.out.println(fecha);
        System.out.println(hora);

        setLayout(null);
        createJPanels();
        createJCalendar();
        createJRangoHora();
        createJlabels();

        createJTable();
        //1000 780
        setSize(1014, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setVisible(true);
    }

    public JCalendar getjCalendar1() {
        return jCalendar1;
    }

    public void setjCalendar1(JCalendar jCalendar1) {
        this.jCalendar1 = jCalendar1;
    }

    public JComboBox<String> getjComboBox1() {
        return jComboBox1;
    }

    public void setjComboBox1(JComboBox<String> jComboBox1) {
        this.jComboBox1 = jComboBox1;
    }

    public void createJCalendar() {
        jCalendar1 = new JCalendar(fecha2);
        jCalendar1.setBounds(184, 60, 233, 200);
        jCalendar1.setVisible(false);
        jCalendar1.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("calendar")) {
                jCalendar1.setVisible(false);
            }
        });
        panelTabla.add(jCalendar1);
    }

    public void createJRangoHora(){
        jComboBox1 = new JComboBox<>();
        jComboBox1.setModel(new DefaultComboBoxModel<>(new String[] { "Todos", "00:00 a 06:00", "06:00 a 12:00", "12:00 a 18:00", "18:00 a 24:00" }) );
        jComboBox1.setBounds(417, 60, 233, 20);
        jComboBox1.setVisible(false);
        jComboBox1.addActionListener(evt -> {
            jComboBox1.setVisible(false);
        });
        panelTabla.add(jComboBox1);
    }
    public void createJPanels() {
        panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 1000, 240);
        panelHeader.setBackground(Color.white);
        //panelHeader.setBorder(BorderFactory.createLineBorder(Color.black));
        panelHeader.setLayout(null);
        add(panelHeader);

        panelTabla = new JPanel();
        panelTabla.setBounds(0, 240, 1000, 480);
        panelTabla.setBackground(Color.white);
        panelTabla.setLayout(null);

        add(panelTabla);
    }

    public void createJlabels() {

        jlabelHeader = new JLabel();
        jlabelHeader.setIcon(new ImageIcon("src/main/resources/Images/Registros/header.png"));
        jlabelHeader.setBounds(0, 0, jlabelHeader.getIcon().getIconWidth(), jlabelHeader.getIcon().getIconHeight());
        panelHeader.add(jlabelHeader);

        jlabelIconRegistros = new JLabel();
        jlabelIconRegistros.setIcon(new ImageIcon("src/main/resources/Images/Registros/iconregistro.png"));
        jlabelIconRegistros.setBounds(40, 90, jlabelIconRegistros.getIcon().getIconWidth(), jlabelIconRegistros.getIcon().getIconHeight());
        panelHeader.add(jlabelIconRegistros);

        jlabelTabla = new JLabel();
        jlabelTabla.setIcon(new ImageIcon("src/main/resources/Images/Registros/tabla.png"));
        jlabelTabla.setBounds(5, 0, jlabelTabla.getIcon().getIconWidth(), jlabelTabla.getIcon().getIconHeight());
        panelTabla.add(jlabelTabla);

        jlabelGraficar = new JLabel();
        jlabelGraficar.setIcon(new ImageIcon("src/main/resources/Images/Registros/buttonGraficar.png"));
        jlabelGraficar.setBounds(805, 115, jlabelGraficar.getIcon().getIconWidth(), jlabelGraficar.getIcon().getIconHeight());
        jlabelGraficar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jlabelGraficar.setIcon(new ImageIcon("src/main/resources/Images/Registros/buttonGraficar_hover.png"));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                jlabelGraficar.setIcon(new ImageIcon("src/main/resources/Images/Registros/buttonGraficar_active.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jlabelGraficar.setIcon(new ImageIcon("src/main/resources/Images/Registros/buttonGraficar.png"));
            }
        });
        panelHeader.add(jlabelGraficar);

        jlabelOpFecha = new JLabel();
        jlabelOpFecha.setIcon(new ImageIcon("src/main/resources/Images/Registros/opciones.png"));
        jlabelOpFecha.setBounds(198, 16, jlabelOpFecha.getIcon().getIconWidth(), jlabelOpFecha.getIcon().getIconHeight());
        jlabelOpFecha.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jlabelOpFecha.setIcon(new ImageIcon("src/main/resources/Images/Registros/opciones_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jlabelOpFecha.setIcon(new ImageIcon("src/main/resources/Images/Registros/opciones.png"));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                jlabelOpFecha.setIcon(new ImageIcon("src/main/resources/Images/Registros/opciones_click.png"));
                jCalendar1.setVisible(true);
                new Thread(() -> {
                    while (jCalendar1.isVisible()) {
                        jCalendar1.repaint();
                    }
                }).start();
            }
        });
        jlabelTabla.add(jlabelOpFecha);

        jlabelOpHora = new JLabel();
        jlabelOpHora.setIcon(new ImageIcon("src/main/resources/Images/Registros/opciones.png"));
        jlabelOpHora.setBounds(431, 16, jlabelOpHora.getIcon().getIconWidth(), jlabelOpHora.getIcon().getIconHeight());
        jlabelOpHora.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jlabelOpHora.setIcon(new ImageIcon("src/main/resources/Images/Registros/opciones_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jlabelOpHora.setIcon(new ImageIcon("src/main/resources/Images/Registros/opciones.png"));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                jlabelOpHora.setIcon(new ImageIcon("src/main/resources/Images/Registros/opciones_click.png"));
                jComboBox1.setVisible(true);

            }
        });
        jlabelTabla.add(jlabelOpHora);

        jlabelTemperatura = new JLabel();
        jlabelTemperatura.setIcon(new ImageIcon("src/main/resources/Images/Registros/Temperatura.png"));
        jlabelTemperatura.setBounds(783, 23, jlabelTemperatura.getIcon().getIconWidth(), jlabelTemperatura.getIcon().getIconHeight());
        jlabelTabla.add(jlabelTemperatura);

        jlabelHumedad = new JLabel();
        jlabelHumedad.setIcon(new ImageIcon("src/main/resources/Images/Registros/Humedad.png"));
        jlabelHumedad.setBounds(783, 23, jlabelHumedad.getIcon().getIconWidth(), jlabelHumedad.getIcon().getIconHeight());
        jlabelHumedad.setVisible(false);
        jlabelTabla.add(jlabelHumedad);

        jlabelOpCambiar = new JLabel();
        jlabelOpCambiar.setIcon(new ImageIcon("src/main/resources/Images/Registros/opciones.png"));
        jlabelOpCambiar.setBounds(704, 16, jlabelOpCambiar.getIcon().getIconWidth(), jlabelOpCambiar.getIcon().getIconHeight());
        jlabelOpCambiar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jlabelOpCambiar.setIcon(new ImageIcon("src/main/resources/Images/Registros/opciones_hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jlabelOpCambiar.setIcon(new ImageIcon("src/main/resources/Images/Registros/opciones.png"));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                jlabelOpCambiar.setIcon(new ImageIcon("src/main/resources/Images/Registros/opciones_click.png"));
                if(jlabelTemperatura.isVisible()){
                    jlabelTemperatura.setVisible(false);
                    jlabelHumedad.setVisible(true);
                }else {
                    jlabelHumedad.setVisible(false);
                    jlabelTemperatura.setVisible(true);

                }
            }
        });
        jlabelTabla.add(jlabelOpCambiar);
    }


    public void createJTable() {
        table = new JTable(10, 4);

        table.setRowHeight(30);

        table.setBounds(5, 70, 990, 480);
        table.setTableHeader(null);

        class MyRenderer extends DefaultTableCellRenderer {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.decode("#FFFFFF") : Color.decode("#F5F3F3"));
                if (isSelected) {
                    c.setBackground(Color.decode("#c7c3c3"));
                }
                setHorizontalAlignment(CENTER);
                return c;
            }

            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(alignment);
            }
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(182);
        table.getColumnModel().getColumn(1).setPreferredWidth(238);
        table.getColumnModel().getColumn(2).setPreferredWidth(279);
        table.getColumnModel().getColumn(3).setPreferredWidth(295);


        table.setDefaultRenderer(table.getColumnClass(0), new MyRenderer());
        table.setShowGrid(false);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 70, 990, 393);
        scrollPane.setViewportView(table);

        jlabelTabla.add(scrollPane);


        DefaultTableModel tabla1 = (DefaultTableModel) table.getModel();
        tabla1.setRowCount(0);
        for (int i = 0; i < 28; i++) {
            tabla1.addRow(new Object[]{i, "2020-12-12", "12:12:12", "12/12"});
        }

    }


    public static void main(String[] args) {
        new ViewRegistro();
    }
}
