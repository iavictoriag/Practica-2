/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author vicky
 * 
 * 
 */
public class EspacioParqueo {
    private String codigoEspacio; //A1, B25, E12
    private String tipoArea;// "SOCIO" o "GENERAL"
    private boolean ocupado;// true = ocupado, false = libre
    private Vehiculo vehiculo;// Vehículo estacionado 

    public EspacioParqueo(String codigoEspacio, String tipoArea) {
        this.codigoEspacio = codigoEspacio;
        this.tipoArea = tipoArea;
        this.ocupado = false;
        this.vehiculo = null;
    }

    public String getCodigoEspacio() {
        return codigoEspacio;
    }

    public void setCodigoEspacio(String codigoEspacio) {
        this.codigoEspacio = codigoEspacio;
    }

    public String getTipoArea() {
        return tipoArea;
    }

    public void setTipoArea(String tipoArea) {
        this.tipoArea = tipoArea;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        this.ocupado = (vehiculo != null);
    }

    @Override
    public String toString() {
        String estadoStr = ocupado ? "OCUPADO [" + vehiculo.getPlaca() + "]" : "LIBRE";
        return "Espacio: " + codigoEspacio + " (" + tipoArea + ") - Estado: " + estadoStr;
    }
}