/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package musica;

import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class musica {
    public static void main(String[] args) {
        // Ruta corregida con doble barra o slash
        String musicapath = "C:/Users/50494/OneDrive/Documents/NetBeansProjects/ProyectoFlowFree/src/musica/musica de elevador.mp3";
        
        PlayMusic(musicapath);
    }

    public static void PlayMusic(String location) {
        while(true){
        try {
            FileInputStream fileInputStream = new FileInputStream(location);
            Player player = new Player(fileInputStream);
            System.out.println("🎵 Reproduciendo música...");
            player.play(); // Reproducir el MP3
            System.out.println("🎵 Música finalizada.");
        } catch (Exception e) {
            System.out.println("❌ Error al reproducir la música: " + e.getMessage());
        }
    }
}
}
