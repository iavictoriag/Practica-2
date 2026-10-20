/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author vicky
 */

//recorre elementos hacia adelante y hacia atrás
public class ListaDoble<T> {
    private NodoDoble<T> inicio;//contador inicial
    private NodoDoble<T> fin;
    private int tamanio;// contado total de elementos

    public ListaDoble() {
        this.inicio = null;
        this.fin = null;
        this.tamanio = 0;
    }

    public boolean estaVacia() {
        return inicio == null;
    }

    // Insertar al final 
    public void insertar(T dato) {
        NodoDoble<T> nuevo = new NodoDoble<>(dato);//nuevo nodo encapsulado
        
        if (estaVacia()) {//si esta vacia el nodo va al inicio
            inicio = nuevo;
            fin = nuevo;
        } else {//buscamos al ultimo nodo
            fin.setSiguiente(nuevo); //  apunta al nuevo
            nuevo.setAnterior(fin); // apunta hacia atrás al antiguo fin
            fin = nuevo; // nuevo nodo al fin 
        }
        tamanio++;
    }

    // enseña desde el inicio hasta el fin
    public void mostrarAdelante() {
        NodoDoble<T> actual = inicio;
        while (actual != null) {
            System.out.println(actual.getDato());
            actual = actual.getSiguiente();//siguiente nodo
        }
    }
}