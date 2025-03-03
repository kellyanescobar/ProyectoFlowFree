/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;
import NIVELES.MapaNivelesBonito;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.*;
import javax.swing.*;


public class MenuInicio extends JPanel {
    private JButton crearUsuario, iniciarSesion, salir;
    private JLabel icono, titulo;

    public MenuInicio() {
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

        titulo = new JLabel("FREE FLOW", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridy = 1;
        add(titulo, gbc);

        crearUsuario = new JButton("Crear Cuenta");
        crearUsuario.addActionListener(e -> abrirCrearUsuario());
        gbc.gridy = 2;
        add(crearUsuario, gbc);

        iniciarSesion = new JButton("Iniciar Sesión");
        iniciarSesion.addActionListener(e -> abrirIniciarSesion());
        gbc.gridy = 3;
        add(iniciarSesion, gbc);

        salir = new JButton("Salir");
        salir.addActionListener(e -> System.exit(0));
        gbc.gridy = 4;
        add(salir, gbc);
    }

    private void abrirCrearUsuario() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new CrearCuenta().mostrarEnFrame();
    }

    private void abrirIniciarSesion() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new IniciarSesion().mostrarEnFrame();
    }

    private ImageIcon cargarImagen(String ruta) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(ruta);
        return (imgURL != null) ? new ImageIcon(imgURL) : new ImageIcon();
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Pantalla Inicial");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

 
  
    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> new MenuInicio().mostrarEnFrame()); {
            MapaNivelesBonito mapa = new MapaNivelesBonito();
            mapa.mostrarEnFrame();
    }
    }
}
