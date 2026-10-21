/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * 
 */
class NodoPila {
    private String evento;
    private NodoPila siguiente;

    public NodoPila(String evento) {
        this.evento = evento;
        this.siguiente = null;
    }

    public String getEvento() { return evento; }
    public NodoPila getSiguiente() { return siguiente; }
    public void setSiguiente(NodoPila siguiente) { this.siguiente = siguiente; }
}

/**
 * 
 */
public class Pila {
    private NodoPila cima;
    private int tamanio;

    public Pila() {
        this.cima = null;
        this.tamanio = 0;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    // Apila 
    public void apilar(String evento) {
        NodoPila nuevo = new NodoPila(evento);
        nuevo.setSiguiente(cima);
        cima = nuevo;
        tamanio++;
    }

    // Desapila
    public String desapilar() {
        if (estaVacia()) return null;
        String evento = cima.getEvento();
        cima = cima.getSiguiente();
        tamanio--;
        return evento;
    }

    public int getTamanio() {
        return tamanio;
    }
}