/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.util.Properties;

public class MenuInicio extends JPanel {

    private static JFrame frame;
    private JButton iniciarSesion, crearUsuario, salir;
    private Image fondoImagen;
    private JLabel titulo;

    public MenuInicio() {
        setLayout(null);
        fondoImagen = new ImageIcon(getClass().getResource("/imagenes/MenuInicio.png")).getImage();

        titulo = new JLabel("", SwingConstants.CENTER); // Lo dejo vacío y se actualiza abajo
        titulo.setFont(new Font("Pixel Font", Font.BOLD, 40));
        titulo.setForeground(new Color(255, 105, 180)); // Rosado
        titulo.setBounds(200, 90, 400, 50);
        add(titulo);

        iniciarSesion = crearBoton("", new Color(0xA2F255), new Color(0x026610));
        iniciarSesion.setBounds(310, 230, 180, 50);
        iniciarSesion.addActionListener(e -> abrirIniciarSesion());
        add(iniciarSesion);

        crearUsuario = crearBoton("", new Color(0x0CC0DF), new Color(0x03579C));
        crearUsuario.setBounds(310, 300, 180, 50);
        crearUsuario.addActionListener(e -> abrirCrearUsuario());
        add(crearUsuario);

        salir = crearBoton("", new Color(0xFFECF4), new Color(0xFA237F));
        salir.setBounds(310, 370, 180, 50);
        salir.addActionListener(e -> System.exit(0));
        add(salir);

        actualizarIdioma(); 
    }

    public void actualizarIdioma() {
        Properties mensajes = Idioma.getMensajes();
        titulo.setText(mensajes.getProperty("menu_inicio_titulo", "MENÚ"));
        iniciarSesion.setText(mensajes.getProperty("iniciar_sesion", "Iniciar Sesión"));
        crearUsuario.setText(mensajes.getProperty("crear_usuario", "Crear Usuario"));
        salir.setText(mensajes.getProperty("salir", "Salir"));
        repaint(); // Refresca la pantalla
    }
    
    public void cambiarIdioma(String idioma){
        Idioma.setIdiomaActual(idioma);
        actualizarIdioma();  
    }

    private JButton crearBoton(String texto, Color bgColor, Color fgColor) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Pixel Font", Font.BOLD, 18));
        boton.setForeground(fgColor);
        boton.setBackground(bgColor);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);
        return boton;
    }
    

    private void abrirCrearUsuario() {
        frame.setVisible(false);
        new CrearCuenta().mostrarEnFrame();
    }

    private void abrirIniciarSesion() {
        frame.setVisible(false);
        new IniciarSesion().mostrarEnFrame();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondoImagen, 0, 0, getWidth(), getHeight(), this);
    }

    public void mostrarEnFrame() {
        if (frame == null) {
            frame = new JFrame("Menu Inicio");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(this);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else {
            frame.setContentPane(this);
            frame.revalidate();
            frame.repaint();
            frame.setVisible(true);
            frame.toFront();
        }
    }

}

