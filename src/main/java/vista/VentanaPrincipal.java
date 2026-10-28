/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to edit this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author vicky
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import estructuras.ListaSimple;
import estructuras.Parqueo;
import estructuras.Pila;
import estructuras.Cola;
import hilos.GaritaEntrada;
import hilos.GaritaSalida;
import modelo.Usuario;
import modelo.Vehiculo;
import modelo.Visitante;
import modelo.EspacioParqueo;

public class VentanaPrincipal extends JFrame {
    
    private ListaSimple listaUsuarios;
    private Pila pilaEventos;
    private Parqueo parqueoSocios;
    private Parqueo parqueoGeneral;

    private Cola colaEntrada;
    private GaritaEntrada garita1;
    private GaritaEntrada garita2;
    private GaritaSalida garitaSalida;

    private JTabbedPane pestañasParqueo;
    private Timer temporizadorRefresco;

    public VentanaPrincipal() {
        listaUsuarios = new ListaSimple();
        pilaEventos = new Pila();
        parqueoSocios = new Parqueo("SOCIO");
        parqueoGeneral = new Parqueo("GENERAL");

        //usuarios.txt con separador
        try {
            File archivo = new File("usuarios.txt");
            if (archivo.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(archivo));
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split("\\|");
                    if (datos.length == 5) {
                        Usuario u = new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4].charAt(0));
                        listaUsuarios.insertar(u);
                    }
                }
                br.close();
            }
        } catch (Exception e) {
            System.out.println(">>> [ERROR] Leer usuarios: " + e.getMessage());
        }

        // vehiculos.txt con separador |
        try {
            File archivoV = new File("vehiculos.txt");
            if (archivoV.exists()) {
                BufferedReader brV = new BufferedReader(new FileReader(archivoV));
                String lineaV;
                while ((lineaV = brV.readLine()) != null) {
                    String[] datosV = lineaV.split("\\|");
                    if (datosV.length >= 7) {
                        Vehiculo vCargado = new Vehiculo(datosV[1], datosV[2], datosV[3], datosV[4], datosV[5]);
                        vCargado.setEstado(datosV[6]);

                        Usuario prop = null;
                        var actualU = listaUsuarios.getCabeza();
                        while (actualU != null) {
                            Usuario u = (Usuario) actualU.getDato();
                            if (u.getId().equalsIgnoreCase(datosV[0])) {
                                prop = u;
                                break;
                            }
                            actualU = actualU.getSiguiente();
                        }

                        if (prop != null) {
                            prop.agregarVehiculo(vCargado);
                            if (datosV[6].equalsIgnoreCase("ESTACIONADO")) {
                                if (prop.getEsSocio() == 'S') {
                                    parqueoSocios.estacionarVehiculo(vCargado);
                                } else {
                                    parqueoGeneral.estacionarVehiculo(vCargado);
                                }
                            }
                        }
                    }
                }
                brV.close();
            }
        } catch (Exception e) {
            System.out.println(">>> [ERROR] Leer vehiculos: " + e.getMessage());
        }

        // espacios de parqueo
        for (int i = 1; i <= 25; i++) parqueoSocios.insertarEspacio("A" + i);
        for (int i = 1; i <= 25; i++) parqueoSocios.insertarEspacio("B" + i);
        for (int i = 1; i <= 25; i++) parqueoSocios.insertarEspacio("C" + i);

        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("E" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("F" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("G" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("H" + i);
        for (int i = 1; i <= 15; i++) parqueoGeneral.insertarEspacio("I" + i);

        // Hilos
        this.colaEntrada = new Cola();
        this.garita1 = new GaritaEntrada(colaEntrada, parqueoSocios, parqueoGeneral, pilaEventos);
        this.garita2 = new GaritaEntrada(colaEntrada, parqueoSocios, parqueoGeneral, pilaEventos);
        this.garitaSalida = new GaritaSalida(parqueoSocios, parqueoGeneral, pilaEventos, listaUsuarios);

        garita1.start();
        garita2.start();
        garitaSalida.start();

        setTitle("Sistema ResiPark - Dashboard de Control");
        setSize(1250, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Color colorFondoOscuro = new Color(216, 22, 99);
        Color colorRosaBotones = new Color(255, 140, 170);

        JLabel lblTitulo = new JLabel("Parking Spot Manager - ResiPark", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(colorFondoOscuro);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelMenuIzquierdo = new JPanel();
        panelMenuIzquierdo.setLayout(new GridLayout(6, 1, 10, 10));
        panelMenuIzquierdo.setBackground(colorFondoOscuro);
        panelMenuIzquierdo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelMenuIzquierdo.setPreferredSize(new Dimension(260, 0));

        JButton btnRegistrarUsuario = new JButton("1. Registrar Usuario");
        JButton btnRegistrarVehiculo = new JButton("2. Registrar Vehículo");
        JButton btnRegistrarVisitante = new JButton("3. Registrar Visitante");
        JButton btnRetirarVehiculo = new JButton("4. Retirar Vehículo");
        JButton btnVerUsuarios = new JButton("5. Ver Registros e Historial");
        JButton btnSalir = new JButton("6. Salir");

        JButton[] botonesMenu = {btnRegistrarUsuario, btnRegistrarVehiculo, btnRegistrarVisitante, btnRetirarVehiculo, btnVerUsuarios, btnSalir};
        for (JButton btn : botonesMenu) {
            btn.setFont(new Font("Arial", Font.BOLD, 13));
            btn.setBackground(colorRosaBotones);
            btn.setForeground(Color.WHITE);
            btn.setOpaque(true);
            btn.setFocusPainted(false);
            panelMenuIzquierdo.add(btn);
        }

        add(panelMenuIzquierdo, BorderLayout.WEST);

        pestañasParqueo = new JTabbedPane();
        pestañasParqueo.setFont(new Font("Arial", Font.BOLD, 14));
        actualizarPanelParqueos();
        add(pestañasParqueo, BorderLayout.CENTER);

        temporizadorRefresco = new Timer(1500, e -> actualizarPanelParqueos());
        temporizadorRefresco.start();
        
        // 1. REGISTRAR USUARIO
        btnRegistrarUsuario.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(null, "Ingrese ID del usuario:", "Registro", JOptionPane.QUESTION_MESSAGE);
            if (id == null || id.trim().isEmpty()) return;

            String nombre = JOptionPane.showInputDialog(null, "Ingrese nombre completo:", "Registro", JOptionPane.QUESTION_MESSAGE);
            if (nombre == null || nombre.trim().isEmpty()) return;

            String numeroCasa = JOptionPane.showInputDialog(null, "Ingrese numero de casa:", "Registro", JOptionPane.QUESTION_MESSAGE);
            if (numeroCasa == null || numeroCasa.trim().isEmpty()) return;

            String telefono = JOptionPane.showInputDialog(null, "Ingrese telefono:", "Registro", JOptionPane.QUESTION_MESSAGE);
            if (telefono == null || telefono.trim().isEmpty()) return;

            String socioStr = JOptionPane.showInputDialog(null, "¿Es socio? (S = Si / N = No):", "Registro", JOptionPane.QUESTION_MESSAGE);
            if (socioStr == null || socioStr.trim().isEmpty()) return;
            
            char esSocio = socioStr.toUpperCase().charAt(0);
            if (esSocio != 'S' && esSocio != 'N') {
                JOptionPane.showMessageDialog(null, "Debe ingresar 'S' o 'N'.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuarioNuevo = new Usuario(id, nombre, numeroCasa, telefono, esSocio);
            listaUsuarios.insertar(usuarioNuevo);
            pilaEventos.apilar("Se registró al usuario: " + nombre);

            // Guardar usando el delimitador |
            persistencia.ManejoArchivos.guardarTexto("usuarios.txt", id + "|" + nombre + "|" + numeroCasa + "|" + telefono + "|" + esSocio);
            JOptionPane.showMessageDialog(null, "¡Usuario registrado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
           });
        
        // 2. REGISTRAR VEHÍCULO
        btnRegistrarVehiculo.addActionListener(e -> {
            String idResidente = JOptionPane.showInputDialog(null, "Ingrese el ID del residente propietario:", "Vehículo", JOptionPane.QUESTION_MESSAGE);
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
                JOptionPane.showMessageDialog(null, "No se encontró el residente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (propietario.getMisVehiculos().getTamanio() >= 3) {
                JOptionPane.showMessageDialog(null, "Límite máximo de 3 vehículos alcanzado.", "Límite", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String placa = JOptionPane.showInputDialog(null, "Ingrese la placa:", "Vehículo", JOptionPane.QUESTION_MESSAGE);
            if (placa == null || placa.trim().isEmpty()) return;

            if (existePlacaGlobal(listaUsuarios, placa)) {
                JOptionPane.showMessageDialog(null, "La placa ya está registrada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String marca = JOptionPane.showInputDialog(null, "Ingrese la marca:", "Vehículo", JOptionPane.QUESTION_MESSAGE);
            if (marca == null || marca.trim().isEmpty()) return;

            String modelo = JOptionPane.showInputDialog(null, "Ingrese el modelo:", "Vehículo", JOptionPane.QUESTION_MESSAGE);
            if (modelo == null || modelo.trim().isEmpty()) return;

            String color = JOptionPane.showInputDialog(null, "Ingrese el color:", "Vehículo", JOptionPane.QUESTION_MESSAGE);
            if (color == null || color.trim().isEmpty()) return;

            String tipo = JOptionPane.showInputDialog(null, "Ingrese el tipo:", "Vehículo", JOptionPane.QUESTION_MESSAGE);
            if (tipo == null || tipo.trim().isEmpty()) return;

            Vehiculo vehiculoNuevo = new Vehiculo(placa, marca, modelo, color, tipo);
            if (propietario.agregarVehiculo(vehiculoNuevo)) {
                pilaEventos.apilar("Se encoló vehículo placa: " + placa);
                // Guardar usando el delimitador |
                persistencia.ManejoArchivos.guardarTexto("vehiculos.txt", idResidente + "|" + placa + "|" + marca + "|" + modelo + "|" + color + "|" + tipo + "|" + vehiculoNuevo.getEstado());
                colaEntrada.encolar(vehiculoNuevo);
                JOptionPane.showMessageDialog(null, "¡Vehículo encolado para entrada!", "En Cola", JOptionPane.INFORMATION_MESSAGE);
                actualizarPanelParqueos();
            }
        });

        // 3. REGISTRAR VISITANTE
        btnRegistrarVisitante.addActionListener(e -> {
            String nombreVisitante = JOptionPane.showInputDialog(null, "Nombre del visitante:", "Visitante", JOptionPane.QUESTION_MESSAGE);
            if (nombreVisitante == null || nombreVisitante.trim().isEmpty()) return;

            String idResidente = JOptionPane.showInputDialog(null, "ID del residente a visitar:", "Visitante", JOptionPane.QUESTION_MESSAGE);
            if (idResidente == null || idResidente.trim().isEmpty()) return;

            Usuario propietarioDestino = null;
            var actual = listaUsuarios.getCabeza();
            while (actual != null) {
                Usuario u = (Usuario) actual.getDato();
                if (u.getId().equalsIgnoreCase(idResidente)) {
                    propietarioDestino = u;
                    break;
                }
                actual = actual.getSiguiente();
            }

            if (propietarioDestino == null) {
                JOptionPane.showMessageDialog(null, "El residente no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String placa = JOptionPane.showInputDialog(null, "Placa del visitante:", "Visitante", JOptionPane.QUESTION_MESSAGE);
            if (placa == null || placa.trim().isEmpty()) return;

            if (existePlacaGlobal(listaUsuarios, placa)) {
                JOptionPane.showMessageDialog(null, "La placa ya está registrada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String marca = JOptionPane.showInputDialog(null, "Marca:", "Visitante", JOptionPane.QUESTION_MESSAGE);
            if (marca == null || marca.trim().isEmpty()) return;
            String modelo = JOptionPane.showInputDialog(null, "Modelo:", "Visitante", JOptionPane.QUESTION_MESSAGE);
            if (modelo == null || modelo.trim().isEmpty()) return;
            String color = JOptionPane.showInputDialog(null, "Color:", "Visitante", JOptionPane.QUESTION_MESSAGE);
            if (color == null || color.trim().isEmpty()) return;
            String tipo = JOptionPane.showInputDialog(null, "Tipo:", "Visitante", JOptionPane.QUESTION_MESSAGE);
            if (tipo == null || tipo.trim().isEmpty()) return;

            Visitante nuevoVisitante = new Visitante(nombreVisitante, placa, idResidente, marca, modelo, color, tipo);
            if (parqueoGeneral.estacionarVehiculo(nuevoVisitante.getVehiculoAsociado())) {
                pilaEventos.apilar("Ingresó visitante: " + nombreVisitante + " (Placa: " + placa + ")");
                JOptionPane.showMessageDialog(null, "¡Visitante estacionado en Zona General!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarPanelParqueos();
            } else {
                JOptionPane.showMessageDialog(null, "Parqueo General lleno.", "Lleno", JOptionPane.WARNING_MESSAGE);
            }
        });

        // 4. RETIRAR VEHÍCULO
        btnRetirarVehiculo.addActionListener(e -> {
            String placaSalida = JOptionPane.showInputDialog(null, "Ingrese la placa del vehículo a retirar:", "Garita de Salida", JOptionPane.QUESTION_MESSAGE);
            if (placaSalida == null || placaSalida.trim().isEmpty()) return;

            garitaSalida.registrarSalida(placaSalida.trim());
            
            // Pausa breve de 200ms para darle tiempo al hilo de procesar la salida y liberar el espacio
            try {
                Thread.sleep(300);
            } catch (Exception ex) {}

            // Forzar actualización inmediata del panel visual de parqueos
            actualizarPanelParqueos();

            JOptionPane.showMessageDialog(null, "Solicitud de salida procesada para la placa: " + placaSalida, "Salida", JOptionPane.INFORMATION_MESSAGE);
        });
        
        // 5. VER REGISTROS 
        btnVerUsuarios.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("=== LISTA DE USUARIOS ===\n");
            var actual = listaUsuarios.getCabeza();
            if (actual == null) {
                sb.append("No hay usuarios registrados.\n");
            } else {
                while (actual != null) {
                    Usuario u = (Usuario) actual.getDato();
                    sb.append("- ID: ").append(u.getId()).append(" | Nombre: ").append(u.getNombre())
                      .append(" | Casa: ").append(u.getNumeroCasa()).append(" | Socio: ").append(u.getEsSocio()).append("\n");
                    actual = actual.getSiguiente();
                }
            }
            
            sb.append("\n=== HISTORIAL DE EVENTOS ===\n");
            if (pilaEventos.estaVacia()) sb.append("Historial vacío.\n");
            else sb.append("Total de eventos: ").append(pilaEventos.getTamanio()).append("\n");

            JTextArea txtArea = new JTextArea(sb.toString(), 20, 60);
            txtArea.setEditable(false);
            txtArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            
            Object[] opciones = {"Cerrar", "Modificar Residente", "Eliminar Residente", "Limpiar Historial"};
            int seleccion = JOptionPane.showOptionDialog(null, new JScrollPane(txtArea), "Gestión", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

            if (seleccion == 1) { // MODIFICAR
                String idBuscado = JOptionPane.showInputDialog(null, "ID del residente a modificar:");
                if (idBuscado == null || idBuscado.trim().isEmpty()) return;

                Usuario resMod = null;
                var nodoU = listaUsuarios.getCabeza();
                while (nodoU != null) {
                    Usuario u = (Usuario) nodoU.getDato();
                    if (u.getId().equalsIgnoreCase(idBuscado)) { resMod = u; break; }
                    nodoU = nodoU.getSiguiente();
                }

                if (resMod == null) { JOptionPane.showMessageDialog(null, "No encontrado."); return; }

                String nuevoNombre = JOptionPane.showInputDialog(null, "Nuevo nombre:", resMod.getNombre());
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) resMod.setNombre(nuevoNombre);
                String nuevaCasa = JOptionPane.showInputDialog(null, "Nueva casa:", resMod.getNumeroCasa());
                if (nuevaCasa != null && !nuevaCasa.trim().isEmpty()) resMod.setNumeroCasa(nuevaCasa);
                String nuevoTel = JOptionPane.showInputDialog(null, "Nuevo teléfono:", resMod.getTelefono());
                if (nuevoTel != null && !nuevoTel.trim().isEmpty()) resMod.setTelefono(nuevoTel);

                reescribirArchivoUsuarios();
                pilaEventos.apilar("Modificado residente ID: " + idBuscado);
                JOptionPane.showMessageDialog(null, "¡Modificado con éxito!");
            } else if (seleccion == 2) { // ELIMINAR
                String idBuscado = JOptionPane.showInputDialog(null, "ID del residente a eliminar:");
                if (idBuscado == null || idBuscado.trim().isEmpty()) return;

                Usuario resDel = null;
                var nodoU = listaUsuarios.getCabeza();
                while (nodoU != null) {
                    Usuario u = (Usuario) nodoU.getDato();
                    if (u.getId().equalsIgnoreCase(idBuscado)) { resDel = u; break; }
                    nodoU = nodoU.getSiguiente();
                }

                if (resDel == null) { JOptionPane.showMessageDialog(null, "No encontrado."); return; }

                boolean tieneVehiculoAdentro = false;
                var nodoV = resDel.getMisVehiculos().getCabeza();
                while (nodoV != null) {
                    Vehiculo v = (Vehiculo) nodoV.getDato();
                    if ("ESTACIONADO".equalsIgnoreCase(v.getEstado())) { tieneVehiculoAdentro = true; break; }
                    nodoV = nodoV.getSiguiente();
                }

                if (tieneVehiculoAdentro) {
                    JOptionPane.showMessageDialog(null, "No se puede eliminar: tiene vehículos estacionados.", "Bloqueado", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(null, "¿Eliminar a " + resDel.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    ListaSimple nuevaLista = new ListaSimple();
                    var actualL = listaUsuarios.getCabeza();
                    while (actualL != null) {
                        Usuario u = (Usuario) actualL.getDato();
                        if (!u.getId().equalsIgnoreCase(idBuscado)) {
                            nuevaLista.insertar(u);
                        }
                        actualL = actualL.getSiguiente();
                    }
                    listaUsuarios = nuevaLista;

                    reescribirArchivoUsuarios();
                    pilaEventos.apilar("Eliminado residente ID: " + idBuscado);
                    JOptionPane.showMessageDialog(null, "¡Eliminado con éxito!");
                }
            } else if (seleccion == 3) { // LIMPIAR PILA
                pilaEventos.limpiar();
                JOptionPane.showMessageDialog(null, "Historial limpiado.");
            }
        });
        
        // 6. SALIR
        btnSalir.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (temporizadorRefresco != null) temporizadorRefresco.stop();
                if (garita1 != null) garita1.detener();
                if (garita2 != null) garita2.detener();
                if (garitaSalida != null) garitaSalida.detener();
                System.exit(0);
            }
        });
    }

    private void actualizarPanelParqueos() {
        int pestañaSeleccionada = pestañasParqueo.getSelectedIndex();
        pestañasParqueo.removeAll();

        JPanel panelSocios = new JPanel(new GridLayout(0, 5, 10, 10));
        panelSocios.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelSocios.setBackground(Color.WHITE);
        for (String letra : new String[]{"A", "B", "C"}) {
            for (int i = 1; i <= 25; i++) {
                panelSocios.add(crearBotonEspacio(parqueoSocios.buscarEspacioPorCodigo(letra + i), letra + i));
            }
        }
        pestañasParqueo.addTab("Parqueo de Socios (75)", new JScrollPane(panelSocios));

        JPanel panelGeneral = new JPanel(new GridLayout(0, 5, 10, 10));
        panelGeneral.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelGeneral.setBackground(Color.WHITE);
        for (String letra : new String[]{"E", "F", "G", "H", "I"}) {
            for (int i = 1; i <= 15; i++) {
                panelGeneral.add(crearBotonEspacio(parqueoGeneral.buscarEspacioPorCodigo(letra + i), letra + i));
            }
        }
        pestañasParqueo.addTab("Parqueo General (75)", new JScrollPane(panelGeneral));
        
        if (pestañaSeleccionada >= 0 && pestañaSeleccionada < pestañasParqueo.getTabCount()) {
            pestañasParqueo.setSelectedIndex(pestañaSeleccionada);
        }
        pestañasParqueo.revalidate();
        pestañasParqueo.repaint();
    }

    private JButton crearBotonEspacio(EspacioParqueo esp, String codigo) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout());
        btn.setOpaque(true);
        btn.setFocusPainted(false);

        JLabel lblInfo = new JLabel();
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblInfo.setFont(new Font("Arial", Font.BOLD, 12));

        if (esp != null && esp.isOcupado()) {
            btn.setBackground(new Color(255, 160, 160));
            String placa = (esp.getVehiculo() != null) ? esp.getVehiculo().getPlaca() : "N/D";
            lblInfo.setText("<html><center><b>" + codigo + "</b><br>" + placa + "</center></html>");
        } else {
            btn.setBackground(new Color(180, 255, 180));
            lblInfo.setText("<html><center><b>" + codigo + "</b></center></html>");
        }

        btn.add(lblInfo, BorderLayout.CENTER);
        btn.addActionListener(e -> {
            if (esp != null && esp.isOcupado() && esp.getVehiculo() != null) {
                JOptionPane.showMessageDialog(this, "Espacio: " + codigo + "\nPlaca: " + esp.getVehiculo().getPlaca(), "Detalles", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Espacio " + codigo + " está Vacante.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        return btn;
    }

    // usuarios.txt 
    private void reescribirArchivoUsuarios() {
        try {
            File archivo = new File("usuarios.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false));
            var actual = listaUsuarios.getCabeza();
            while (actual != null) {
                Usuario u = (Usuario) actual.getDato();
                bw.write(u.getId() + "|" + u.getNombre() + "|" + u.getNumeroCasa() + "|" + u.getTelefono() + "|" + u.getEsSocio());
                bw.newLine();
                actual = actual.getSiguiente();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println(">>> [ERROR] Reescribir usuarios: " + e.getMessage());
        }
    }

    public static boolean existePlacaGlobal(ListaSimple listaUsuarios, String placaBusqueda) {
        var nodoUsuario = listaUsuarios.getCabeza();
        while (nodoUsuario != null) {
            Usuario u = (Usuario) nodoUsuario.getDato();
            var nodoVehiculo = u.getMisVehiculos().getCabeza();
            while (nodoVehiculo != null) {
                Vehiculo v = (Vehiculo) nodoVehiculo.getDato();
                if (v.getPlaca().equalsIgnoreCase(placaBusqueda)) return true;
                nodoVehiculo = nodoVehiculo.getSiguiente();
            }
            nodoUsuario = nodoUsuario.getSiguiente();
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}