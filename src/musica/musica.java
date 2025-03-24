/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package musica;

/**
 *
 * @author 50494
 */

import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class musica {
    private static Player player;
    private static float volumenActual = 0.5f; 

    public static void PlayMusic(String location) {
        new Thread(() -> {
            while (true) {  //loop hilo
                try {
                    FileInputStream fileInputStream = new FileInputStream(location);
                    player = new Player(fileInputStream);
                    System.out.println("musica...");
                    player.play();
                    System.out.println("Reproduciendo de nuevo...");
                } catch (Exception e) {
                    System.out.println("Error al reproducir la música: " + e.getMessage());
                }
            }
        }).start();
    }

    public static void setVolume(float volumen) {
        volumenActual = volumen;
        System.out.println("Volumen ajustado a: " + (int) (volumen * 100) + "%");
    }

    public static float getVolume() {
        return volumenActual;
    }
}