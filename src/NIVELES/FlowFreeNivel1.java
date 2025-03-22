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
import proyectoflowfree.Login;

public class FlowFreeNivel1 extends JPanel {
    private final int gridSize = 3;
    private final int cellSize = 100;
    private final int[][] grid = new int[gridSize][gridSize];
    private final Color[] colors = {Color.GREEN, Color.BLUE, Color.RED};
    private final HashMap<Point, Integer> startPoints = new HashMap<>();
    private Stack<Point> trazoActual = new Stack<>();
    private Point previousPoint = null;
    private int currentColor = 0;
    private BufferedImage buffer;
    private Graphics2D bufferGraphics;
    private MapaNivelesBonito mapa;
    private boolean mostrarError = false;

    public FlowFreeNivel1(MapaNivelesBonito mapa) {
        this.mapa = mapa;
        setLayout(new BorderLayout());

        JPanel panelGrid = new PanelGrid();
        JPanel panelBotones = new JPanel(new BorderLayout());

        
        ImageIcon iconBack = new ImageIcon("C:/Users/50494/OneDrive/Documents/NetBeansProjects/ProyectoFlowFree/src/Imagenes/back.png");
        Image scaledBack = iconBack.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JButton btnBack = new JButton(new ImageIcon(scaledBack));
        btnBack.setContentAreaFilled(false);
        btnBack.setBorderPainted(false);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> volverAlMapa());

        ImageIcon iconbtnUndo = new ImageIcon("C:/Users/50494/OneDrive/Documents/NetBeansProjects/ProyectoFlowFree/src/Imagenes/undo.png");
        Image scaledUndo = iconbtnUndo.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JButton btnUndo = new JButton(new ImageIcon(scaledUndo));
        btnUndo.setContentAreaFilled(false);
        btnUndo.setBorderPainted(false);
        btnUndo.setFocusPainted(false);
        btnUndo.addActionListener(e -> deshacerPaso());

        panelBotones.setBackground(Color.BLACK);
        panelBotones.add(btnBack, BorderLayout.WEST);
        panelBotones.add(btnUndo, BorderLayout.EAST);

        add(panelGrid, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
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
                        JOptionPane.showMessageDialog(null, "¡Nivel 1 completado!");
                        mapa.desbloquearNivel(1);
                        if (Login.usuarioLogueado != null) {
                            Login.usuarioLogueado.setNivelAlcanzado(1);
                            Login.usuarioLogueado.guardarDatos();
                        }
                        volverAlMapa();
                    }
                    currentColor = 0;
                    previousPoint = null;
                    repaint();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (currentColor == 0 || previousPoint == null) return;

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

            int circleSize = 80;
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

    private void volverAlMapa() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) frame.dispose();
        mapa.mostrarEnFrame();
    }

    private void deshacerPaso() {
        if (trazoActual.isEmpty() || currentColor == 0) return;
        Point ultimo = trazoActual.pop();
        grid[ultimo.x][ultimo.y] = 0;
        inicializarBuffer();

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

        if (!trazoActual.isEmpty()) {
            previousPoint = trazoActual.peek();
        } else {
            previousPoint = null;
            currentColor = 0;
        }

        repaint();
    }

    private void inicializarBuffer() {
        buffer = new BufferedImage(gridSize * cellSize, gridSize * cellSize, BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = buffer.createGraphics();
        bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bufferGraphics.setStroke(new BasicStroke(8));
    }

    private void dibujarLineaRealTime() {
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

    private void cargarPuntosDeLaImagen() {
        startPoints.put(new Point(0, 0), 1);
        startPoints.put(new Point(1, 1), 1);
        startPoints.put(new Point(0, 1), 2);
        startPoints.put(new Point(1, 2), 2);
        startPoints.put(new Point(2, 0), 3);
        startPoints.put(new Point(2, 2), 3);
    }

    private boolean esValido(int x, int y) {
        return x >= 0 && x < gridSize && y >= 0 && y < gridSize;
    }

    private boolean esAdyacente(Point p1, Point p2) {
        int dx = Math.abs(p1.x - p2.x);
        int dy = Math.abs(p1.y - p2.y);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    private boolean nivelCompletado() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (grid[i][j] == 0) return false;
            }
        }
        return true;
    }

    private void reiniciarTrazoConError() {
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
        Timer t = new Timer(300, evt -> {
            mostrarError = false;
            repaint();
        });
        t.setRepeats(false);
        t.start();
    }
}
