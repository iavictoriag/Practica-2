/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author vicky
 */

/**
 * 
 * 
 */
public class Usuario {
    private String id; // unico del residente
    private String nombre;
    private String numeroCasa;// # de casa del residencial
    private String telefono;  
    private char esSocio; // S = socio, N = residente no socio
    private ListaSimple misVehiculos;// vehículos propios max. 3

    public Usuario(String id, String nombre, String numeroCasa, String telefono, char esSocio) {
        this.id = id;
        this.nombre = nombre;
        this.numeroCasa = numeroCasa;
        this.telefono = telefono;
        this.esSocio = Character.toUpperCase(esSocio);
        this.misVehiculos = new ListaSimple();
    }

    //agregar un vehículo validando máximo 3 vehículos
    public boolean agregarVehiculo(Vehiculo v) {
        if (misVehiculos.getTamanio() >= 3) {
            System.out.println("Error: El residente ya cuenta con el limite maximo de 3 vehiulos registrados.");
            return false;
        }
        misVehiculos.insertar(v);
        return true;
    }

    // Getters y Setters
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

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public char getEsSocio() {
        return esSocio;
    }

    public void setEsSocio(char esSocio) {
        this.esSocio = Character.toUpperCase(esSocio);
    }

    public ListaSimple getMisVehiculos() {
        return misVehiculos;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre + " | Casa: " + numeroCasa + " | Socio: " + esSocio + " | Vehículos reg.: " + misVehiculos.getTamanio();
    }
}