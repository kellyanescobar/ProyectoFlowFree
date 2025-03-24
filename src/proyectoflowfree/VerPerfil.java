/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.*;
/**
 *
 * @author laraj
 */

public class VerPerfil extends JPanel {
    private JLabel avatar, nombreAvatar;
    private JLabel usuario, nombre, fecha; 
    private JButton configuracion, regresar;
    private Image fondoImagen;

    public VerPerfil() {
     String rutaArchivo = "/proyectoflowfree/idiomas/mensajes_" + Idioma.getIdiomaActual() + ".properties";
     Properties mensajes = Idioma.getMensajes();


        setLayout(null);

        fondoImagen = new ImageIcon(getClass().getResource("/imagenes/Perfil.png")).getImage();

        JLabel titulo = new JLabel(mensajes.getProperty("titulo_ver_perfil", "PERFIL"), SwingConstants.CENTER);
        titulo.setFont(new Font("Pixel Font", Font.BOLD, 40)); 
        titulo.setForeground(new Color(255, 105, 180)); 
        titulo.setBounds(250, 90, 300, 50); 
        add(titulo);

        String avatarPath = (Login.usuarioLogueado != null) ? Login.usuarioLogueado.getAvatar() : null;
        URL avatarUrl = (avatarPath != null && !avatarPath.equals("default.png")) ? getClass().getResource(avatarPath) : null;
        
        avatar = new JLabel();
        avatar.setOpaque(true);
        avatar.setBackground(Color.WHITE);
        avatar.setBounds(30, 200, 200, 200);
        add(avatar);

        if (avatarUrl != null) {
            ImageIcon imagenAvatar = new ImageIcon(avatarUrl);
            Image imagenRedimensionada = imagenAvatar.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            avatar.setIcon(new ImageIcon(imagenRedimensionada));
        }
        
        // Usuario
       String nombreDeUsuario=(Login.usuarioLogueado !=null)? Login.usuarioLogueado.getUsuario(): "Sin Usuario";
       usuario = crearEtiquetaNoEditable("Usuario: "+nombreDeUsuario, new Color(247, 186, 186), new Color(255, 49, 49));
       usuario.setBounds(30, 410, 200, 50);
       add(usuario);

        // Nombre
        String nombreCompleto=(Login.usuarioLogueado !=null)? Login.usuarioLogueado.getNombreCompleto():"Sin nombre";
        nombre = crearEtiquetaNoEditable("Nombre: "+nombreCompleto, new Color(250, 201, 222), new Color(230, 31, 147));
        nombre.setBounds(300, 150, 200, 40);
        add(nombre);
        
        // Nombre del Avatar 
        String nombreAvatarSeleccionado = (Login.usuarioLogueado != null) ? Login.usuarioLogueado.getAvatarNombre() : "Sin Avatar";
        nombreAvatar = crearEtiquetaNoEditable("Avatar: " + nombreAvatarSeleccionado, new Color(200, 200, 255), new Color(50, 50, 255));
        nombreAvatar.setBounds(30, 150, 200, 40);
        add(nombreAvatar);

        // Fecha
        String fechaRegistro=(Login.usuarioLogueado !=null)? Login.usuarioLogueado.getFechaRegistro():"Sin fecha";
        fecha = crearEtiquetaNoEditable("Fecha: "+fechaRegistro, new Color(200, 255, 247), new Color(0, 183, 231));
        fecha.setBounds(300, 220, 200, 40);
        add(fecha);


        // Boton Configuracion 
        configuracion = crearBotonEditable("CONFIGURACION",new Color(251, 210, 255), new Color(199, 0, 255));
        configuracion.setBounds(300, 290, 200, 40);
        configuracion.addActionListener(e -> abrirConfiguracion());
        add(configuracion);

        // Boton Regresar 
        regresar = crearBotonEditable("REGRESAR", new Color(246, 176, 164), new Color(234, 89, 35));
        regresar.setBounds(300, 360, 200, 40);
        regresar.addActionListener(e -> regresarMenu());
        add(regresar);
        actualizarTextos(mensajes);
    }
    

    private JLabel crearEtiquetaNoEditable(String texto, Color bgColor, Color fgColor) {
    JLabel etiqueta = new JLabel(texto, SwingConstants.CENTER);
    etiqueta.setFont(new Font("Pixel Font", Font.BOLD, 14));
    etiqueta.setForeground(fgColor);  
    etiqueta.setOpaque(true);
    etiqueta.setBackground(bgColor);  
    etiqueta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
    return etiqueta;
}

private void actualizarTextos(Properties mensajes) {
    String nombreDeUsuario = (Login.usuarioLogueado != null) ? Login.usuarioLogueado.getUsuario() : mensajes.getProperty("sin_usuario");
    usuario.setText(mensajes.getProperty("usuario") + ": " + nombreDeUsuario);

    String nombreCompleto = (Login.usuarioLogueado != null) ? Login.usuarioLogueado.getNombreCompleto() : mensajes.getProperty("sin_nombre");
    nombre.setText(mensajes.getProperty("nombre") + ": " + nombreCompleto);

    String nombreAvatarSeleccionado = (Login.usuarioLogueado != null) ? Login.usuarioLogueado.getAvatarNombre() : mensajes.getProperty("sin_avatar");
    nombreAvatar.setText(mensajes.getProperty("avatar") + ": " + nombreAvatarSeleccionado);

    String fechaRegistro = (Login.usuarioLogueado != null) ? Login.usuarioLogueado.getFechaRegistro() : mensajes.getProperty("sin_fecha");
    fecha.setText(mensajes.getProperty("fecha") + ": " + fechaRegistro);

    configuracion.setText(mensajes.getProperty("configuracion"));
    regresar.setText(mensajes.getProperty("regresar"));
}

    
    private JButton crearBotonEditable(String texto, Color bgColor, Color fgColor) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Pixel Font", Font.BOLD, 14));
        boton.setForeground(fgColor);
        boton.setBackground(bgColor);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);
        
        Color originalBg=bgColor;
        Color hoverBg=bgColor.darker();
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt){
            boton.setBackground(hoverBg);
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt){
           boton.setBackground(originalBg); 
        }
        });
        
        return boton;
    }

    private void abrirConfiguracion() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new Configuracion().mostrarEnFrame();
    }

    private void regresarMenu() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new MenuPrincipal(Login.usuarioLogueado).mostrarEnFrame();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondoImagen, 0, 0, getWidth(), getHeight(), this);
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Ver Perfil");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VerPerfil().mostrarEnFrame();
        });
    }
}