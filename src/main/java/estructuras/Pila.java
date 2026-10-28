/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to edit this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * Almacena el registrado en el sistema.
 */
class NodoPila {
    private String descripcionEvento;
    private NodoPila siguiente;

    public NodoPila(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
        this.siguiente = null;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public NodoPila getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPila siguiente) {
        this.siguiente = siguiente;
    }
}

/**
 * historial
 */
public class Pila {
    private NodoPila cima;
    private int tamanio;

    public Pila() {
        this.cima = null;
        this.tamanio = 0;
    }

    // Apilar lo nuevo
    public void apilar(String evento) {
        NodoPila nuevo = new NodoPila(evento);
        nuevo.setSiguiente(cima);
        cima = nuevo;
        tamanio++;
    }

    // Desapilar lo último
    public String desapilar() {
        if (cima == null) return null;
        String evento = cima.getDescripcionEvento();
        cima = cima.getSiguiente();
        tamanio--;
        return evento;
    }

    // Mostrar el historial del más reciente al antiguo
    public void mostrarHistorial() {
        if (cima == null) {
            System.out.println("No hay eventos registrados en la pila.");
            return;
        }

        NodoPila actual = cima;
        System.out.println("\n--- HISTORIAL DE EVENTOS (PILA LIFO) ---");
        while (actual != null) {
            System.out.println("[EVENTO] " + actual.getDescripcionEvento());
            actual = actual.getSiguiente();
        }
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int getTamanio() {
        return tamanio;
    }
    //  vaciar el historial
    public void limpiar() {
        while (cima != null) {
            desapilar();
        }
        System.out.println(">>> [PILA] El historial de eventos ha sido limpiado por completo.");
    }
}