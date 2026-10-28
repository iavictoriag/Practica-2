/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to edit this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hilos;

import estructuras.Cola;
import estructuras.Parqueo;
import estructuras.Pila;
import estructuras.ListaSimple;
import modelo.Vehiculo;
import modelo.Usuario;
import modelo.EspacioParqueo;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Hilo de salida de vehículos 
 * @author vicky
 */
public class GaritaSalida extends Thread {
    private Cola colaSalida;
    private Parqueo parqueoSocios;
    private Parqueo parqueoGeneral;
    private Pila pilaEventos;
    private ListaSimple listaUsuarios;
    private boolean activo;

    public GaritaSalida(Parqueo parqueoSocios, Parqueo parqueoGeneral, Pila pilaEventos, ListaSimple listaUsuarios) {
        this.colaSalida = new Cola();
        this.parqueoSocios = parqueoSocios;
        this.parqueoGeneral = parqueoGeneral;
        this.pilaEventos = pilaEventos;
        this.listaUsuarios = listaUsuarios;
        this.activo = true;
    }

    public synchronized void registrarSalida(String placa) {
        Vehiculo vSalida = new Vehiculo(placa, "", "", "", "");
        vSalida.setEstado("EN_COLA_SALIDA");
        colaSalida.encolar(vSalida);
        pilaEventos.apilar("Vehículo placa " + placa + " solicitado para salida (en cola).");
        notify();
    }

    @Override
    public void run() {
        while (activo) {
            Vehiculo vehiculoASalir = null;

            synchronized (this) {
                while (colaSalida.estaVacia() && activo) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }

            if (!activo) break;

            Object dato = colaSalida.desencolar();
            if (dato != null && dato instanceof Vehiculo) {
                vehiculoASalir = (Vehiculo) dato;
                procesarSalidaVehiculo(vehiculoASalir.getPlaca());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void procesarSalidaVehiculo(String placa) {
        boolean encontradoYLiberado = false;

        if (buscarYMarcarFuera(parqueoSocios, new String[]{"A", "B", "C"}, 25, placa)) {
            encontradoYLiberado = true;
        } else if (buscarYMarcarFuera(parqueoGeneral, new String[]{"E", "F", "G", "H", "I"}, 15, placa)) {
            encontradoYLiberado = true;
        }

        if (encontradoYLiberado) {
            reescribirArchivoVehiculos();
            pilaEventos.apilar("Garita de Salida: Vehículo con placa " + placa + " retirado con éxito.");
        } else {
            pilaEventos.apilar("Garita de Salida: No se encontró el vehículo con placa " + placa + " estacionado.");
        }
    }

    private boolean buscarYMarcarFuera(Parqueo parqueo, String[] letras, int maxNum, String placaBusqueda) {
        for (String letra : letras) {
            for (int i = 1; i <= maxNum; i++) {
                String codigo = letra + i;
                EspacioParqueo esp = parqueo.buscarEspacioPorCodigo(codigo);
                if (esp != null && esp.isOcupado() && esp.getVehiculo() != null) {
                    Vehiculo v = (Vehiculo) esp.getVehiculo();
                    if (v != null && v.getPlaca().equalsIgnoreCase(placaBusqueda)) {
                        v.setEstado("FUERA");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Reescribe vehiculos.txt 
    private void reescribirArchivoVehiculos() {
        try {
            File archivo = new File("vehiculos.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false));
            
            var nodoU = listaUsuarios.getCabeza();
            while (nodoU != null) {
                Usuario u = (Usuario) nodoU.getDato();
                var nodoV = u.getMisVehiculos().getCabeza();
                while (nodoV != null) {
                    Vehiculo v = (Vehiculo) nodoV.getDato();
                    String linea = u.getId() + "|" + v.getPlaca() + "|" + v.getMarca() + "|" + v.getModelo() + "|" + v.getColor() + "|" + v.getTipo() + "|" + v.getEstado();
                    bw.write(linea);
                    bw.newLine();
                    nodoV = nodoV.getSiguiente();
                }
                nodoU = nodoU.getSiguiente();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println(">>> [ERROR] No se pudo actualizar vehiculos.txt: " + e.getMessage());
        }
    }

    public void detener() {
        this.activo = false;
        synchronized (this) {
            notify();
        }
    }
}