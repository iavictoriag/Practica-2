/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hilos;

/**
 *
 * @author vicky
 */

import estructuras.Cola;
import estructuras.Parqueo;
import estructuras.Pila;
import modelo.Usuario;
import modelo.Vehiculo;


public class GaritaEntrada extends Thread {

    private Cola colaEntrada;
    private Parqueo parqueoSocios;
    private Parqueo parqueoGeneral;
    private Pila pilaEventos;
    private boolean ejecutando;
    private int tiempoEsperaMs; // Tiempo de espera x vehículo

    public GaritaEntrada(Cola colaEntrada, Parqueo parqueoSocios, Parqueo parqueoGeneral, Pila pilaEventos) {
        this.colaEntrada = colaEntrada;
        this.parqueoSocios = parqueoSocios;
        this.parqueoGeneral = parqueoGeneral;
        this.pilaEventos = pilaEventos;
        this.ejecutando = true;
        this.tiempoEsperaMs = 2000; //2 segundos x vehículo
    }

    @Override
    public void run() {
        System.out.println(">>> [HILO GARITA ENTRADA] Iniciado y escuchando vehiculos...");

        while (ejecutando) {
            try {
                // si hay vehiculos en la cola
                if (!colaEntrada.estaVacia()) {
                    // Desencolar sincronizado
                    Vehiculo vehiculoProcesando = colaEntrada.desencolar();

                    if (vehiculoProcesando != null) {
                        System.out.println("\n[GARITA ENTRADA] Procesando vehículo con placa: " + vehiculoProcesando.getPlaca());

                        // Simular el tiempo de espera
                        Thread.sleep(tiempoEsperaMs);

                        boolean estacionado = false;

                        // estacionar segun el tipo de socio
                        estacionado = parqueoSocios.estacionarVehiculo(vehiculoProcesando);

                        if (!estacionado) {
                            // zona general
                            estacionado = parqueoGeneral.estacionarVehiculo(vehiculoProcesando);
                        }

                        if (estacionado) {
                            pilaEventos.apilar("[GARITA ENTRADA] Vehiculo " + vehiculoProcesando.getPlaca() + " estacionado con exito.");
                            System.out.println(">>> [GARITA ENTRADA] Vehiculo " + vehiculoProcesando.getPlaca() + " ingreso al parqueo.");
                        } else {
                            // Si sigue lleno, se reencola al final
                            colaEntrada.encolar(vehiculoProcesando);
                            pilaEventos.apilar("[GARITA ENTRADA] Parqueo lleno. Vehiculo " + vehiculoProcesando.getPlaca() + " reencolado.");
                            System.out.println(">>> [GARITA ENTRADA] Parqueo lleno. Vehiculo regresado a la cola.");
                        }
                    }
                }

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                System.out.println(">>> [HILO GARITA ENTRADA] Interrumpido.");
                break;
            }
        }

        System.out.println(">>> [HILO GARITA ENTRADA] Detenido.");
    }

    public void detener() {
        this.ejecutando = false;
    }

    public void setTiempoEsperaMs(int tiempoEsperaMs) {
        this.tiempoEsperaMs = tiempoEsperaMs;
    }
}