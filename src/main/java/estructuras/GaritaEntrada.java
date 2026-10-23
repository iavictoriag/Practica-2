/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
import modelo.Vehiculo;
/**
 *
 * @author vicky
 */
public class GaritaEntrada implements Runnable {
    private String nombreGarita;
    private Cola colaEntrada;
    private Parqueo parqueoSocios;
    private Parqueo parqueoGeneral;
    private Pila pilaEventos;
    private boolean activo;

    public GaritaEntrada(String nombreGarita, Cola colaEntrada, Parqueo parqueoSocios, Parqueo parqueoGeneral, Pila pilaEventos) {
        this.nombreGarita = nombreGarita;
        this.colaEntrada = colaEntrada;
        this.parqueoSocios = parqueoSocios;
        this.parqueoGeneral = parqueoGeneral;
        this.pilaEventos = pilaEventos;
        this.activo = true;
    }

    @Override
    public void run() {
        while (activo) {
            Vehiculo v = null;
            
            //acceso a la cola 
            synchronized (colaEntrada) {
                while (colaEntrada.estaVacia() && activo) {
                    try {
                        colaEntrada.wait(); //vehiculo si la cola esta vacía
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                
                if (!activo) break;
                v = colaEntrada.desencolar();
            }

            if (v != null) {
                System.out.println("[" + nombreGarita + "] Atendiendo vehículo placa: " + v.getPlaca());
                
                //tiempo de espera en garita 
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                //estacionar x tipo de propietario
                boolean estacionado = false;
                                estacionado = parqueoGeneral.estacionarVehiculo(v);

                synchronized (pilaEventos) {
                    if (estacionado) {
                        pilaEventos.apilar("Garita " + nombreGarita + " estaciono vehiculo " + v.getPlaca());
                        System.out.println("[" + nombreGarita + "] ¡Vehiculo " + v.getPlaca() + " estacionado con exito!");
                    } else {
                        pilaEventos.apilar("Garita " + nombreGarita + ": Rechazado " + v.getPlaca() + " (Parqueo lleno)");
                        System.out.println("[" + nombreGarita + "] Parqueo lleno. Vehiculo " + v.getPlaca() + " rechazado.");
                    }
                }
            }
        }
    }

    public void detener() {
        this.activo = false;
        synchronized (colaEntrada) {
            colaEntrada.notifyAll();
        }
    }
}