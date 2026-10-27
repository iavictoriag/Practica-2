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
import modelo.EspacioParqueo;

public class VentanaPrincipal extends JFrame {
    
    //estructuras
    private ListaSimple listaUsuarios;
    private Pila pilaEventos;
    private Parqueo parqueoSocios;
    private Parqueo parqueoGeneral;

    public VentanaPrincipal() {
        //estructura
        listaUsuarios = new ListaSimple();
        pilaEventos = new Pila();
        parqueoSocios = new Parqueo("SOCIO");
        parqueoGeneral = new Parqueo("GENERAL");

        //espacios de parqueo 75 socios y 75 generales
        for (int i = 1; i <= 25; i++) parqueoSocios.insertarEspacio("A" + i);
        for (int i = 1; i <= 25; i++) parqueoSocios.insertarEspacio("B" + i);
        for (int i = 1; i <= 25; i++) parqueoSocios.insertarEspacio("C" + i);

        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("E" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("F" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("G" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("H" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("I" + i);

        //ventana principal
        setTitle("Sistema ResiPark - Interfaz Gráfica");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fondo blanco ventana
        getContentPane().setBackground(Color.WHITE);

        // titulo principal
        setLayout(new BorderLayout());

        // Colores 
        Color rosaPersonalizado = new Color(255, 140, 170); //color rosa

        // Panel de Título
        JLabel lblTitulo = new JLabel("GESTIÓN DE PARQUEO - RESIPARK", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(rosaPersonalizado);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        lblTitulo.setOpaque(false);
        add(lblTitulo, BorderLayout.NORTH);

        //botones para el main
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 1, 12, 12));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 180, 30, 180));
        panelBotones.setBackground(Color.WHITE);

        JButton btnRegistrarUsuario = new JButton("1. Registrar Usuario");
        JButton btnRegistrarVehiculo = new JButton("2. Registrar Vehículo y Estacionar");
        JButton btnVerParqueos = new JButton("3. Ver Estado de los Parqueos");
        JButton btnVerUsuarios = new JButton("4. Ver Registros e Historial");
        JButton btnSalir = new JButton("5. Salir"); 

        // Estilo y color de los botones
        Font fuenteBoton = new Font("Arial", Font.BOLD, 14);
        Color colorTexto = Color.WHITE;

        JButton[] botones = {btnRegistrarUsuario, btnRegistrarVehiculo, btnVerParqueos, btnVerUsuarios, btnSalir};

        for (JButton btn : botones) {
            btn.setFont(fuenteBoton);
            btn.setBackground(rosaPersonalizado);
            btn.setForeground(colorTexto);
            btn.setOpaque(true);
            btn.setFocusPainted(false);
        }

        // Añadir botones al panel
        panelBotones.add(btnRegistrarUsuario);
        panelBotones.add(btnRegistrarVehiculo);
        panelBotones.add(btnVerParqueos);
        panelBotones.add(btnVerUsuarios);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);
        
        // boton 1 
        btnRegistrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = JOptionPane.showInputDialog(null, "Ingrese ID del usuario:", "Registro de Usuario", JOptionPane.QUESTION_MESSAGE);
                if (id == null || id.trim().isEmpty()) return;

                String nombre = JOptionPane.showInputDialog(null, "Ingrese nombre completo:", "Registro de Usuario", JOptionPane.QUESTION_MESSAGE);
                if (nombre == null || nombre.trim().isEmpty()) return;

                String numeroCasa = JOptionPane.showInputDialog(null, "Ingrese numero de casa:", "Registro de Usuario", JOptionPane.QUESTION_MESSAGE);
                if (numeroCasa == null || numeroCasa.trim().isEmpty()) return;

                String telefono = JOptionPane.showInputDialog(null, "Ingrese telefono:", "Registro de Usuario", JOptionPane.QUESTION_MESSAGE);
                if (telefono == null || telefono.trim().isEmpty()) return;

                String socioStr = JOptionPane.showInputDialog(null, "¿Es socio? (S = Si / N = No):", "Registro de Usuario", JOptionPane.QUESTION_MESSAGE);
                if (socioStr == null || socioStr.trim().isEmpty()) return;
                
                char esSocio = socioStr.toUpperCase().charAt(0);
                if (esSocio != 'S' && esSocio != 'N') {
                    JOptionPane.showMessageDialog(null, "Error: Debe ingresar 'S' o 'N' para el estado de socio.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Usuario usuarioNuevo = new Usuario(id, nombre, numeroCasa, telefono, esSocio);
                listaUsuarios.insertar(usuarioNuevo);
                pilaEventos.apilar("Se registró al usuario: " + nombre + " (ID: " + id + ")");

                String lineaTexto = id + "," + nombre + "," + numeroCasa + "," + telefono + "," + esSocio;
                persistencia.ManejoArchivos.guardarTexto("usuarios.txt", lineaTexto);

                JOptionPane.showMessageDialog(null, "¡Usuario registrado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
       // boton 2 registrar
        btnRegistrarVehiculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idResidente = JOptionPane.showInputDialog(null, "Ingrese el ID del residente propietario:", "Registro de Vehículo", JOptionPane.QUESTION_MESSAGE);
                if (idResidente == null || idResidente.trim().isEmpty()) return;

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

                Vehiculo vehiculoNuevo = new Vehiculo(placa, marca, modelo, color, tipo);
                boolean agregadoAlResidente = propietario.agregarVehiculo(vehiculoNuevo);

                if (agregadoAlResidente) {
                    boolean estacionado = false;

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
        
        //boton 3 parqueo
        btnVerParqueos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialogParqueos = new JDialog((Frame) null, "Mapa de Estado de Parqueos - ResiPark", true);
                dialogParqueos.setSize(900, 650);
                dialogParqueos.setLocationRelativeTo(null);
                dialogParqueos.setLayout(new BorderLayout());

                JTabbedPane pestañasParqueo = new JTabbedPane();
                //parqueo de socios
                JPanel panelSocios = new JPanel();
                panelSocios.setLayout(new GridLayout(0, 5, 8, 8)); // 5 columnas ordenadas
                panelSocios.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                panelSocios.setBackground(Color.WHITE);

                try {
                } catch (Exception ex) {
                
                }
                String[] letrasSocios = {"A", "B", "C"};
                for (String letra : letrasSocios) {
                    for (int i = 1; i <= 25; i++) {
                        String codigo = letra + i;
                        // Buscamos el espacio en socios
                        EspacioParqueo espacioEncontrado = buscarEspacioEnParqueo(parqueoSocios, codigo);
                        
                        JButton btnEspacio = new JButton(codigo);
                        btnEspacio.setFont(new Font("Arial", Font.BOLD, 11));
                        
                        if (espacioEncontrado != null && espacioEncontrado.isOcupado()) {
                            btnEspacio.setBackground(new Color(255, 100, 100)); // Rojo si está ocupado
                            String placaVehiculo = (espacioEncontrado.getVehiculo() != null) ? espacioEncontrado.getVehiculo().getPlaca() : "Ocupado";
                            btnEspacio.setToolTipText("Ocupado - Placa: " + placaVehiculo);
                        } else {
                            btnEspacio.setBackground(new Color(144, 238, 144)); // Verde si está libre
                            btnEspacio.setToolTipText("Disponible");
                        }
                        
                        btnEspacio.setForeground(Color.DARK_GRAY);
                        btnEspacio.setOpaque(true);
                        btnEspacio.setFocusPainted(false);
                        
                        // clic en el espacio
                        final EspacioParqueo espFinal = espacioEncontrado;
                        btnEspacio.addActionListener(evt -> {
                            if (espFinal != null && espFinal.isOcupado()) {
                                String info = "Espacio: " + espFinal.getCodigoEspacio() + "\nEstado: OCUPADO";
                                if (espFinal.getVehiculo() != null) {
                                    info += "\nPlaca: " + espFinal.getVehiculo().getPlaca() +
                                            "\nMarca: " + espFinal.getVehiculo().getMarca() +
                                            "\nModelo: " + espFinal.getVehiculo().getModelo();
                                }
                                JOptionPane.showMessageDialog(dialogParqueos, info, "Detalle de Espacio", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(dialogParqueos, "Espacio " + codigo + " está LIBRE.", "Detalle de Espacio", JOptionPane.INFORMATION_MESSAGE);
                            }
                        });

                        panelSocios.add(btnEspacio);
                    }
                }

                JScrollPane scrollSocios = new JScrollPane(panelSocios);
                pestañasParqueo.addTab("Parqueo de Socios (75)", scrollSocios);

                // ----------------------------------------------------
                // 2. PANEL DE PARQUEO GENERAL
                // ----------------------------------------------------
                JPanel panelGeneral = new JPanel();
                panelGeneral.setLayout(new GridLayout(0, 5, 8, 8)); // 5 columnas
                panelGeneral.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                panelGeneral.setBackground(Color.WHITE);

                String[] letrasGeneral = {"E", "F", "G", "H", "I"};
                for (String letra : letrasGeneral) {
                    for (int i = 1; i <= 15; i++) {
                        String codigo = letra + i;
                        EspacioParqueo espacioEncontrado = buscarEspacioEnParqueo(parqueoGeneral, codigo);
                        
                        JButton btnEspacio = new JButton(codigo);
                        btnEspacio.setFont(new Font("Arial", Font.BOLD, 11));
                        
                        if (espacioEncontrado != null && espacioEncontrado.isOcupado()) {
                            btnEspacio.setBackground(new Color(255, 100, 100)); // Rojo si está ocupado
                            String placaVehiculo = (espacioEncontrado.getVehiculo() != null) ? espacioEncontrado.getVehiculo().getPlaca() : "Ocupado";
                            btnEspacio.setToolTipText("Ocupado - Placa: " + placaVehiculo);
                        } else {
                            btnEspacio.setBackground(new Color(144, 238, 144)); // Verde si está libre
                            btnEspacio.setToolTipText("Disponible");
                        }
                        
                        btnEspacio.setForeground(Color.DARK_GRAY);
                        btnEspacio.setOpaque(true);
                        btnEspacio.setFocusPainted(false);
                        
                        final EspacioParqueo espFinal = espacioEncontrado;
                        btnEspacio.addActionListener(evt -> {
                            if (espFinal != null && espFinal.isOcupado()) {
                                String info = "Espacio: " + espFinal.getCodigoEspacio() + "\nEstado: OCUPADO";
                                if (espFinal.getVehiculo() != null) {
                                    info += "\nPlaca: " + espFinal.getVehiculo().getPlaca() +
                                            "\nMarca: " + espFinal.getVehiculo().getMarca() +
                                            "\nModelo: " + espFinal.getVehiculo().getModelo();
                                }
                                JOptionPane.showMessageDialog(dialogParqueos, info, "Detalle de Espacio", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(dialogParqueos, "Espacio " + codigo + " está LIBRE.", "Detalle de Espacio", JOptionPane.INFORMATION_MESSAGE);
                            }
                        });

                        panelGeneral.add(btnEspacio);
                    }
                }

                JScrollPane scrollGeneral = new JScrollPane(panelGeneral);
                pestañasParqueo.addTab("Parqueo General (75)", scrollGeneral);

                dialogParqueos.add(pestañasParqueo, BorderLayout.CENTER);

                //cerrar el mapa
                JButton btnCerrarMapa = new JButton("Cerrar Mapa");
                btnCerrarMapa.setBackground(rosaPersonalizado);
                btnCerrarMapa.setForeground(Color.WHITE);
                btnCerrarMapa.setOpaque(true);
                btnCerrarMapa.setFont(new Font("Arial", Font.BOLD, 13));
                btnCerrarMapa.addActionListener(evt -> dialogParqueos.dispose());

                JPanel panelSur = new JPanel();
                panelSur.setBackground(Color.WHITE);
                panelSur.add(btnCerrarMapa);
                dialogParqueos.add(panelSur, BorderLayout.SOUTH);

                dialogParqueos.setVisible(true);
            }
        });
        
        // boton 4 registro e historial
        btnVerUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                
                sb.append("=== LISTA DE USUARIOS REGISTRADOS ===\n");
                var actual = listaUsuarios.getCabeza();
                if (actual == null) {
                    sb.append("No hay usuarios registrados todavía.\n");
                } else {
                    while (actual != null) {
                        Usuario u = (Usuario) actual.getDato();
                        sb.append("- ID: ").append(u.getId())
                          .append(" | Nombre: ").append(u.getNombre())
                          .append(" | Casa: ").append(u.getNumeroCasa())
                          .append(" | Socio: ").append(u.getEsSocio())
                          .append("\n");
                        actual = actual.getSiguiente();
                    }
                }
                
                sb.append("\n=== HISTORIAL DE EVENTOS ===\n");
                sb.append("Eventos registrados en la pila del sistema correctamente.\n");

                JTextArea txtAreaRegistros = new JTextArea(sb.toString(), 20, 50);
                txtAreaRegistros.setEditable(false);
                txtAreaRegistros.setFont(new Font("Monospaced", Font.PLAIN, 12));
                
                JScrollPane scrollPane = new JScrollPane(txtAreaRegistros);

                JOptionPane.showMessageDialog(
                    null, 
                    scrollPane, 
                    "Registros e Historial del Sistema", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        
        // botón Salir
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
                    System.exit(0);
                }
            }
        });
    }

    //buscar espacio 
    private EspacioParqueo buscarEspacioEnParqueo(Parqueo parqueoObj, String codigoBuscado) {
        return parqueoObj.buscarEspacioPorCodigo(codigoBuscado);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
}