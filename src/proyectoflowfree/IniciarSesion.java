/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 *
 * @author laraj
 */

public class IniciarSesion extends JPanel {
    private JTextField usuario;
    private JPasswordField contra;
    private JButton ingresar, regresar;
    private JLabel lblUsuario, lblContraseña;
    private Image fondoImagen;

    public IniciarSesion() {
        setLayout(null);

        fondoImagen = new ImageIcon(getClass().getResource("/imagenes/IniciarSesion.png")).getImage();

        // lbl Nombre de Usuario
        lblUsuario = crearEtiqueta("Nombre de Usuario:");
        lblUsuario.setBounds(270, 200, 250, 30);
        add(lblUsuario);

        //Entrada Nombre de Usuario
        usuario = crearCampoTexto();
        usuario.setBounds(270, 230, 250, 40);
        add(usuario);

        //lbl Contraseña
        lblContraseña = crearEtiqueta("Contraseña:");
        lblContraseña.setBounds(270, 270, 250, 30);
        add(lblContraseña);

        //Entrada de Contraseña
        contra = new JPasswordField();
        estilizarCampo(contra);
        contra.setBounds(270, 300, 250, 40);
        add(contra);

        // Boton Iniciar Secion
        ingresar = crearBoton("Iniciar Sesion", new Color(0xA2F255), new Color(0x026610));
        ingresar.setBounds(270, 370, 250, 50);
        ingresar.addActionListener(e -> iniciarSesion());
        add(ingresar);

        // Boton Regresar
        regresar = crearBoton("Regresar", new Color(0xFFECF4), new Color(0xFA237F));
        regresar.setBounds(270, 440, 250, 50);
        regresar.addActionListener(e -> regresarPantalla());
        add(regresar);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto, SwingConstants.LEFT);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 16));
        etiqueta.setForeground(Color.WHITE);
        return etiqueta;
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

    private void iniciarSesion() {
        String usuarioIngresado = usuario.getText();
        String passwordIngresado = new String(contra.getPassword());

        if (Login.iniciarSesion(usuarioIngresado, passwordIngresado)) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
            new MenuPrincipal(Login.cargarDatos(usuarioIngresado)).mostrarEnFrame();
        //} else {
            //JOptionPane.showMessageDialog(null, "Cuenta no existe o contraseña incorrecta.");
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
        JFrame frame = new JFrame("Iniciar Sesion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
   
}
