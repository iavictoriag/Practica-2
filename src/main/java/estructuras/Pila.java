/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author vicky
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

}