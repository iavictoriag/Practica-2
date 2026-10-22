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
 **/
public class Vehiculo {
    private String placa;// placa unica en todo el sistema
    private String marca;
    private String modelo;// Línea o modelo del vehículo
    private String color; 
    private String tipo;// de vehiculo 
    private String estado;//FUERA, EN_COLA_ENTRADA, ESTACIONADO, EN_COLA_SALIDA

    public Vehiculo(String placa, String marca, String modelo, String color, String tipo) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.tipo = tipo;
        this.estado = "FUERA"; //al crearse el vehículo se sca del residencial
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Placa: " + placa + " | Marca: " + marca + " | Modelo: " + modelo + " | Tipo: " + tipo + " | Estado: " + estado;
    }
}