/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

import modelo.Usuario;

/**
 * 
 * 
 * 
 * @author vicky
 */
public class ListaDoble {
    private NodoDoble<Object> cabeza;
    private NodoDoble<Object> cola;
    private int tamanio;

    // lista doble vacía
    public ListaDoble() {
        this.cabeza = null;
        this.cola = null;
        this.tamanio = 0;
    }

    //si la lista no contiene elementos
    public boolean estaVacia() {
        return cabeza == null;
    }

    //nuevo elemento al final de la lista doble
    public void insertar(Object dato) {
        NodoDoble<Object> nuevo = new NodoDoble<>(dato);
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

    // Busca un residente dentro de la lista utilizando su ID único
    public Object buscarPorId(String idBuscado) {
        NodoDoble<Object> actual = cabeza;
        while (actual != null) {
            Usuario u = (Usuario) actual.getDato();
            if (u.getId().equalsIgnoreCase(idBuscado)) {
                return u; // Retorna el usuario encontrado
            }
            actual = actual.getSiguiente();
        }
        return null; // Si no lo encuentra, retorna nulo
    }

    // Recorre e imprime los elementos desde el inicio hasta el final
    public void mostrarAdelante() {
        NodoDoble<Object> actual = cabeza;
        while (actual != null) {
            System.out.println(actual.getDato().toString());
            actual = actual.getSiguiente();
        }
    }

    // Obtener el tamaño actual de la lista
    public int getTamanio() {
        return tamanio;
    }

    // Obtener el nodo cabeza de la lista
    public NodoDoble<Object> getCabeza() {
        return cabeza;
    }
}