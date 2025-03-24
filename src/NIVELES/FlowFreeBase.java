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
import java.util.HashMap;
import java.util.Stack;

import javax.swing.Timer;
import proyectoflowfree.Login;

public abstract class FlowFreeBase extends JPanel {

    protected int gridSize;
    protected int cellSize;
    protected int[][] grid;
    protected Color[] colors;
    protected HashMap<Point, Integer> startPoints = new HashMap<>();
    protected Stack<Point> trazoActual = new Stack<>();
    protected Point previousPoint = null;
    protected int currentColor = 0;
    protected BufferedImage buffer;
    protected Graphics2D bufferGraphics;
    protected boolean mostrarError = false;
    protected MapaNivelesBonito mapa;
    protected long tiempoInicio;

    public FlowFreeBase(MapaNivelesBonito mapa, int gridSize, int cellSize, Color[] colors) {
        this.mapa = mapa;
        this.gridSize = gridSize;
        this.cellSize = cellSize;
        this.colors = colors;
        this.grid = new int[gridSize][gridSize];

        setLayout(new BorderLayout());

        JPanel panelGrid = new PanelGrid();
        JPanel panelBotones = new JPanel(new BorderLayout());

        JButton btnBack = crearBoton("back.png", e -> volverAlMapa());
        JButton btnUndo = crearBoton("undo.png", e -> deshacerPaso());

        panelBotones.setBackground(Color.BLACK);
        panelBotones.add(btnBack, BorderLayout.WEST);
        panelBotones.add(btnUndo, BorderLayout.EAST);

        add(panelGrid, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        this.tiempoInicio = System.currentTimeMillis();

    }

    private JButton crearBoton(String nombreImagen, ActionListener listener) {
        ImageIcon icon = new ImageIcon("src/Imagenes/" + nombreImagen);
        Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JButton btn = new JButton(new ImageIcon(scaled));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.addActionListener(listener);
        return btn;
    }

    private class PanelGrid extends JPanel {

        public PanelGrid() {
            setPreferredSize(new Dimension(gridSize * cellSize, gridSize * cellSize));
            setBackground(Color.BLACK);
            cargarPuntosDeLaImagen();
            inicializarBuffer();

            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    int x = e.getX() / cellSize;
                    int y = e.getY() / cellSize;
                    Point p = new Point(x, y);

                    if (startPoints.containsKey(p)) {
                        currentColor = startPoints.get(p);
                        previousPoint = p;
                        trazoActual.clear();
                        trazoActual.push(p);
                        grid[x][y] = currentColor;
                        repaint();
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    for (Point p : trazoActual) {
                        grid[p.x][p.y] = currentColor;
                    }
                    if (nivelCompletado()) {
                        JOptionPane.showMessageDialog(null, "¡Nivel " + numeroNivel() + " completado!");
                        mapa.desbloquearNivel(numeroNivel());
                        //duracion nivel
                        long tiempoFinal = System.currentTimeMillis();
                        long duracionSegundos = (tiempoFinal - tiempoInicio) / 1000;

                        if (Login.usuarioLogueado != null) {
                            int tiempoPrevio = Login.usuarioLogueado.getTiempoJugado();
                            Login.usuarioLogueado.setTiempoJugado(tiempoPrevio + (int) duracionSegundos);
                            Login.usuarioLogueado.guardarDatos();
                        }

                        if (Login.usuarioLogueado != null) {

                            new Thread(() -> Login.usuarioLogueado.guardarDatos()).start(); //hilos
                        }

                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FlowFreeBase.this);
                        CargaEntreNiveles carga = new CargaEntreNiveles(frame);
                        carga.mostrarPor(2000, FlowFreeBase.this::volverAlMapa);
                    }

                    currentColor = 0;
                    previousPoint = null;
                    repaint();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (currentColor == 0 || previousPoint == null) {
                        return;
                    }

                    int x = e.getX() / cellSize;
                    int y = e.getY() / cellSize;
                    Point p = new Point(x, y);

                    if (esValido(x, y) && esAdyacente(previousPoint, p)) {
                        int colorEnCelda = grid[x][y];

                        if (startPoints.containsKey(p) && startPoints.get(p) != currentColor) {
                            reiniciarTrazoConError();
                            return;
                        }

                        if (colorEnCelda == 0 || colorEnCelda == currentColor) {
                            trazoActual.push(p);
                            previousPoint = p;
                            grid[p.x][p.y] = currentColor;
                            dibujarLineaRealTime();
                        } else {
                            reiniciarTrazoConError();
                        }
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(buffer, 0, 0, this);
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(Color.WHITE);
            for (int x = 0; x <= gridSize; x++) {
                g2.drawLine(x * cellSize, 0, x * cellSize, gridSize * cellSize);
            }
            for (int y = 0; y <= gridSize; y++) {
                g2.drawLine(0, y * cellSize, gridSize * cellSize, y * cellSize);
            }

            int circleSize = Math.min(cellSize - 20, 70);
            int offset = (cellSize - circleSize) / 2;

            for (Point p : startPoints.keySet()) {
                g2.setColor(colors[startPoints.get(p) - 1]);
                g2.fillOval(p.x * cellSize + offset, p.y * cellSize + offset, circleSize, circleSize);
            }

            if (mostrarError) {
                g2.setColor(new Color(255, 0, 0, 80));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    protected void volverAlMapa() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.dispose();
        }
        mapa.mostrarEnFrame();
    }

    protected void deshacerPaso() {
        if (trazoActual.isEmpty()) {
            return;
        }

        Point ultimo = trazoActual.pop();
        grid[ultimo.x][ultimo.y] = 0;

        inicializarBuffer();

        if (!trazoActual.isEmpty() && currentColor > 0) {
            bufferGraphics.setColor(colors[currentColor - 1]);
            for (int i = 1; i < trazoActual.size(); i++) {
                Point p1 = trazoActual.get(i - 1);
                Point p2 = trazoActual.get(i);
                int x1 = p1.x * cellSize + cellSize / 2;
                int y1 = p1.y * cellSize + cellSize / 2;
                int x2 = p2.x * cellSize + cellSize / 2;
                int y2 = p2.y * cellSize + cellSize / 2;
                bufferGraphics.drawLine(x1, y1, x2, y2);
            }
        }

        previousPoint = trazoActual.isEmpty() ? null : trazoActual.peek();
        if (trazoActual.isEmpty()) {
            currentColor = 0;
        }

        repaint();
    }

    protected void inicializarBuffer() {
        buffer = new BufferedImage(gridSize * cellSize, gridSize * cellSize, BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = buffer.createGraphics();
        bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bufferGraphics.setStroke(new BasicStroke(8));
    }

    protected void dibujarLineaRealTime() {
        if (trazoActual.size() > 1) {
            Point p1 = trazoActual.get(trazoActual.size() - 2);
            Point p2 = trazoActual.peek();
            bufferGraphics.setColor(colors[currentColor - 1]);
            int x1 = p1.x * cellSize + cellSize / 2;
            int y1 = p1.y * cellSize + cellSize / 2;
            int x2 = p2.x * cellSize + cellSize / 2;
            int y2 = p2.y * cellSize + cellSize / 2;
            bufferGraphics.drawLine(x1, y1, x2, y2);
            repaint();
        }
    }

    protected void reiniciarTrazoConError() {
        for (Point punto : trazoActual) {
            if (grid[punto.x][punto.y] == currentColor) {
                grid[punto.x][punto.y] = 0;
            }
        }
        trazoActual.clear();
        currentColor = 0;
        previousPoint = null;
        repaint();

        mostrarError = true;
        Timer t = new Timer(300, e -> { //hilos para que se muestra la pantalla en rojo
            mostrarError = false;
            repaint();
        });
        t.setRepeats(false);
        t.start();
    }

    protected boolean esValido(int x, int y) {
        return x >= 0 && x < gridSize && y >= 0 && y < gridSize;
    }

    protected boolean esAdyacente(Point p1, Point p2) {
        int dx = Math.abs(p1.x - p2.x);
        int dy = Math.abs(p1.y - p2.y);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    // metodos abstractos
    protected abstract void cargarPuntosDeLaImagen();

    protected abstract boolean nivelCompletado();

    protected abstract int numeroNivel();
}
