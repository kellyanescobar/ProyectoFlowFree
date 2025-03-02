/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author laraj
 */
public class Reportes extends JPanel {
    private JButton verLogs, verRanking, regresar;
    private JLabel icono, titulo;

    public Reportes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ImageIcon imagenIcono = cargarImagen("imagenes/Icono.jpeg");
        icono = (imagenIcono != null) ? new JLabel(imagenIcono) : new JLabel("Imagen no encontrada");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(icono, gbc);

        titulo = new JLabel("Reportes", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(titulo, gbc);

        JPanel botonesPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        botonesPanel.setPreferredSize(new Dimension(250, 120));

        
        verRanking = new JButton("Ver Ranking de Jugadores");
        verRanking.addActionListener(e -> abrirRanking());
        botonesPanel.add(verRanking);

        regresar = new JButton("Regresar");
        regresar.addActionListener(e -> regresarMenu());
        botonesPanel.add(regresar);

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(botonesPanel, gbc);
    }

    

    private void abrirRanking() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new Ranking().mostrarEnFrame();
    }

    private void regresarMenu() {
    if (Login.usuarioLogueado == null) {
        JOptionPane.showMessageDialog(null, "No hay usuario logueado.");
        return;
    }
    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
    frame.dispose();
    new MenuPrincipal(Login.usuarioLogueado).mostrarEnFrame();
}


    private ImageIcon cargarImagen(String ruta) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(ruta);
        return (imgURL != null) ? new ImageIcon(imgURL) : new ImageIcon();
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Reportes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
