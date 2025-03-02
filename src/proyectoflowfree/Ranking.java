/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;

/**
 *
 * @author laraj
 */

import java.awt.*;
import javax.swing.*;

public class Ranking extends JPanel {
    private JTextArea rankingArea;
    private JButton regresar;
    private JLabel icono, titulo;

    public Ranking() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ImageIcon imagenIcono = cargarImagen("imagenes/Icono.jpg");
        icono = (imagenIcono != null) ? new JLabel(imagenIcono) : new JLabel("Imagen no encontrada");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(icono, gbc);

        titulo = new JLabel("Ranking de Jugadores", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridy = 1;
        add(titulo, gbc);

        rankingArea = new JTextArea(10, 30);
        rankingArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(rankingArea);
        gbc.gridy = 2;
        add(scrollPane, gbc);

        regresar = new JButton("Regresar");
        regresar.addActionListener(e -> regresarMenu());
        gbc.gridy = 3;
        add(regresar, gbc);

        mostrarRanking();
    }

    private void mostrarRanking() {
        if (Login.usuarioLogueado == null) {
            rankingArea.setText("No hay usuario logueado.");
            return;
        }

    }

    private void regresarMenu() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new Reportes().mostrarEnFrame();

    }

    private ImageIcon cargarImagen(String ruta) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(ruta);
        return (imgURL != null) ? new ImageIcon(imgURL) : new ImageIcon();
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Ranking de Jugadores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
