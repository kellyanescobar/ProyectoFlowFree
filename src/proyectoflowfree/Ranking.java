/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoflowfree;
import java.awt.*;
import java.io.File;
import javax.swing.*;
/**
 *
 * @author laraj
 */
public class Ranking extends JPanel {
    private JTextArea rankingArea;
    private JButton regresar;
    private JLabel icono, titulo;
    private Image fondoImagen;

    public Ranking() {
        setLayout(null); 

        fondoImagen = new ImageIcon(getClass().getResource("/imagenes/RankingJugadores.png")).getImage();

        ImageIcon imagenIcono = cargarImagen("imagenes/Icono.jpg");
        icono = (imagenIcono != null) ? new JLabel(imagenIcono) : new JLabel("Imagen no encontrada");
        icono.setBounds(300, 20, 200, 100);
        add(icono);

        titulo = new JLabel("RANKING DE JUGADORES", SwingConstants.CENTER);
        titulo.setFont(new Font("Pixel Font", Font.BOLD, 20));
        titulo.setForeground(Color.BLACK);
        titulo.setBounds(230, 130, 350, 40);
        add(titulo);

        rankingArea = new JTextArea();
        rankingArea.setEditable(false);
        rankingArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(rankingArea);
        scrollPane.setBounds(200, 190, 400, 200);
        add(scrollPane);

        regresar = crearBoton("REGRESAR", new Color(246, 176, 164), new Color(234, 89, 35));
        regresar.setBounds(300, 420, 200, 50);
        regresar.addActionListener(e -> regresarMenu());
        add(regresar);
        mostrarRanking();
    }

    private JButton crearBoton(String texto, Color bgColor, Color fgColor) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Pixel Font", Font.BOLD, 16));
        boton.setForeground(fgColor);
        boton.setBackground(bgColor);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);

        Color hoverBg = bgColor.darker();
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(hoverBg);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(bgColor);
            }
        });
        return boton;
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

    private void mostrarRanking() {
    File usuariosDir = new File("usuarios/");
    StringBuilder rankingTexto = new StringBuilder();

    if (usuariosDir.exists() && usuariosDir.isDirectory()) {
        File[] usuarios = usuariosDir.listFiles();

        for (File userDir : usuarios) {
            if (userDir.isDirectory()) {
                Login user = Login.cargarDatos(userDir.getName());
                if (user != null) {
                    rankingTexto.append("Jugador: ")
                                .append(user.getUsuario())
                                .append(" | Nivel Alcanzado: ")
                                .append(user.getNivelAlcanzado())
                                .append("/5\n");
                }
            }
        }
    }
    rankingArea.setText(rankingTexto.toString());
}

    private void regresarMenu() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new Reportes().mostrarEnFrame();
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Ranking de Jugadores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ranking().mostrarEnFrame());
    }
}
