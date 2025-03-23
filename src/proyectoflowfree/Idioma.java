/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author laraj
 */
public class Idioma {
    private static String idiomaActual = "es"; 

    public static String getIdiomaActual() {
        return idiomaActual;
    }

    public static void setIdiomaActual(String nuevoIdioma) {
        idiomaActual = nuevoIdioma;
    }

    public static Properties getMensajes() {
        Properties mensajes = new Properties();
        try {
            String rutaArchivo = "/proyectoflowfree/idiomas/mensajes_" + idiomaActual + ".properties";
            InputStream archivo = Idioma.class.getResourceAsStream(rutaArchivo);
            mensajes.load(archivo);
            archivo.close();
        } catch (Exception e) {
            System.out.println("Error al cargar el idioma: " + e.getMessage());
        }
        return mensajes;
    }
}
