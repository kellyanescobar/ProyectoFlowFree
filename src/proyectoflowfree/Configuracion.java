/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;

import java.awt.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.InputStream;
import java.util.Properties;
import musica.musica;

/**
 *
 * @author laraj
 */
public class Configuracion extends JPanel {

    private JButton idioma, cambiarNombre, cambiarUsuario, cambiarContra, avatar, eliminarCuenta, volumen, regresar;
    private Image fondoImagen;
    private Clip clip;
    private float volumenActual = 0f;
    private Properties mensajes = new Properties();
    private String idiomaActual = "es";

    private JButton[] avatares;
    private String[] nombresAvatares = {"Rosado", "Azul", "Turquesa", "Amarillo", "Rojo", "Verde"};
    private String[] rutasAvatares = {
        "/imagenes/AvatarRosado.png",
        "/imagenes/AvatarAzul.png",
        "/imagenes/AvatarTurquesa.png",
        "/imagenes/AvatarAmarillo.png",
        "/imagenes/AvatarRojo.png",
        "/imagenes/AvatarVerde.png"
    };

    public Configuracion() {
        setLayout(null);
        cargarIdioma(idiomaActual);
        Idioma.setIdiomaActual("en");

        fondoImagen = new ImageIcon(getClass().getResource("/imagenes/Configuracion.png")).getImage();
        JLabel titulo = new JLabel(mensajes.getProperty("configuracion_titulo", "CONFIGURACIÓN"), SwingConstants.CENTER);
        titulo.setFont(new Font("Pixel Font", Font.BOLD, 40)); 
        titulo.setForeground(new Color(255, 105, 180));
        titulo.setBounds(200, 50, 400, 50);
        add(titulo);
        // Boton Idioma
        idioma = crearBoton("IDIOMA", new Color(255, 245, 202), new Color(255, 205, 0));
        idioma.setBounds(180, 170, 180, 50);
        idioma.addActionListener(e -> cambiarIdioma());
        add(idioma);

        // Boton Avatar
        avatar = crearBoton("AVATAR", new Color(250, 201, 222), new Color(230, 31, 147));
        avatar.setBounds(180, 240, 180, 50);
        avatar.addActionListener(e -> mostrarSeleccionAvatares());
        add(avatar);

        // Boton Eliminar Cuenta
        eliminarCuenta = crearBoton("ELIMINAR CUENTA", new Color(247, 186, 186), new Color(255, 49, 49));
        eliminarCuenta.setBounds(180, 310, 180, 50);
        eliminarCuenta.addActionListener(e -> eliminarCuenta());
        add(eliminarCuenta);

        // Boton Cambiar Nombre
        cambiarNombre = crearBoton("CAMBIAR NOMBRE", new Color(251, 210, 255), new Color(199, 0, 255));
        cambiarNombre.setBounds(430, 170, 180, 50);
        cambiarNombre.addActionListener(e -> cambiarNombre());
        add(cambiarNombre);

        // Boton Cambiar Usuario
        cambiarUsuario = crearBoton("CAMBIAR USUARIO", new Color(200, 255, 247), new Color(0, 183, 231));
        cambiarUsuario.setBounds(430, 240, 180, 50);
        cambiarUsuario.addActionListener(e -> cambiarUsuario());
        add(cambiarUsuario);

        // Boton Cambiar Contraseña
        cambiarContra = crearBoton("CAMBIAR CONTRA", new Color(204, 255, 155), new Color(14, 184, 102));
        cambiarContra.setBounds(430, 310, 180, 50);
        cambiarContra.addActionListener(e -> cambiarContraseña());
        add(cambiarContra);

        //boton volumen
        volumen = crearBoton("VOLUMEN", new Color(255, 223, 160), new Color(255, 140, 0));
        volumen.setBounds(310, 380, 180, 50);
        volumen.addActionListener(e -> mostrarControlDeVolumen());
        add(volumen);

        // Boton Regresar
        regresar = crearBoton("REGRESAR", new Color(246, 176, 164), new Color(234, 89, 35));
        regresar.setBounds(460, 460, 180, 50);
        regresar.addActionListener(e -> regresar());
        add(regresar);

    }

