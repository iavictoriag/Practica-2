/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author vicky
 */
import modelo.Usuario; // O Residente según tu convención

class NodoDoble {
    private Object dato;
    private NodoDoble siguiente;
    private NodoDoble anterior;

    public NodoDoble(Object dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }

    public Object getDato() { return dato; }
    public void setDato(Object dato) { this.dato = dato; }
    public NodoDoble getSiguiente() { return siguiente; }
    public void setSiguiente(NodoDoble siguiente) { this.siguiente = siguiente; }
    public NodoDoble getAnterior() { return anterior; }
    public void setAnterior(NodoDoble anterior) { this.anterior = anterior; }
}

public class ListaDoble {
    private NodoDoble cabeza;
    private NodoDoble cola;
    private int tamanio;

    public ListaDoble() {
        this.cabeza = null;
        this.cola = null;
        this.tamanio = 0;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    // Insertar al final de la lista doble
    public void insertar(Object dato) {
        NodoDoble nuevo = new NodoDoble(dato);
        if (estaVacia()) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.setSiguiente(nuevo);
            nuevo.setAnterior(cola);
            cola = nuevo;
        }
        tamanio++;
    }

    // Buscar residente por ID (asumiendo que el objeto tiene un método getId())
    public Object buscarPorId(String idBuscado) {
        NodoDoble actual = cabeza;
        while (actual != null) {
            Usuario u = (Usuario) actual.getDato();
            if (u.getId().equalsIgnoreCase(idBuscado)) {
                return u;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    // Mostrar elementos hacia adelante
    public void mostrarAdelante() {
        NodoDoble actual = cabeza;
        while (actual != null) {
            System.out.println(actual.getDato().toString());
            actual = actual.getSiguiente();
        }
    }

    public int getTamanio() {
        return tamanio;
    }

    public NodoDoble getCabeza() {
        return cabeza;
    }
}