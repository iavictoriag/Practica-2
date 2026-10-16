package com.mycompany._practica2;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * @author vicky
 */
public class ManejoArchivos {

    public void crearReporteHTML() {
        try {
            FileWriter archivo = new FileWriter("reporte.html");
            PrintWriter escritor = new PrintWriter(archivo);

            escritor.println("<html>");
            escritor.println("<head>");
            escritor.println("<title>Reporte de Puntajes</title>");
            escritor.println("</head>");
            escritor.println("<body>");
            
            escritor.println("<h1>Quetzal Space Defender - Reporte</h1>");
            escritor.println("<hr>");
            
            // Tabla de puntajes
            escritor.println("<h2>Mejores Puntajes</h2>");
            escritor.println("<table border='1'>");
            escritor.println("<tr><th>Jugador</th><th>Puntaje</th></tr>");
            escritor.println("<tr><td>Victoria</td><td>500</td></tr>");
            escritor.println("<tr><td>Jugador 2</td><td>300</td></tr>");
            escritor.println("</table>");
            
            // Imagen de la gráfica 
            escritor.println("<h2>Grafica de Desempeno</h2>");
            escritor.println("<img src='grafica.png' width='500'>");
            
            escritor.println("</body>");
            escritor.println("</html>");

            escritor.close();
            System.out.println("¡Reporte HTML!");

        } catch (IOException e) {
            System.out.println("Error al generar el reporte HTML.");
        }
    }
}