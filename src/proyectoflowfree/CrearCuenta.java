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

public class CrearCuenta extends JPanel {
    private JTextField usuario;
    private JPasswordField contra;
    private JTextField nombreCompleto;
    private JButton guardar, regresar;
    private JLabel icono;

    public CrearCuenta() {
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

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        formPanel.add(new JLabel("Nombre Completo:", SwingConstants.RIGHT));
        nombreCompleto = new JTextField(15);
        formPanel.add(nombreCompleto);

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

        guardar = new JButton("Crear Cuenta");
        guardar.addActionListener(e -> crearUsuario());
        buttonPanel.add(guardar);

        regresar = new JButton("Regresar");
        regresar.addActionListener(e -> regresarPantalla());
        buttonPanel.add(regresar);

        gbc.gridy = 2;
        add(buttonPanel, gbc);
    }

    private void crearUsuario() {
        String nombre = usuario.getText();
        String password = new String(contra.getPassword());
        String nombreCompletoStr = nombreCompleto.getText();

        if (Login.registrarUsuario(nombre, password, nombreCompletoStr)) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
            new MenuPrincipal(Login.cargarDatos(nombre)).mostrarEnFrame();
        } else {
            JOptionPane.showMessageDialog(null, "Error al crear usuario.");
        }
    }

    private void regresarPantalla() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new MenuInicio().mostrarEnFrame();
    }

    private ImageIcon cargarImagen(String ruta) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(ruta);
        return (imgURL != null) ? new ImageIcon(imgURL) : null;
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Crear Cuenta");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}