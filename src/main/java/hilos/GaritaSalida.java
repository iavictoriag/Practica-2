package hilos;

import estructuras.Parqueo;
import estructuras.Pila;

/**
 *.
 * Libera espacios en el parqueo
 */
public class GaritaSalida extends Thread {

    private Parqueo parqueoSocios;
    private Parqueo parqueoGeneral;
    private Pila pilaEventos;
    private boolean ejecutando;

    public GaritaSalida(Parqueo parqueoSocios, Parqueo parqueoGeneral, Pila pilaEventos) {
        this.parqueoSocios = parqueoSocios;
        this.parqueoGeneral = parqueoGeneral;
        this.pilaEventos = pilaEventos;
        this.ejecutando = true;
    }

    @Override
    public void run() {
        System.out.println(">>> [HILO GARITA SALIDA] Iniciado...");

        while (ejecutando) {
            try {
                // Pausa entre chequeos o salidas simuladas
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void registrarSalida(String placa) {
        boolean liberado = parqueoSocios.retirarVehiculo(placa);
        if (!liberado) {
            liberado = parqueoGeneral.retirarVehiculo(placa);
        }

        if (liberado) {
            pilaEventos.apilar("[GARITA SALIDA] Vehiculo placa " + placa + " salio del parqueo.");
            System.out.println(">>> [GARITA SALIDA] Vehículo " + placa + " ha salido. Espacio liberado.");
        } else {
            System.out.println(">>> [GARITA SALIDA] No se encontró la placa " + placa + " dentro del parqueo.");
        }
    }

    public void detener() {
        this.ejecutando = false;
    }
}