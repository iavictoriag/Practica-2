package modelo;

import estructuras.ListaVehiculos;

public class Residente {
    private String id;
    private String nombre;
    private String casa;
    private boolean esSocio;
    private ListaVehiculos vehiculos;

    // Constructor básico
    public Residente(String id, String nombre, String casa, boolean esSocio) {
        this.id = id;
        this.nombre = nombre;
        this.casa = casa;
        this.esSocio = esSocio;
        this.vehiculos = new ListaVehiculos(); // Inicializamos la lista vacía
    }

    // Getters y Setters sencillos
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public boolean isEsSocio() {
        return esSocio;
    }

    public void setEsSocio(boolean esSocio) {
        this.esSocio = esSocio;
    }

    public ListaVehiculos getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(ListaVehiculos vehiculos) {
        this.vehiculos = vehiculos;
    }
}