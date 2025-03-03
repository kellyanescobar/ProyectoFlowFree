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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Point;

public class FlowFreeNivel1 extends JPanel {
    private final int gridSize = 3;
    private final int cellSize = 100;
    private final int[][] grid = new int[gridSize][gridSize];
    private final Color[] colors = {Color.GREEN, Color.BLUE, Color.RED};
    private final HashMap<Point, Integer> startPoints = new HashMap<>();
    private Point previousPoint = null;
    private int currentColor = 0;
    private MapaNivelesBonito mapa;
    

    public FlowFreeNivel1(MapaNivelesBonito mapa) {
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
                    grid[x][y] = currentColor;
                    repaint();
                }
            }

            public void mouseReleased(MouseEvent e) {
                currentColor = 0;
                previousPoint = null;

                if (nivelCompletado()) {
                    JOptionPane.showMessageDialog(null, "¡Nivel 1 completado!");
                    mapa.desbloquearNivel(1); 
                    SwingUtilities.getWindowAncestor(FlowFreeNivel1.this).dispose();
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
                    if (grid[x][y] == 0 || grid[x][y] == currentColor) {
                        grid[x][y] = currentColor;
                        previousPoint = p;
                        repaint();
                    }
                }
            }
        });
    }
    

    private void cargarPuntosDeLaImagen() {
        startPoints.put(new Point(0, 0), 1); // Verde
        startPoints.put(new Point(1, 1), 1);
        startPoints.put(new Point(0, 1), 2); // Azul
        startPoints.put(new Point(1, 2), 2);
        startPoints.put(new Point(2, 0), 3); // Rojo
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
        g.setColor(Color.WHITE);
        for (int x = 0; x <= gridSize; x++) {
            g.drawLine(x * cellSize, 0, x * cellSize, gridSize * cellSize);
        }
        for (int y = 0; y <= gridSize; y++) {
            g.drawLine(0, y * cellSize, gridSize * cellSize, y * cellSize);
        }

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                if (grid[x][y] > 0) {
                    g.setColor(colors[grid[x][y] - 1]);
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }

        for (Point p : startPoints.keySet()) {
            g.setColor(colors[startPoints.get(p) - 1]);
            g.fillOval(p.x * cellSize + 10, p.y * cellSize + 10, 80, 80);
        }
    }
}
