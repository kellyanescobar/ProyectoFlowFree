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

public class VerPerfil extends JPanel {
    private JTextArea datosArea;
    private JButton cambiarContraseña, eliminarCuenta, regresar;
    private JLabel icono, titulo;

    public VerPerfil() {
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

        titulo = new JLabel("Ver Perfil", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridy = 1;
        add(titulo, gbc);

        datosArea = new JTextArea(5, 20);
        datosArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(datosArea);
        gbc.gridy = 2;
        add(scrollPane, gbc);

        JPanel botonesPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        botonesPanel.setPreferredSize(new Dimension(300, 50));

        cambiarContraseña = new JButton("Cambiar Contraseña");
        cambiarContraseña.addActionListener(e -> mostrarDialogoCambioContraseña());
        botonesPanel.add(cambiarContraseña);

        eliminarCuenta = new JButton("Eliminar Cuenta");
        eliminarCuenta.addActionListener(e -> confirmarEliminarCuenta());
        botonesPanel.add(eliminarCuenta);

        regresar = new JButton("Regresar al Menu");
        regresar.addActionListener(e -> regresarMenu());
        botonesPanel.add(regresar);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(botonesPanel, gbc);

        mostrarDatosPerfil();
    }

    private void mostrarDatosPerfil() {
       
    }

    private void mostrarDialogoCambioContraseña() {
        if (Login.usuarioLogueado == null) {
            JOptionPane.showMessageDialog(null, "No hay usuario logueado.");
            return;
        }

        JPasswordField actualField = new JPasswordField();
        JPasswordField nuevaField = new JPasswordField();
        Object[] message = {
            "Contraseña Actual:", actualField,
            "Nueva Contraseña (5 caracteres):", nuevaField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Cambiar Contraseña", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String actual = new String(actualField.getPassword());
            String nueva = new String(nuevaField.getPassword());


            if (nueva.length() != 5) {
                JOptionPane.showMessageDialog(null, "La nueva contraseña debe tener exactamente 5 caracteres.");
                return;
            }

            
        }
    }

    private void confirmarEliminarCuenta() {
        if (Login.usuarioLogueado == null) {
            JOptionPane.showMessageDialog(null, "No hay usuario logueado.");
            return;
        }

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
        JFrame frame = new JFrame("Ver Perfil");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
