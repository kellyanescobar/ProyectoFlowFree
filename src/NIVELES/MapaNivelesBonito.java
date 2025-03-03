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
import java.util.HashMap;

public class MapaNivelesBonito extends JPanel {

    private final int totalNiveles = 5;
    private boolean[] nivelesDesbloqueados = new boolean[totalNiveles];
    private HashMap<Integer, Point> posicionesNiveles;

    public MapaNivelesBonito() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.BLACK);

        nivelesDesbloqueados[0] = true; // Solo el nivel 1 desbloqueado al inicio

        cargarPosicionesNiveles();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < totalNiveles; i++) {
                    Point pos = posicionesNiveles.get(i);
                    int distancia = (int) Math.sqrt(Math.pow(e.getX() - pos.x, 2) + Math.pow(e.getY() - pos.y, 2));
                    if (distancia <= 25 && nivelesDesbloqueados[i]) {
                        abrirNivel(i + 1);
                    }
                }
            }
        });
    }

    private void cargarPosicionesNiveles() {
        posicionesNiveles = new HashMap<>();
        posicionesNiveles.put(0, new Point(50, 300));
        posicionesNiveles.put(1, new Point(150, 200));
        posicionesNiveles.put(2, new Point(250, 300));
        posicionesNiveles.put(3, new Point(350, 200));
        posicionesNiveles.put(4, new Point(450, 300));
    }

    private void abrirNivel(int nivel) {
        if (nivel == 1) {
            JFrame frame = new JFrame("Nivel 1");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(new FlowFreeNivel1(this)); // Aquí tu nivel 1
            frame.pack();
            frame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Nivel " + nivel + " aún está bloqueado.");
        }
    }

    public void desbloquearNivel(int nivel) {
        if (nivel < totalNiveles) {
            nivelesDesbloqueados[nivel] = true;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarGrid(g);
        dibujarCamino(g);
        dibujarNiveles(g);
        dibujarMonedas(g);
    }

    private void dibujarGrid(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int x = 0; x <= getWidth(); x += 50) {
            g.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y <= getHeight(); y += 50) {
            g.drawLine(0, y, getWidth(), y);
        }
    }

    private void dibujarCamino(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(30, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        Color[] coloresCamino = {Color.RED, Color.ORANGE, Color.GREEN, Color.BLUE, Color.MAGENTA};

        for (int i = 0; i < totalNiveles - 1; i++) {
            g2d.setColor(coloresCamino[i]);
            Point actual = posicionesNiveles.get(i);
            Point siguiente = posicionesNiveles.get(i + 1);
            g2d.drawLine(actual.x, actual.y, siguiente.x, siguiente.y);
        }
    }

    private void dibujarNiveles(Graphics g) {
        for (int i = 0; i < totalNiveles; i++) {
            Point pos = posicionesNiveles.get(i);
            g.setColor(nivelesDesbloqueados[i] ? Color.WHITE : Color.GRAY);
            g.fillOval(pos.x - 25, pos.y - 25, 50, 50);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            String numero = String.valueOf(i + 1);
            g.drawString(numero, pos.x - 6, pos.y + 7);
        }
    }

    private void dibujarMonedas(Graphics g) {
        g.setColor(Color.YELLOW);
        int[][] monedas = {
                {100, 250}, {200, 300}, {300, 200}, {400, 250}
        };
        for (int[] moneda : monedas) {
            g.fillOval(moneda[0] - 10, moneda[1] - 10, 20, 20);
            g.setColor(Color.ORANGE);
            g.drawOval(moneda[0] - 10, moneda[1] - 10, 20, 20);
            g.setColor(Color.YELLOW);
        }
    }

    public void mostrarEnFrame() {
        JFrame frame = new JFrame("Mapa de Niveles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
