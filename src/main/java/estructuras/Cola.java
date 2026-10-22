/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to edit this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

import modelo.Vehiculo;

/**
 * Almacena un vehículo
 */
class NodoCola {
    private Vehiculo vehiculo;
    private NodoCola siguiente;

    public NodoCola(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        this.siguiente = null;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setSiguiente(NodoCola siguiente) {
        this.siguiente = siguiente;
    }

    public NodoCola getSiguiente() {
        return siguiente;
    }
}

/**
 * gestionar la llegada de vehículos a las garitas
 */
public class Cola {
    private NodoCola cabeza;
    private NodoCola fin;
    private int tamanio;

    public Cola() {
        this.cabeza = null;
        this.fin = null;
        this.tamanio = 0;
    }

    // Encolar un vehículo
    public synchronized void encolar(Vehiculo v) {
        NodoCola nuevo = new NodoCola(v);
        if (fin == null) {
            cabeza = fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        tamanio++;
    }

    // Desencolar el vehículo
    public synchronized Vehiculo desencolar() {
        if (cabeza == null) return null;
        
        Vehiculo v = cabeza.getVehiculo();
        cabeza = cabeza.getSiguiente();
        if (cabeza == null) {
            fin = null;
        }
        tamanio--;
        return v;
    }

    public synchronized boolean estaVacia() {
        return cabeza == null;
    }

    public synchronized int getTamanio() {
        return tamanio;
    }
}