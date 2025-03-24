/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;

import NIVELES.MapaNivelesBonito;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.*;

/**
 *
 * @author laraj
 */
public class MenuPrincipal extends JPanel {

    private JButton jugar, verPerfil, reportes, cerrarSesion;
    private Image fondoImagen;
    private Login log;

    public MenuPrincipal(Login log) {

        this.log = log;
        setLayout(null);

        fondoImagen = new ImageIcon(getClass().getResource("/imagenes/MenuPrincipal.png")).getImage();

        String rutaArchivo = "/proyectoflowfree/idiomas/mensajes_" + Idioma.getIdiomaActual() + ".properties";
        InputStream archivo = getClass().getResourceAsStream(rutaArchivo);
        Properties mensajes = Idioma.getMensajes();

        JLabel titulo = new JLabel(mensajes.getProperty("menu_principal_titulo", "MENÚ"), SwingConstants.CENTER);
        titulo.setFont(new Font("Pixel Font", Font.BOLD, 40));
        titulo.setForeground(new Color(255, 105, 180));
        titulo.setBounds(200, 90, 400, 50);
        add(titulo);

        jugar = crearBoton("Jugar", new Color(0xFBD2FF), new Color(0xC700FF));
        verPerfil = crearBoton("Ver Perfil", new Color(0xFAC9DE), new Color(0xE61F93));
        reportes = crearBoton("Reportes", new Color(0xC8FFF7), new Color(0x00B7E7));
        cerrarSesion = crearBoton("Cerrar Sesion", new Color(0xF6B0A4), new Color(0xEA5923));

        add(jugar);
        add(verPerfil);
        add(reportes);
        add(cerrarSesion);
        actualizarTextos(mensajes);

        jugar.addActionListener(e -> abrirMapa());
        verPerfil.addActionListener(e -> abrirVerPerfil());
        reportes.addActionListener(e -> abrirReportes());
        cerrarSesion.addActionListener(e -> cerrarSesion());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                reubicarBotones();
            }
        });
    }

    private void actualizarTextos(Properties mensajes) {
        jugar.setText(mensajes.getProperty("jugar"));
        verPerfil.setText(mensajes.getProperty("ver_perfil"));
        reportes.setText(mensajes.getProperty("reportes"));
        cerrarSesion.setText(mensajes.getProperty("cerrar_sesion"));
    }

    private void reubicarBotones() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int buttonWidth = 180;
        int buttonHeight = 50;
        int centerX = (panelWidth - buttonWidth) / 2;
        int startY = panelHeight / 3;
        int spacing = 70;

        jugar.setBounds(centerX, startY, buttonWidth, buttonHeight);
        verPerfil.setBounds(centerX, startY + spacing, buttonWidth, buttonHeight);
        reportes.setBounds(centerX, startY + 2 * spacing, buttonWidth, buttonHeight);
        cerrarSesion.setBounds(centerX, startY + 3 * spacing, buttonWidth, buttonHeight);
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
        new Reportes().mostrarEnFrame();
    }

    public void cambiarIdioma(String idioma){
        Idioma.setIdiomaActual(idioma);
        MenuInicio menu=new MenuInicio();
        menu.mostrarEnFrame();
    }
    
    private void cerrarSesion() {
    Login.cerrarSesion();  // Guarda y limpia
    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
    frame.dispose();

    MenuInicio menu = new MenuInicio();
    menu.mostrarEnFrame();
    menu.actualizarIdioma();  // Refresca con el idioma actual (Español o Ingles)
}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondoImagen, 0, 0, getWidth(), getHeight(), this);
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