    private JButton crearBoton(String texto, Color bgColor, Color fgColor) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Pixel Font", Font.BOLD, 14));
        boton.setForeground(fgColor);
        boton.setBackground(bgColor);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);

        Color originalBg = bgColor;
        Color hoverBg = bgColor.darker();

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(hoverBg);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(originalBg);
            }
        });

        return boton;
    }

    private void cambiarNombre() {
        if (Login.usuarioLogueado == null) {
            JOptionPane.showMessageDialog(this, "No hay usuario logueado.");
            return;
        }

        String nuevoNombre = JOptionPane.showInputDialog(this, "Ingrese su nuevo nombre:");
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            Login.usuarioLogueado.cambiarNombre(nuevoNombre);
            JOptionPane.showMessageDialog(this, "Nombre cambiado a " + nuevoNombre);
        }
    }

    private void cambiarUsuario() {
        if (Login.usuarioLogueado == null) {
            JOptionPane.showMessageDialog(this, "No hay usuario logueado.");
            return;
        }

        String nuevoUsuario = JOptionPane.showInputDialog(this, "Ingrese su nuevo usuario:");
        if (nuevoUsuario != null && !nuevoUsuario.trim().isEmpty()) {
            if (Login.usuarioLogueado.cambiarUsuario(nuevoUsuario)) {
                JOptionPane.showMessageDialog(this, "Usuario cambiado exitosamente a " + nuevoUsuario);
            }
        }
    }

    private void mostrarSeleccionAvatares() {
        JFrame avatarFrame = new JFrame("Selecciona tu Avatar");
        avatarFrame.setSize(600, 550);
        avatarFrame.setLayout(new BorderLayout());
        avatarFrame.getContentPane().setBackground(Color.BLACK);

        JPanel panelAvatares = new JPanel(new GridLayout(2, 3, 10, 10));
        panelAvatares.setBackground(Color.BLACK);

        avatares = new JButton[rutasAvatares.length];
        final int[] avatarSeleccionado = {-1};

        for (int i = 0; i < rutasAvatares.length; i++) {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutasAvatares[i]));
            Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);

            avatares[i] = new JButton(iconoRedimensionado);
            avatares[i].setBackground(Color.BLACK);
            avatares[i].setBorderPainted(false);

            int index = i;
            avatares[i].addActionListener(e -> {
                avatarSeleccionado[0] = index;
                for (JButton btn : avatares) {
                    btn.setBorderPainted(false);
                }
                avatares[index].setBorderPainted(true);
                avatares[index].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
            });

            panelAvatares.add(avatares[i]);
        }

        JButton botonElegir = new JButton("ELEGIR");
        botonElegir.setFont(new Font("Arial", Font.BOLD, 18));
        botonElegir.setBackground(new Color(0, 183, 231));
        botonElegir.setForeground(Color.WHITE);
        botonElegir.setFocusPainted(false);

        botonElegir.addActionListener(e -> {
            if (avatarSeleccionado[0] != -1) {
                if (Login.usuarioLogueado != null) {
                    Login.usuarioLogueado.setAvatar(rutasAvatares[avatarSeleccionado[0]], nombresAvatares[avatarSeleccionado[0]]);
                    Login.usuarioLogueado.guardarDatos();

                    JOptionPane.showMessageDialog(avatarFrame, "Has elegido el avatar: " + nombresAvatares[avatarSeleccionado[0]]);

                    avatarFrame.dispose();
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    if (frame != null) {
                        frame.dispose();
                    }
                    new VerPerfil().mostrarEnFrame();
                }
            } else {
                JOptionPane.showMessageDialog(avatarFrame, "Por favor, selecciona un avatar antes de continuar.");
            }
        });

        avatarFrame.add(panelAvatares, BorderLayout.CENTER);
        avatarFrame.add(botonElegir, BorderLayout.SOUTH);

        avatarFrame.setLocationRelativeTo(this);
        avatarFrame.setVisible(true);
    }

    private void cambiarContraseña() {
    if (Login.usuarioLogueado == null) {
        JOptionPane.showMessageDialog(this, "No hay usuario logueado.");
        return;
    }
    JPasswordField nuevaContraseña = new JPasswordField();
    Object[] message = {"Nueva Contraseña:", nuevaContraseña};
    int option = JOptionPane.showConfirmDialog(this, message, "Cambiar Contraseña", JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
        String nueva = new String(nuevaContraseña.getPassword());

        if (nueva.length() >= 5) {
            boolean cambio = Login.usuarioLogueado.cambiarContraseña(nueva);
            if (cambio) {
                JOptionPane.showMessageDialog(this, "¡Contraseña cambiada exitosamente!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 5 caracteres.");
        }
    }
}

    private void eliminarCuenta() {
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Esta seguro de eliminar su cuenta?", "Confirmacion",
                JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Cuenta eliminada exitosamente.");
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
            new MenuInicio().mostrarEnFrame();
        }
    }

    private void cambiarAvatar() {
        JOptionPane.showMessageDialog(this, "Funcion de cambio de avatar en desarrollo, todavia me falta");
    }

      private void mostrarControlDeVolumen() {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (musica.getVolume() * 100));
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        int opcion = JOptionPane.showConfirmDialog(this, slider, "Ajustar Volumen", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            float volumenSeleccionado = slider.getValue() / 100f;
            musica.setVolume(volumenSeleccionado);
        }
    }

    private void cargarIdioma(String idioma) {
        try {
            String rutaArchivo = "/proyectoflowfree/idiomas/mensajes_" + idioma + ".properties";
            InputStream archivo = getClass().getResourceAsStream(rutaArchivo);

            if (archivo == null) {
                System.out.println(" ERROR: No se encontró el archivo de idioma en " + rutaArchivo);
                return;
            }

            mensajes.load(archivo);
            archivo.close();

            System.out.println(" Archivo de idioma cargado correctamente: " + rutaArchivo);
        } catch (Exception e) {
            System.out.println(" No se pudo cargar el archivo de idioma: " + e.getMessage());
        }
    }

    private void cambiarIdioma() {
        String[] opciones = {"Español", "English"};
        int seleccion = JOptionPane.showOptionDialog(this, "Selecciona el idioma:", "Idioma",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == 1) {
            Idioma.setIdiomaActual("en");  // Guarda el idioma globalmente
        } else {
            Idioma.setIdiomaActual("es");
        }
        cargarIdioma(Idioma.getIdiomaActual());
        actualizarTextos();
    }

    private void actualizarTextos() {
        idioma.setText(mensajes.getProperty("idioma"));
        avatar.setText(mensajes.getProperty("avatar"));
        eliminarCuenta.setText(mensajes.getProperty("eliminar_cuenta"));
        cambiarNombre.setText(mensajes.getProperty("cambiar_nombre"));
        cambiarUsuario.setText(mensajes.getProperty("cambiar_usuario"));
        cambiarContra.setText(mensajes.getProperty("cambiar_contra"));
        volumen.setText(mensajes.getProperty("volumen"));
        regresar.setText(mensajes.getProperty("regresar"));
    }

    private void regresar() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new VerPerfil().mostrarEnFrame();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondoImagen, 0, 0, getWidth(), getHeight(), this);
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Configuración");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Configuracion().mostrarEnFrame());
    }
}
