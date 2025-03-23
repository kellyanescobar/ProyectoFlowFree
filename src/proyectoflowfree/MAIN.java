/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;

import javax.swing.SwingUtilities;
import static musica.musica.PlayMusic;


/**
 *
 * @author 50494
 */
public class MAIN {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuInicio().mostrarEnFrame());
        String musicapath = "C:\\Users\\50494\\OneDrive\\Documents\\NetBeansProjects\\ProyectoFlowFree\\src\\musica\\backgrounbg.mp3";
        PlayMusic(musicapath);
    }

}
//modificar restriccion de mapa con 
//estadistica y lo del timpo para ganar
//volumen

