package estructuras;

import modelo.Vehiculo;

public class ListaVehiculos {
    private NodoVehiculo cabeza;
    private int cantidad;

    public ListaVehiculos() {
        this.cabeza = null;
        this.cantidad = 0;
    }

    public boolean agregar(Vehiculo vehiculo) {
        if (cantidad >= 3) {
            return false; // Máximo 3 vehículos permitidos por residente
        }
        
        NodoVehiculo nuevo = new NodoVehiculo(vehiculo);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoVehiculo actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        cantidad++;
        return true;
    }

    public int getCantidad() {
        return cantidad;
    }

    public NodoVehiculo getCabeza() {
        return cabeza;
    }
}