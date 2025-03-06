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
import java.util.Stack;

public class FlowFreeNivel3 extends JPanel {
    private final int gridSize = 7;
    private final int cellSize = 100;
    private final int[][] grid = new int[gridSize][gridSize];
    private final Color[] colors = {Color.BLUE, Color.GREEN, new Color(255, 102, 0), new Color(160, 32, 240), Color.PINK, Color.YELLOW}; // Rojo, Azul, Verde, Naranja, Morado, Rosado y Amarillo
    private final HashMap<Point, Integer> startPoints = new HashMap<>();
    private Stack<Point> trazoActual = new Stack<>();
    private Point previousPoint = null;
    private int currentColor = 0;
    private MapaNivelesBonito mapa;

    public FlowFreeNivel3(MapaNivelesBonito mapa) {
        this.mapa = mapa;
        setPreferredSize(new Dimension(gridSize * cellSize, gridSize * cellSize));
        setBackground(Color.BLACK);
        cargarPuntosDeLaImagen();

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
                currentColor = 0;
                previousPoint = null;

                if (nivelCompletado()) {
                    JOptionPane.showMessageDialog(null, "¡Nivel 2 completado!");
                    mapa.desbloquearNivel(2);
                    SwingUtilities.getWindowAncestor(FlowFreeNivel3.this).dispose();
                }
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
                        grid[x][y] = currentColor;
                        trazoActual.push(p);
                        previousPoint = p;
                        repaint();
                    } else if (colorEnCelda == currentColor) {
                        trazoActual.push(p);
                        previousPoint = p;
                        repaint();
                    } else {
                        cancelarTrazo();
                        JOptionPane.showMessageDialog(null, "Colisión con otro color. Trazo cancelado.");
                    }
                }
            }
        });
    }

    private void cancelarTrazo() {
        while (!trazoActual.isEmpty()) {
            Point p = trazoActual.pop();
            if (startPoints.containsKey(p)) {
                break;
            } else {
                grid[p.x][p.y] = 0;
            }
        }
        currentColor = 0;
        previousPoint = null;
        repaint();
    }

    private void cargarPuntosDeLaImagen() {
        startPoints.put(new Point(0, 0), 1); // Azul
        startPoints.put(new Point(6, 6), 1); // Azul

        startPoints.put(new Point(3, 1), 2); // Verde
        startPoints.put(new Point(4, 3), 2); // Verde

        startPoints.put(new Point(1, 6), 3); // Naranja
        startPoints.put(new Point(5, 1), 3); // Naranja

        startPoints.put(new Point(0, 1), 4); // Morado
        startPoints.put(new Point(0, 6), 4); // Morado
        
        startPoints.put(new Point(2, 1), 5); // Rosado
        startPoints.put(new Point(2, 4), 5); // Rosado
        
        startPoints.put(new Point(1, 3), 6); // Amarillo
        startPoints.put(new Point(4, 5), 6); // Amarillo
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
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);

        for (int x = 0; x <= gridSize; x++) {
            g2.drawLine(x * cellSize, 0, x * cellSize, gridSize * cellSize);
        }
        for (int y = 0; y <= gridSize; y++) {
            g2.drawLine(0, y * cellSize, gridSize * cellSize, y * cellSize);
        }

        g2.setStroke(new BasicStroke(6)); // Mantiene las líneas delgadas como en el Nivel 1

        for (int i = 0; i < trazoActual.size() - 1; i++) {
            Point p1 = trazoActual.get(i);
            Point p2 = trazoActual.get(i + 1);
            int colorIndex = grid[p1.x][p1.y] - 1;
            g2.setColor(colors[colorIndex]);

            int x1 = p1.x * cellSize + cellSize / 2;
            int y1 = p1.y * cellSize + cellSize / 2;
            int x2 = p2.x * cellSize + cellSize / 2;
            int y2 = p2.y * cellSize + cellSize / 2;

            g2.drawLine(x1, y1, x2, y2);
        }

        for (Point p : startPoints.keySet()) {
            g2.setColor(colors[startPoints.get(p) - 1]);
            g2.fillOval(p.x * cellSize + 10, p.y * cellSize + 10, 80, 80);
        }
    }
}

