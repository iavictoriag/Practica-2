/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

class NodoCola {
    private Object dato;
    private NodoCola siguiente;

    public NodoCola(Object dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public Object getDato() { return dato; }
    public NodoCola getSiguiente() { return siguiente; }
    public void setSiguiente(NodoCola siguiente) { this.siguiente = siguiente; }
}

public class Cola {
   

    public boolean estaVacia() {
        return frente == null;
    }

    // Encolar (Enqueue)
    public void encolar(Object dato) {
        NodoCola nuevo = new NodoCola(dato);
        if (estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
        tamanio++;
    }

    // Desencolar (Dequeue)
    public Object desencolar() {
        if (estaVacia()) return null;
        Object dato = frente.getDato();
        frente = frente.getSiguiente();
        if (frente == null) {
            fin = null;
        }
        tamanio--;
        return dato;
    }

    public int getTamanio() {
        return tamanio;
    }
}