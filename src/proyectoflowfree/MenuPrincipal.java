/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;
import NIVELES.MapaNivelesBonito;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author laraj
 */
public class MenuPrincipal extends JPanel {
    private JButton jugar, verPerfil, reportes, cerrarSesion;
    private JLabel icono, titulo;
    private Login log;

    public MenuPrincipal(Login log) {
        this.log = log;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ImageIcon imagenIcono = cargarImagen("imagenes/Icono.jpeg");
        icono = (imagenIcono != null) ? new JLabel(imagenIcono) : new JLabel("Imagen no encontrada");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(icono, gbc);

        titulo = new JLabel("Menu Principal", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridy = 1;
        add(titulo, gbc);

        jugar = new JButton("Jugar");
        jugar.addActionListener(e -> abrirMapa());  // <-- Aquí ahora abre el mapa bonito
        gbc.gridy = 2;
        add(jugar, gbc);

        verPerfil = new JButton("Ver Perfil");
        verPerfil.addActionListener(e -> abrirVerPerfil());
        gbc.gridy = 3;
        add(verPerfil, gbc);

        reportes = new JButton("Reportes");
        reportes.addActionListener(e -> abrirReportes());
        gbc.gridy = 4;
        add(reportes, gbc);

        cerrarSesion = new JButton("Cerrar Sesion");
        cerrarSesion.addActionListener(e -> cerrarSesion());
        gbc.gridy = 5;
        add(cerrarSesion, gbc);
    }

    private void abrirMapa() {
      
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();

        SwingUtilities.invokeLater(() -> {
            MapaNivelesBonito mapa = new MapaNivelesBonito();  
            mapa.mostrarEnFrame();
        });
    }

    private void abrirVerPerfil() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new VerPerfil().mostrarEnFrame();
    }

    private void abrirReportes() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        Reportes reportes = new Reportes();
        reportes.mostrarEnFrame();
    }

    private void cerrarSesion() {
        log.cerrarSesion();
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new MenuInicio().mostrarEnFrame();
    }

    private ImageIcon cargarImagen(String ruta) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(ruta);
        return (imgURL != null) ? new ImageIcon(imgURL) : new ImageIcon();
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
