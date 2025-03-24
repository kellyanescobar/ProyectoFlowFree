/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectoflowfree;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.*;
import java.util.Properties;
/**
 *
 * @author laraj
 */
public class Estadisticas extends JPanel {
    private JLabel icono, titulo, partidas, puntos, completados, tiempo;
    private JButton regresar;
    private Image fondoImagen;

    public Estadisticas() {
        setLayout(null);
        Properties mensajes = Idioma.getMensajes();

        fondoImagen = new ImageIcon(getClass().getResource("/imagenes/Estadistica.png")).getImage();

        ImageIcon imagenIcono = cargarImagen("imagenes/Icono.jpeg");
        icono = (imagenIcono != null) ? new JLabel(imagenIcono) : new JLabel("Imagen no encontrada");
        icono.setBounds(300, 20, 200, 100);
        add(icono);

        titulo = new JLabel(mensajes.getProperty("estadistica_titulo", "ESTADÍSTICAS"), SwingConstants.CENTER);
        titulo.setFont(new Font("Pixel Font", Font.BOLD, 40)); 
        titulo.setForeground(new Color(255, 105, 180)); 
        titulo.setBounds(200, 90, 400, 50); 
        add(titulo);

        Login usuario = Login.usuarioLogueado;

        int partidasJugadas = (usuario != null) ? usuario.getPartidasJugadas() : 0;
        int puntosTotales = (usuario != null) ? usuario.getPuntos() : 0;
        int nivelAlcanzado = (usuario != null) ? usuario.getNivelAlcanzado() : 0;
        int tiempoJugado = (usuario != null) ? usuario.getTiempoJugado() : 0;

        partidas = crearLabel(mensajes.getProperty("partidas_jugadas", "Partidas Jugadas") + ": " + partidasJugadas, 200);
        puntos = crearLabel(mensajes.getProperty("puntos_totales", "Puntos Totales") + ": " + puntosTotales, 250);
        completados = crearLabel(mensajes.getProperty("juegos_completados", "Juegos Completados") + ": " + (nivelAlcanzado - 1), 300);
        tiempo = crearLabel(mensajes.getProperty("tiempo_jugado", "Tiempo Jugado (min)") + ": " + tiempoJugado, 350);

        add(partidas);
        add(puntos);
        add(completados);
        add(tiempo);

        regresar = crearBoton(mensajes.getProperty("regresar", "REGRESAR"), new Color(246, 176, 164), new Color(234, 89, 35));
        regresar.setBounds(300, 420, 200, 50);
        regresar.addActionListener(e -> regresarMenu());
        add(regresar);
    }

    private JLabel crearLabel(String texto, int y) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(new Font("Pixel Font", Font.BOLD, 18));
        label.setBounds(200, y, 400, 40);
        return label;
    }

    private JButton crearBoton(String texto, Color bgColor, Color fgColor) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Pixel Font", Font.BOLD, 16));
        boton.setForeground(fgColor);
        boton.setBackground(bgColor);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        return boton;
    }

    private void regresarMenu() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new Reportes().mostrarEnFrame();
    }

    private ImageIcon cargarImagen(String ruta) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(ruta);
        return (imgURL != null) ? new ImageIcon(imgURL) : new ImageIcon();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondoImagen, 0, 0, getWidth(), getHeight(), this);
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Estadisticas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Estadisticas().mostrarEnFrame());
    }
}

