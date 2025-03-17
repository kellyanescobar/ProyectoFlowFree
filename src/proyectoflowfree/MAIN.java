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
            String musicapath = "C:/Users/50494/OneDrive/Documents/NetBeansProjects/ProyectoFlowFree/src/musica/musica de elevador.mp3";
            PlayMusic(musicapath);
    }
    
}
//boton de reinicio
//boton de regreso
//alamcenamiento de progreso
//estadistica y lo del timpo para ganar
//niveles en abstract
//idomas
