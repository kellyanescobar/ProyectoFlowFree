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

public class IniciarSesion extends JPanel {
    private JTextField usuario;
    private JPasswordField contra;
    private JButton ingresar, regresar;
    private JLabel icono;

    public IniciarSesion() {
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

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        formPanel.add(new JLabel("Nombre de usuario:", SwingConstants.RIGHT));
        usuario = new JTextField(15);
        formPanel.add(usuario);

        formPanel.add(new JLabel("Contraseña:", SwingConstants.RIGHT));
        contra = new JPasswordField(15);
        formPanel.add(contra);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(formPanel, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        ingresar = new JButton("Iniciar Sesión");
        ingresar.addActionListener(e -> iniciarSesion());
        buttonPanel.add(ingresar);

        regresar = new JButton("Regresar");
        regresar.addActionListener(e -> regresarPantalla());
        buttonPanel.add(regresar);

        gbc.gridy = 2;
        add(buttonPanel, gbc);
    }

    private void iniciarSesion() {
        String usuarioIngresado = usuario.getText();
        String passwordIngresado = new String(contra.getPassword());

        if (Login.iniciarSesion(usuarioIngresado, passwordIngresado)) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
            new MenuPrincipal(Login.cargarDatos(usuarioIngresado)).mostrarEnFrame();
        } else {
            JOptionPane.showMessageDialog(null, "Cuenta no existe o contraseña incorrecta.");
        }
    }

    private void regresarPantalla() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new MenuInicio().mostrarEnFrame();
    }

    private ImageIcon cargarImagen(String ruta) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(ruta);
        return (imgURL != null) ? new ImageIcon(imgURL) : new ImageIcon();
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Iniciar Sesion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


