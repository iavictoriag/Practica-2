/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author vicky
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import estructuras.ListaSimple;
import estructuras.Parqueo;
import estructuras.Pila;
import modelo.Usuario;
import modelo.Vehiculo;

public class VentanaPrincipal extends JFrame {
    
    //estructuras
    private ListaSimple listaUsuarios;
    private Pila pilaEventos;
    private Parqueo parqueoSocios;
    private Parqueo parqueoGeneral;

    public VentanaPrincipal() {
        // Inicializar estructuras
        listaUsuarios = new ListaSimple();
        pilaEventos = new Pila();
        parqueoSocios = new Parqueo("SOCIO");
        parqueoGeneral = new Parqueo("GENERAL");

        // Inicializar espacios de parqueo 75 socios y 75 generales
        for (int i = 1; i <= 25; i++) parqueoSocios.insertarEspacio("A" + i);
        for (int i = 1; i <= 25; i++) parqueoSocios.insertarEspacio("B" + i);
        for (int i = 1; i <= 25; i++) parqueoSocios.insertarEspacio("C" + i);

        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("E" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("F" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("G" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("H" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("I" + i);

        // Configuración de la ventana principal
        setTitle("Sistema ResiPark - Interfaz Gráfica");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout principal
        setLayout(new BorderLayout());

        // Panel de Título 
        JLabel lblTitulo = new JLabel("GESTIÓN DE PARQUEO - RESIPARK", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel Central con Botones para el main
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 1, 12, 12));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 180, 30, 180));

        JButton btnRegistrarUsuario = new JButton("1. Registrar Usuario");
        JButton btnRegistrarVehiculo = new JButton("2. Registrar Vehículo y Estacionar");
        JButton btnVerParqueos = new JButton("3. Ver Estado de los Parqueos");
        JButton btnVerUsuarios = new JButton("4. Ver Registros e Historial");
        JButton btnSalir = new JButton("5. Salir"); 

        // Estilo de botones
        Font fuenteBoton = new Font("Arial", Font.PLAIN, 14);
        btnRegistrarUsuario.setFont(fuenteBoton);
        btnRegistrarVehiculo.setFont(fuenteBoton);
        btnVerParqueos.setFont(fuenteBoton);
        btnVerUsuarios.setFont(fuenteBoton);
        btnSalir.setFont(fuenteBoton);

        // Añadir botones al panel
        panelBotones.add(btnRegistrarUsuario);
        panelBotones.add(btnRegistrarVehiculo);
        panelBotones.add(btnVerParqueos);
        panelBotones.add(btnVerUsuarios);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);
        //acciones de los botones 
        
        btnRegistrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Aquí abrirás el formulario para Registrar Usuario.");
            }
        });

        btnRegistrarVehiculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Aquí abrirás el formulario para Registrar y Estacionar Vehículo.");
            }
        });

        btnVerParqueos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Aquí mostrarás el estado gráfico de los parqueos.");
            }
        });

        btnVerUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Aquí mostrarás la lista de usuarios y la pila de eventos.");
            }
        });

        //botón Salir
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(
                    null, 
                    "¿Estás segura de que deseas salir del sistema ResiPark?", 
                    "Salir", 
                    JOptionPane.YES_NO_OPTION
                );
                if (confirmacion == JOptionPane.YES_OPTION) {
                    System.exit(0); // Cierra por completo
                }
            }
        });
    }

    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
}