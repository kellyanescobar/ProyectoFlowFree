/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NIVELES;

/**
 *
 * @author 50494
 */
import javax.swing.*;
import java.awt.*;

public class CargaEntreNiveles extends JDialog {

    private JLabel label;
    private boolean animando = true;

    public CargaEntreNiveles(Window parent) {
        super(parent);

        setSize(parent.getWidth(), parent.getHeight());
        setLocationRelativeTo(parent);

        getContentPane().setBackground(new Color(0, 0, 0, 220));
        setLayout(new GridBagLayout());

        label = new JLabel("Cargando");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 32));

        add(label);
    }

    public void mostrarPor(int milisegundos, Runnable cuandoTermine) {
        setVisible(true);

        // Animación del texto con puntitos
        new Thread(() -> {
            int puntos = 0;
            while (animando) {
                String texto = "Cargando" + ".".repeat(puntos % 4);
                label.setText(texto);
                puntos++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // hilo de espera y cierre
        new Thread(() -> {
            try {
                Thread.sleep(milisegundos);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            animando = false;
            SwingUtilities.invokeLater(() -> {
                setVisible(false);
                dispose();
                if (cuandoTermine != null) {
                    cuandoTermine.run();
                }
            });
        }).start();
    }
}
