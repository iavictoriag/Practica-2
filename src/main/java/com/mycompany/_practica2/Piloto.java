/**almacena al piloto y sus atributoss
 * */
package com.mycompany._practica2;

public class Piloto {
    private String nombre;
    private int puntajeMaximo;
    private String tipoNave;    // Explorador, Caza Estelar, Acorazado
    private String dificultad;  // Fácil, Normal, Difícil
    private int cadenciaDisparo; // Tiempo en milisegundos de 2000, 1000, o 300
    
    public Piloto(String nombre, String dificultad){
        this.nombre = nombre;
        this.puntajeMaximo = 0; //empieza desde 0 puntos
        this.dificultad = dificultad;
        nave(dificultad); // nave y tiempos según la dificultad
    }

    // la nave
    private void nave(String dificultad) {
        if (dificultad.equalsIgnoreCase("Fácil")) {
            this.tipoNave = "Explorador";
            this.cadenciaDisparo = 2000; // 2 segundos
        } else if (dificultad.equalsIgnoreCase("Difícil")) {
            this.tipoNave = "Acorazado";
            this.cadenciaDisparo = 300;  // 0.3 segundos 
        } else {
            this.tipoNave = "Caza Estelar";
            this.cadenciaDisparo = 1000; // 1 segundo 
        }
    }

    // Getters y Setters
    public String getNombre(){
        return nombre;
    }
    public int getPuntajeMaximo(){
        return puntajeMaximo;
    }
    public void setPuntajeMaximo(int puntajeMaximo){
        this.puntajeMaximo = puntajeMaximo;
    }
    
    public String getTipoNave() { return tipoNave; }
    public String getDificultad() { return dificultad; }
    public int getCadenciaDisparo() { return cadenciaDisparo; }
}