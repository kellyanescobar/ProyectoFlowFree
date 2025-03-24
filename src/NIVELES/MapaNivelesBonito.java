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
import java.awt.image.BufferedImage;
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

        actualizarNivelesDesbloqueados();

        cargarPosicionesNiveles();
        configurarBotones();
        agregarBotonRegresar();
        actualizarTextos(mensajes);
    }

    private void actualizarNivelesDesbloqueados() {
        int nivelAlcanzadoUsuario = 1;

        if (Login.usuarioLogueado != null) {
            nivelAlcanzadoUsuario = Login.usuarioLogueado.getNivelAlcanzado();
        }

        nivelesDesbloqueados[0] = true;

        for (int i = 1; i < totalNiveles; i++) {
            nivelesDesbloqueados[i] = (i + 1) <= nivelAlcanzadoUsuario && nivelesDesbloqueados[i - 1];
        }
    }

    private void actualizarTextos(Properties mensajes) {
        botonRegresar.setText(mensajes.getProperty("regresar"));
    }

    private void cargarPosicionesNiveles() {
        posicionesNiveles = new HashMap<>();
        posicionesNiveles.put(0, new Point(71, 275));
        posicionesNiveles.put(1, new Point(169, 53));
        posicionesNiveles.put(2, new Point(303, 318));
        posicionesNiveles.put(3, new Point(372, 104));
        posicionesNiveles.put(4, new Point(575, 185));
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
                Image img = icon.getImage();

                MediaTracker tracker = new MediaTracker(this);
                tracker.addImage(img, 0);
                try {
                    tracker.waitForID(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int width = img.getWidth(this);
                int height = img.getHeight(this);

                if (width <= 0 || height <= 0) {
                    boton = new JButton("" + (i + 1));
                    if (!nivelesDesbloqueados[i]) {
                        boton.setForeground(Color.GRAY);
                    }
                } else {
                    img = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

                    if (!nivelesDesbloqueados[i]) {
                        try {
                            img = convertirAGris(img);
                        } catch (Exception e) {
                            boton = new JButton("" + (i + 1));
                            boton.setForeground(Color.GRAY);
                            boton.setContentAreaFilled(false);
                            boton.setFocusPainted(false);
                            boton.setOpaque(false);
                            boton.setBounds(pos.x - 25, pos.y - 25, 50, 50);
                            boton.setEnabled(nivelesDesbloqueados[i]);
                            int nivel = i + 1;
                            boton.addActionListener(e2 -> abrirNivel(nivel));
                            add(boton);
                            continue;
                        }
                    }

                    boton = new JButton(new ImageIcon(img));
                }
            } else {
                boton = new JButton("" + (i + 1));

                if (!nivelesDesbloqueados[i]) {
                    boton.setForeground(Color.GRAY);
                }
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

    private Image convertirAGris(Image imagenOriginal) {
        MediaTracker tracker = new MediaTracker(this);
        tracker.addImage(imagenOriginal, 0);
        try {
            tracker.waitForID(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return imagenOriginal;
        }

        int width = imagenOriginal.getWidth(this);
        int height = imagenOriginal.getHeight(this);

        if (width <= 0 || height <= 0) {
            return imagenOriginal;
        }

        BufferedImage buffImg = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_ARGB);

        Graphics g = buffImg.getGraphics();
        g.drawImage(imagenOriginal, 0, 0, width, height, this);
        g.dispose();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = buffImg.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xff;
                int r = (rgb >> 16) & 0xff;
                int g1 = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                int gris = (int) (0.299 * r + 0.587 * g1 + 0.114 * b);
                int nuevoRGB = (alpha << 24) | (gris << 16) | (gris << 8) | gris;
                buffImg.setRGB(x, y, nuevoRGB);
            }
        }

        return buffImg;
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
        int indiceNivel = nivel - 1;

        if (!nivelesDesbloqueados[indiceNivel]) {
            JOptionPane.showMessageDialog(this, "Debes completar el nivel anterior primero.");
            return;
        }

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
        int indiceNivelCompletado = nivelCompletado - 1;
        int indiceSiguienteNivel = nivelCompletado;

        if (indiceSiguienteNivel < totalNiveles) {
            if (Login.usuarioLogueado != null && Login.usuarioLogueado.getNivelAlcanzado() <= nivelCompletado) {
                Login.usuarioLogueado.setNivelAlcanzado(nivelCompletado + 1);
                Login.usuarioLogueado.guardarDatos();
            }

            actualizarNivelesDesbloqueados();
            configurarBotones();
        } else if (nivelCompletado == totalNiveles) {
            javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
                JOptionPane.showMessageDialog(
                        this,
                        "¡Felicidades! Has completado todos los niveles del juego",
                        "Juego completado",
                        JOptionPane.INFORMATION_MESSAGE
                );
            });
            timer.setRepeats(false);
            timer.start();
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
