/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

/**
 *
 * @author vicky
 */
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ManejoArchivos {

    // guardar o escribir una línea de texto en un archivo
    public static void guardarTexto(String nombreArchivo, String contenido) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            //sagregar texto al archivo sin sobreescribir lo anterior
            fichero = new FileWriter(nombreArchivo, true);
            pw = new PrintWriter(fichero);
            pw.println(contenido);
            
            System.out.println(">>> [ARCHIVO] Datos guardados exitosamente en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println(">>> [ERROR] Ocurrió un error al escribir el archivo: " + e.getMessage());
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    //leer el contenido del archivo 
    public static void leerArchivo(String nombreArchivo) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File(nombreArchivo);
            
            // miramos que el archivo existe 
            if (!archivo.exists()) {
                System.out.println(">>> [AVISO] El archivo " + nombreArchivo + " aún no existe.");
                return;
            }

            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String linea;
            System.out.println("\n==========================================");
            System.out.println("      CONTENIDO DEL ARCHIVO: " + nombreArchivo);
            System.out.println("==========================================");
            
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            System.out.println("==========================================");

        } catch (IOException e) {
            System.out.println("Ocurrio un error al leer el archivo: " + e.getMessage());
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
}