/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to edit this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Maneja la información temporal de los visitantes del residencial
 * @author vicky
 */
public class Visitante {
    private String nombre;
    private String placa;
    private String idResidenteDestino; // ID de la visita
    private Vehiculo vehiculoAsociado;  // Vehículo del visitante

    public Visitante(String nombre, String placa, String idResidenteDestino, String marca, String modelo, String color, String tipo) {
        this.nombre = nombre;
        this.placa = placa;
        this.idResidenteDestino = idResidenteDestino;
        
        // Creamos el vehículo y le asignamos un estado temporal (no se guarda en archivos)
        this.vehiculoAsociado = new Vehiculo(placa, marca, modelo, color, tipo);
        this.vehiculoAsociado.setEstado("VISITANTE_ESTACIONADO");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getIdResidenteDestino() {
        return idResidenteDestino;
    }

    public void setIdResidenteDestino(String idResidenteDestino) {
        this.idResidenteDestino = idResidenteDestino;
    }

    public Vehiculo getVehiculoAsociado() {
        return vehiculoAsociado;
    }

    public void setVehiculoAsociado(Vehiculo vehiculoAsociado) {
        this.vehiculoAsociado = vehiculoAsociado;
    }

    @Override
    public String toString() {
        return "Visitante: " + nombre + " | Placa: " + placa + " | Visita a ID: " + idResidenteDestino;
    }
}