/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;
import java.awt.*;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.*;
/**
 *
 * @author laraj
 */
public class Reportes extends JPanel {
    private JButton verRanking, estadisticas, regresar;
    private JLabel icono, titulo;
    private Image fondoImagen;

    public Reportes() {
        Properties mensajes = Idioma.getMensajes(); 

        setLayout(null);
        fondoImagen = new ImageIcon(getClass().getResource("/imagenes/Reportes.png")).getImage();

       
        ImageIcon imagenIcono = cargarImagen("imagenes/Icono.jpeg");
        icono = (imagenIcono != null) ? new JLabel(imagenIcono) : new JLabel("Imagen no encontrada");
        icono.setBounds(250, 30, 300, 50);  // Corrige posición
        add(icono);

        // Título dinámico ROSADO
        titulo = new JLabel(mensajes.getProperty("titulo_reportes", "REPORTES"), SwingConstants.CENTER);
        titulo.setFont(new Font("Pixel Font", Font.BOLD, 40)); 
        titulo.setForeground(new Color(255, 105, 180)); 
        titulo.setBounds(200, 90, 400, 50); 
        add(titulo);

        // Botón Ranking
        verRanking = crearBoton(mensajes.getProperty("ver_ranking", "VER RANKING"), new Color(251, 210, 255), new Color(199, 0, 255));
        verRanking.setBounds(300, 220, 200, 50);
        verRanking.addActionListener(e -> abrirRanking());
        add(verRanking);

        // Botón Estadísticas
        estadisticas = crearBoton(mensajes.getProperty("estadisticas", "Estadísticas"), new Color(251, 210, 255), new Color(199, 0, 255));
        estadisticas.setBounds(300, 300, 200, 50);
        estadisticas.addActionListener(e -> abrirEstadisticas());
        add(estadisticas);

        // Botón Regresar
        regresar = crearBoton(mensajes.getProperty("regresar", "REGRESAR"), new Color(246, 176, 164), new Color(234, 89, 35));
        regresar.setBounds(300, 380, 200, 50);
        regresar.addActionListener(e -> regresarMenu());
        add(regresar);
    }

    private JButton crearBoton(String texto, Color bgColor, Color fgColor) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Pixel Font", Font.BOLD, 16));
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

    private void abrirRanking() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new Ranking().mostrarEnFrame();
    }

    private void abrirEstadisticas() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new Estadisticas().mostrarEnFrame();
    }

    private void regresarMenu() {
        if (Login.usuarioLogueado == null) {
            JOptionPane.showMessageDialog(null, "No hay usuario logueado");
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondoImagen, 0, 0, getWidth(), getHeight(), this);
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Reportes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
