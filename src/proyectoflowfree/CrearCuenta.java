/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
/**
 *
 * @author laraj
 */

public class CrearCuenta extends JPanel {
    private JTextField usuario, nombreCompleto;
    private JPasswordField contra;
    private JButton guardar, regresar;
    private JLabel lblNombre, lblUsuario, lblContra; 
    private Image fondoImagen;

    public CrearCuenta() {
        Properties mensajes = Idioma.getMensajes();
        setLayout(null);

        fondoImagen = new ImageIcon(getClass().getResource("/imagenes/CrearCuenta.png")).getImage();
        JLabel titulo = new JLabel(mensajes.getProperty("crear_cuenta_titulo"), SwingConstants.CENTER);
        titulo.setFont(new Font("Pixel Font", Font.BOLD, 40)); // Tamaño 40 y Negrita
        titulo.setForeground(new Color(255, 105, 180)); // Color Rosado
        titulo.setBounds(200, 30, 400, 50); // Ajusta posición si es necesario
        add(titulo);
        //lbl del Nombre completo
        lblNombre = crearEtiqueta("Nombre Completo:");
        lblNombre.setBounds(270, 170, 250, 30);
        add(lblNombre);
        
        //Entrada Nombre completo
        nombreCompleto = crearCampoTexto();
        nombreCompleto.setBounds(270, 200, 250, 40);
        add(nombreCompleto);
        
        //lbl de Nombre de usuario
        lblUsuario = crearEtiqueta("Nombre de Usuario:");
        lblUsuario.setBounds(270, 230, 250, 30);
        add(lblUsuario);

        //Entrada Nombre de usuario
        usuario = crearCampoTexto();
        usuario.setBounds(270, 260, 250, 40);
        add(usuario);

        //lbl Contraseña
        lblContra = crearEtiqueta("Contraseña:");
        lblContra.setBounds(270, 290, 250, 30);
        add(lblContra);

        //Entrada para Contraseña
        contra = new JPasswordField();
        estilizarCampo(contra);
        contra.setBounds(270, 320, 250, 40);
        add(contra);

        //Boton para crear cuenta
        guardar = crearBoton("Crear Cuenta", new Color(0xA2F255), new Color(0x026610));
        guardar.setBounds(270, 380, 250, 50);
        guardar.addActionListener(e -> crearUsuario());
        add(guardar);

        //Boton para regresar
        regresar = crearBoton("Regresar", new Color(0xFFECF4), new Color(0xFA237F));
        regresar.setBounds(270, 450, 250, 50);
        regresar.addActionListener(e -> regresarPantalla());
        add(regresar);
        actualizarTextos(mensajes);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto, SwingConstants.LEFT);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 16));
        etiqueta.setForeground(Color.WHITE); 
        return etiqueta;
    }
    
    private void actualizarTextos(Properties mensajes) {
    lblNombre.setText(mensajes.getProperty("nombre_completo"));
    lblUsuario.setText(mensajes.getProperty("nombre_usuario"));
    lblContra.setText(mensajes.getProperty("contraseña"));
    guardar.setText(mensajes.getProperty("crear_cuenta"));
    regresar.setText(mensajes.getProperty("regresar"));
}


    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        estilizarCampo(campo);
        return campo;
    }

    private void estilizarCampo(JComponent campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, 18));
        campo.setForeground(Color.BLACK);
        campo.setBackground(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x026610), 2), 
                BorderFactory.createEmptyBorder(5, 10, 5, 10) 
        ));
    }

    private JButton crearBoton(String texto, Color bgColor, Color fgColor) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Pixel Font", Font.BOLD, 18));
        boton.setForeground(fgColor);
        boton.setBackground(bgColor);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(bgColor.darker()); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(bgColor); 
            }

            @Override
            public void mousePressed(MouseEvent e) {
                boton.setBackground(bgColor.darker().darker()); 
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                boton.setBackground(bgColor.darker()); 
            }
        });

        return boton;
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondoImagen, 0, 0, getWidth(), getHeight(), this);
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Crear Cuenta");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
