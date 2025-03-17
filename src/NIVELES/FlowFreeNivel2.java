/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NIVELES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Stack;

public class FlowFreeNivel2 extends JPanel {
    private final int gridSize = 5;
    private final int cellSize = 100;
    private final int[][] grid = new int[gridSize][gridSize];
    private final Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, new Color(255, 102, 0), new Color(160, 32, 240)};
    private final HashMap<Point, Integer> startPoints = new HashMap<>();
    private Stack<Point> trazoActual = new Stack<>();
    private Point previousPoint = null;
    private int currentColor = 0;
    private BufferedImage buffer;
    private Graphics2D bufferGraphics;
    private MapaNivelesBonito mapa;

    public FlowFreeNivel2(MapaNivelesBonito mapa) {
        this.mapa = mapa;
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
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (nivelCompletado()) {
                    JOptionPane.showMessageDialog(null, "¡Nivel 2 completado!");
                    mapa.desbloquearNivel(2);
                    SwingUtilities.getWindowAncestor(FlowFreeNivel2.this).dispose(); // Cierra el panel del nivel
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

                    if (colorEnCelda == 0) {
                        trazoActual.push(p);
                        previousPoint = p;
                        dibujarLineaRealTime();
                    } else if (colorEnCelda == currentColor) {
                        trazoActual.push(p);
                        previousPoint = p;
                        dibujarLineaRealTime();
                    } else {
                        JOptionPane.showMessageDialog(null, "❌ No se puede tocar colores diferentes.");
                    }
                }
            }
        });
    }

    private void inicializarBuffer() {
        buffer = new BufferedImage(gridSize * cellSize, gridSize * cellSize, BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = buffer.createGraphics();
        bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bufferGraphics.setStroke(new BasicStroke(8));
        bufferGraphics.setColor(Color.WHITE);
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
        startPoints.put(new Point(1, 1), 1);
        startPoints.put(new Point(4, 0), 1);
        startPoints.put(new Point(2, 1), 2);
        startPoints.put(new Point(4, 4), 2);
        startPoints.put(new Point(0, 0), 3);
        startPoints.put(new Point(2, 2), 3);
        startPoints.put(new Point(3, 2), 4);
        startPoints.put(new Point(0, 4), 4);
        startPoints.put(new Point(0, 3), 5);
        startPoints.put(new Point(2, 3), 5);
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
        for (Point p : startPoints.keySet()) {
            int color = startPoints.get(p);
            if (grid[p.x][p.y] != color) {
                return false;
            }
        }
        return true;
    }

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

        for (Point p : startPoints.keySet()) {
            g2.setColor(colors[startPoints.get(p) - 1]);
            g2.fillOval(p.x * cellSize + 10, p.y * cellSize + 10, 80, 80);
        }
    }
}
