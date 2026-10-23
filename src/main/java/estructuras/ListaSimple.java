/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

import modelo.Usuario;

/**
 *
 * @author vicky
 */
public class ListaSimple {
    private NodoSimple cabeza;
    private int tamanio;

    public ListaSimple() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    // Comprobar si la lista esta  vacía
    public boolean estaVacia() {
        return cabeza == null;
    }

    //insertar elementos en la lista
    public void insertar(Object dato) {
        NodoSimple nuevo = new NodoSimple(dato);
        if (estaVacia()) {
            cabeza = nuevo;
        } else {
            NodoSimple actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamanio++; // Incrementar tamaño
    }

    // mostrar  elementos de la lista
    public void mostrar() {
        if (estaVacia()) {
            System.out.println("No hay usuarios registrados en el sistema.");
            return;
        }
        NodoSimple actual = cabeza;
        while (actual != null) {
            System.out.println(actual.getDato().toString());
            actual = actual.getSiguiente();
        }
    }

    // Obtener el tamaño de la lista
    public int getTamanio() {
        return tamanio;
    }

    // Obtener la cabeza de la lista
    public NodoSimple getCabeza() {
        return cabeza;
    }
}