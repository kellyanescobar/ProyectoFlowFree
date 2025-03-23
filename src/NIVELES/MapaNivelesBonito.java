/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NIVELES;

/**
 *
 * @author 50494
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectoflowfree.Idioma;
import proyectoflowfree.Login;
import proyectoflowfree.MenuPrincipal;

public class MapaNivelesBonito extends JPanel {

    private final int totalNiveles = 5;
    private boolean[] nivelesDesbloqueados = new boolean[totalNiveles];
    private HashMap<Integer, Point> posicionesNiveles;
    private Image fondo;
    private JButton botonRegresar;

    public MapaNivelesBonito() {
        Properties mensajes = Idioma.getMensajes();
        
        setPreferredSize(new Dimension(700, 400));
        setLayout(null);
        fondo = new ImageIcon(getClass().getResource("/imagenes/mapa2.png")).getImage();

        for (int i = 0; i < totalNiveles; i++) {
            nivelesDesbloqueados[i] = Login.usuarioLogueado != null && i < Login.usuarioLogueado.getNivelAlcanzado();
        }

        cargarPosicionesNiveles();
        configurarBotones();
        agregarBotonRegresar();
        actualizarTextos(mensajes);

    }
    
    private void actualizarTextos(Properties mensajes) {
    botonRegresar.setText(mensajes.getProperty("regresar")); 
}

    private void cargarPosicionesNiveles() {
        posicionesNiveles = new HashMap<>();
        posicionesNiveles.put(0, new Point(71, 275));   // Nivel 1
        posicionesNiveles.put(1, new Point(169, 53));   // Nivel 2
        posicionesNiveles.put(2, new Point(303, 318));  // Nivel 3
        posicionesNiveles.put(3, new Point(372, 104));  // Nivel 4
        posicionesNiveles.put(4, new Point(575, 185));  // Nivel 5
    }

    private void configurarBotones() {
        removeAll();
        setLayout(null);
        for (int i = 0; i < totalNiveles; i++) {
            Point pos = posicionesNiveles.get(i);
            String rutaImagen = "/imagenes/botonnivel" + (i + 1) + ".png";
            URL imgURL = getClass().getResource(rutaImagen);
            if (imgURL == null) {
                try {
                    imgURL = new File("C:/Users/50494/OneDrive/Pictures/botonnivel" + (i + 1) + ".png").toURI().toURL();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(MapaNivelesBonito.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            JButton boton;
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                boton = new JButton(new ImageIcon(img));
            } else {
                //System.out.println("Imagen no encontrada: " + rutaImagen);
                boton = new JButton("" + (i + 1));
            }
            boton.setContentAreaFilled(false);
            boton.setFocusPainted(false);
            boton.setOpaque(false);
            boton.setBounds(pos.x - 25, pos.y - 25, 50, 50);
            boton.setEnabled(nivelesDesbloqueados[i]);
            int nivel = i + 1;
            boton.addActionListener(e -> abrirNivel(nivel));
            add(boton);
        }
        if (botonRegresar != null) {
            add(botonRegresar);
        }

        revalidate();
        repaint();
    }

    private void agregarBotonRegresar() {
        botonRegresar = new JButton("REGRESAR");
        botonRegresar.setFont(new Font("Pixel Font", Font.BOLD, 14));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setBackground(new Color(234, 89, 35));
        botonRegresar.setBorderPainted(false);
        botonRegresar.setFocusPainted(false);
        botonRegresar.setBounds(540, 330, 130, 40);

        botonRegresar.addActionListener(e -> regresarMenu());
        add(botonRegresar);
    }

    private void abrirNivel(int nivel) {
        JFrame framemapa = new JFrame("Nivel " + nivel);
        framemapa.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        switch (nivel) {
            case 1:
                framemapa.add(new FlowFreeNivel1(this));
                break;
            case 2:
                framemapa.add(new FlowFreeNivel2(this));
                break;
            case 3:
                framemapa.add(new FlowFreeNivel3(this));
                break;
            case 4:
                framemapa.add(new FlowFreeNivel4(this));
                break;
            case 5:
                framemapa.add(new FlowFreeNivel5(this));
                break;
            default:
                JOptionPane.showMessageDialog(this, "Nivel " + nivel + " aún no está implementado.");
                return;
        }

        framemapa.pack();
        framemapa.setLocationRelativeTo(null);
        framemapa.setResizable(false);
        framemapa.setVisible(true);
        framemapa.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                desbloquearNivel(nivel);
            }
        });
    }

    public void desbloquearNivel(int nivelCompletado) {
        if (nivelCompletado < totalNiveles) {
            int siguienteNivel = nivelCompletado + 1;

            // Verifica si el siguiente nivel aún no está desbloqueado
            if (Login.usuarioLogueado.getNivelAlcanzado() <= nivelCompletado) {
                Login.usuarioLogueado.setNivelAlcanzado(siguienteNivel);
            }

            if (siguienteNivel < totalNiveles) {
                nivelesDesbloqueados[siguienteNivel] = true;
            }

            configurarBotones();
        }
    }

    private void regresarMenu() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        new MenuPrincipal(Login.usuarioLogueado).mostrarEnFrame();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("FlowFree Mapa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
