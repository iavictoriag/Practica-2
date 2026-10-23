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
        
        //boton 1 
        btnRegistrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//boton 1
                // Pedir ID
                String id = JOptionPane.showInputDialog(null, "Ingrese ID del usuario:", "Registro de Usuario", JOptionPane.QUESTION_MESSAGE);
                if (id == null || id.trim().isEmpty()) return; // Si cancela o esta vacío

                // Pedir Nombre
                String nombre = JOptionPane.showInputDialog(null, "Ingrese nombre completo:", "Registro de Usuario", JOptionPane.QUESTION_MESSAGE);
                if (nombre == null || nombre.trim().isEmpty()) return;

                // Pedir Número de Casa
                String numeroCasa = JOptionPane.showInputDialog(null, "Ingrese numero de casa:", "Registro de Usuario", JOptionPane.QUESTION_MESSAGE);
                if (numeroCasa == null || numeroCasa.trim().isEmpty()) return;

                // Pedir Teléfono
                String telefono = JOptionPane.showInputDialog(null, "Ingrese telefono:", "Registro de Usuario", JOptionPane.QUESTION_MESSAGE);
                if (telefono == null || telefono.trim().isEmpty()) return;

                // Pedir si es Socio
                String socioStr = JOptionPane.showInputDialog(null, "¿Es socio? (S = Si / N = No):", "Registro de Usuario", JOptionPane.QUESTION_MESSAGE);
                if (socioStr == null || socioStr.trim().isEmpty()) return;
                
                char esSocio = socioStr.toUpperCase().charAt(0);
                if (esSocio != 'S' && esSocio != 'N') {
                    JOptionPane.showMessageDialog(null, "Error: Debe ingresar 'S' o 'N' para el estado de socio.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear objeto Usuario y guardarlo en la estructura
                Usuario usuarioNuevo = new Usuario(id, nombre, numeroCasa, telefono, esSocio);
                listaUsuarios.insertar(usuarioNuevo);
                pilaEventos.apilar("Se registró al usuario: " + nombre + " (ID: " + id + ")");

                // Guardar en el archivo de texto (Persistencia)
                String lineaTexto = id + "," + nombre + "," + numeroCasa + "," + telefono + "," + esSocio;
                persistencia.ManejoArchivos.guardarTexto("usuarios.txt", lineaTexto);

                JOptionPane.showMessageDialog(null, "¡Usuario registrado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
       // ==========================================
        // 2. REGISTRAR VEHÍCULO Y ESTACIONAR
        // ==========================================
        btnRegistrarVehiculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Pedir ID del residente propietario
                String idResidente = JOptionPane.showInputDialog(null, "Ingrese el ID del residente propietario:", "Registro de Vehículo", JOptionPane.QUESTION_MESSAGE);
                if (idResidente == null || idResidente.trim().isEmpty()) return;

                // Buscar al residente en la lista
                Usuario propietario = null;
                var actual = listaUsuarios.getCabeza();
                while (actual != null) {
                    Usuario u = (Usuario) actual.getDato();
                    if (u.getId().equalsIgnoreCase(idResidente)) {
                        propietario = u;
                        break;
                    }
                    actual = actual.getSiguiente();
                }

                if (propietario == null) {
                    JOptionPane.showMessageDialog(null, "Error: No se encontró un residente con el ID ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //boton 2 datos del vehículo
                String placa = JOptionPane.showInputDialog(null, "Ingrese la placa del vehículo:", "Registro de Vehículo", JOptionPane.QUESTION_MESSAGE);
                if (placa == null || placa.trim().isEmpty()) return;

                String marca = JOptionPane.showInputDialog(null, "Ingrese la marca:", "Registro de Vehículo", JOptionPane.QUESTION_MESSAGE);
                if (marca == null || marca.trim().isEmpty()) return;

                String modelo = JOptionPane.showInputDialog(null, "Ingrese el modelo/línea:", "Registro de Vehículo", JOptionPane.QUESTION_MESSAGE);
                if (modelo == null || modelo.trim().isEmpty()) return;

                String color = JOptionPane.showInputDialog(null, "Ingrese el color:", "Registro de Vehículo", JOptionPane.QUESTION_MESSAGE);
                if (color == null || color.trim().isEmpty()) return;

                String tipo = JOptionPane.showInputDialog(null, "Ingrese el tipo de vehículo:", "Registro de Vehículo", JOptionPane.QUESTION_MESSAGE);
                if (tipo == null || tipo.trim().isEmpty()) return;

                // Crear Vehículo
                Vehiculo vehiculoNuevo = new Vehiculo(placa, marca, modelo, color, tipo);

                // Agregarlo al residente
                boolean agregadoAlResidente = propietario.agregarVehiculo(vehiculoNuevo);

                if (agregadoAlResidente) {
                    boolean estacionado = false;

                    // Asignar espacio según sea socio o general
                    if (propietario.getEsSocio() == 'S') {
                        estacionado = parqueoSocios.estacionarVehiculo(vehiculoNuevo);
                        if (!estacionado) {
                            estacionado = parqueoGeneral.estacionarVehiculo(vehiculoNuevo);
                        }
                    } else {
                        estacionado = parqueoGeneral.estacionarVehiculo(vehiculoNuevo);
                    }

                    if (estacionado) {
                        pilaEventos.apilar("Se estacionó el vehículo placa: " + placa);
                        
                        // Guardar en el archivo de texto (Persistencia)
                        String lineaVehiculo = idResidente + "," + placa + "," + marca + "," + modelo + "," + color + "," + tipo + "," + vehiculoNuevo.getEstado();
                        persistencia.ManejoArchivos.guardarTexto("vehiculos.txt", lineaVehiculo);

                        JOptionPane.showMessageDialog(null, "¡Vehículo registrado y estacionado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        pilaEventos.apilar("Rechazado vehículo placa: " + placa + " (Parqueo lleno)");
                        JOptionPane.showMessageDialog(null, "Aviso: El vehículo se registró al residente, pero el estacionamiento está completamente lleno.", "Parqueo Lleno", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        
        //boton 3 estado del parqueo
        btnVerParqueos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //panel para el estado de los parqueos
                JTextArea txtAreaEstado = new JTextArea(20, 40);
                txtAreaEstado.setEditable(false);
                txtAreaEstado.setFont(new Font("Monospaced", Font.PLAIN, 12));

                StringBuilder sb = new StringBuilder();
                sb.append("=== ESTADO DEL PARQUEO DE SOCIOS ===\n");
                // Nota: Aquí recorremos o simulamos la vista del parqueo de socios
                sb.append("Espacios A1-A25, B1-B25, C1-C25\n");
                sb.append("Revisa la consola o el sistema interno para detalles de ocupación.\n\n");
                
                sb.append("=== ESTADO DEL PARQUEO GENERAL ===\n");
                sb.append("Espacios E1-E15, F1-F15, G1-G15, H1-H15, I1-I15\n");
                
                //esumen del Parqueo.
                txtAreaEstado.setText(sb.toString());

                JScrollPane scrollPane = new JScrollPane(txtAreaEstado);
                
                JOptionPane.showMessageDialog(
                    null, 
                    scrollPane, 
                    "Estado Actual de los Parqueos", 
                    JOptionPane.INFORMATION_MESSAGE
                );
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