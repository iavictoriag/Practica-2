/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;

/**
 * 
 */
class NodoEspacio {
    private String idEspacio;       
    private String tipoArea;          
    private boolean estadoOcupado;    
    private String placaVehiculo;     // Placa del estacionado o libre
    private NodoEspacio siguiente;    

    public NodoEspacio(String idEspacio, String tipoArea) {
        this.idEspacio = idEspacio;
        this.tipoArea = tipoArea;
        this.estadoOcupado = false;
        this.placaVehiculo = "LIBRE";
        this.siguiente = null;
    }

    public String getIdEspacio() { return idEspacio; }
    public boolean isEstadoOcupado() { return estadoOcupado; }
    public void setEstadoOcupado(boolean estadoOcupado) { this.estadoOcupado = estadoOcupado; }
    public String getPlacaVehiculo() { return placaVehiculo; }
    public void setPlacaVehiculo(String placaVehiculo) { this.placaVehiculo = placaVehiculo; }
    public NodoEspacio getSiguiente() { return siguiente; }
    public void setSiguiente(NodoEspacio siguiente) { this.siguiente = siguiente; }
}

/**
 * 
 * 
 */
public class ListaCircular {
    private NodoEspacio cabeza;
    private NodoEspacio ultimoAsignado; 
    private int limiteEspacios;

    public ListaCircular(int limite) {
        this.cabeza = null;
        this.ultimoAsignado = null;
        this.limiteEspacios = limite;
    }


    public void inicializarEspacios(String prefijo) {
        for (int i = 1; i <= limiteEspacios; i++) {
            String id = prefijo + i;
            NodoEspacio nuevo = new NodoEspacio(id, prefijo);
            if (cabeza == null) {
                cabeza = nuevo;
                cabeza.setSiguiente(cabeza);
                ultimoAsignado = cabeza;
            } else {
                NodoEspacio actual = cabeza;
                while (actual.getSiguiente() != cabeza) {
                    actual = actual.getSiguiente();
                }
                actual.setSiguiente(nuevo);
                nuevo.setSiguiente(cabeza);
            }
        }
    }

    public boolean estacionarVehiculo(String placa) {
        if (cabeza == null) return false;

        NodoEspacio inicioBusqueda = ultimoAsignado != null ? ultimoAsignado.getSiguiente() : cabeza;
        NodoEspacio actual = inicioBusqueda;

        do {
            if (!actual.isEstadoOcupado()) {
                actual.setEstadoOcupado(true);
                actual.setPlacaVehiculo(placa);
                ultimoAsignado = actual;
                return true; 
            }
            actual = actual.getSiguiente();
        } while (actual != inicioBusqueda);

        return false; 
    }

    public boolean retirarVehiculo(String placa) {
        if (cabeza == null) return false;
        NodoEspacio actual = cabeza;
        do {
            if (actual.isEstadoOcupado() && actual.getPlacaVehiculo().equalsIgnoreCase(placa)) {
                actual.setEstadoOcupado(false);
                actual.setPlacaVehiculo("LIBRE");
                return true; 
            }
            actual = actual.getSiguiente();
        } while (actual != cabeza);
        return false;
    }
}