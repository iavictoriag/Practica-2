/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
import modelo.EspacioParqueo;
/**
 *
 * @author vicky
 */

public class NodoCircular {
    private EspacioParqueo espacio;
    private NodoCircular siguiente;

    public NodoCircular(EspacioParqueo espacio) {
        this.espacio = espacio;
        this.siguiente = null;
    }

    public EspacioParqueo getEspacio() {
        return espacio;
    }

    public void setEspacio(EspacioParqueo espacio) {
        this.espacio = espacio;
    }

    public NodoCircular getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCircular siguiente) {
        this.siguiente = siguiente;
    }
}
