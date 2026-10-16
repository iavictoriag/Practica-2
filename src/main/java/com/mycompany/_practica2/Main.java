package com.mycompany._practica2;

import javax.swing.JOptionPane;

/**
 * Clase principal del sistema Quetzal Space Defender.
 * @author Victoria Yannarett García Gómez (202502718)
 */
public class Main {
    
    // Arreglo simple para guardar los pilotos
    private static Piloto[] listaPilotos = new Piloto[50];
    private static int totalPilotos = 0;
    public static Piloto pilotoActual = null;
    
    public static void main(String[] args) {
        int opcion = 0;
        
        do {
            String menu = "=== QUETZAL SPACE DEFENDER ===\n" +
                          "1. Crear o seleccionar piloto\n" +
                          "2. Jugar\n" +
                          "3. Ver puntajes\n" +
                          "4. Salir\n\n" +
                          "Elige una opción:";
        
            String entrada = JOptionPane.showInputDialog(null, menu);
            if (entrada == null) break; // Si cancela, sale
            if (entrada.equals("")) continue;
            
            opcion = Integer.parseInt(entrada);
            
            switch (opcion) {
                case 1:
                    gestionarPiloto();
                    break;
                case 2:
                    if (pilotoActual == null) {
                        JOptionPane.showMessageDialog(null, "¡Primero debes crear o seleccionar un piloto!");
                    } else {
                        iniciarJuego();
                    }
                    break;
                case 3:
                    mostrarPuntajes();
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "¡Saliendo del juego!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
                    break;
            }     
        } while (opcion != 4);
    }
    
    //  buscar o crear el piloto
    public static void gestionarPiloto() {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del piloto:");
        
        if (nombre == null || nombre.trim().isEmpty()) return;
        nombre = nombre.trim();
        
        // Buscar si ya existe
        for (int i = 0; i < totalPilotos; i++) {
            if (listaPilotos[i].getNombre().equalsIgnoreCase(nombre)) {
                pilotoActual = listaPilotos[i];
                JOptionPane.showMessageDialog(null, "¡Piloto seleccionado: " + pilotoActual.getNombre() + "!");
                return; // Termina aquí porque ya lo encontró
            }
        }
        
        // Si no existe, preguntar dificultad y crearlo
        if (totalPilotos < listaPilotos.length) {
            String[] dificultades = {"Fácil", "Normal", "Difícil"};
            String dificultad = (String) JOptionPane.showInputDialog(
                null, "Selecciona la dificultad:", "Dificultad", 
                JOptionPane.QUESTION_MESSAGE, null, dificultades, dificultades[1]);
            
            if (dificultad == null) return; // Si cancela la dificultad
            
            // Creamos el nuevo piloto
            Piloto nuevo = new Piloto(nombre, dificultad);
            listaPilotos[totalPilotos] = nuevo;
            totalPilotos++;
            pilotoActual = nuevo;
            
            JOptionPane.showMessageDialog(null, "¡Piloto creado!\nNave: " + nuevo.getTipoNave() + "\nDificultad: " + dificultad);
        } else {
            JOptionPane.showMessageDialog(null, "El arreglo de pilotos está lleno.");
        }
    }

    public static void iniciarJuego() {
        JuegoVentana ventana = new JuegoVentana();
        ventana.setVisible(true);
    }
      
    public static void mostrarPuntajes() {
        if (totalPilotos == 0) {
            JOptionPane.showMessageDialog(null, "No hay pilotos registrados.");
            return;
        }
        
        String reporte = "=== REPORTE DE PILOTOS ===\n\n";
        for (int i = 0; i < totalPilotos; i++) {
            reporte += (i + 1) + ". " + listaPilotos[i].getNombre() + " - Récord: " + listaPilotos[i].getPuntajeMaximo() + " pts\n";
        }
        
        JOptionPane.showMessageDialog(null, reporte);
        //html
        ManejoArchivos manejador = new ManejoArchivos();
        manejador.crearReporteHTML();
    }  
}