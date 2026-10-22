/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 *
 * @author vicky
 */

public class Parqueo {
    private NodoCircular cabeza;
    private NodoCircular ultimoAsignado; // Puntero para la asignación circular equitativa
    private int tamanio;
    private String tipoZona; // "SOCIO" o "GENERAL"

    public Parqueo(String tipoZona) {
        this.cabeza = null;
        this.ultimoAsignado = null;
        this.tamanio = 0;
        this.tipoZona = tipoZona;
    }

   
}