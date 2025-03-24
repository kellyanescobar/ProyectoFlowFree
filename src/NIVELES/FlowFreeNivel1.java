/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NIVELES;

import java.awt.Color;
import java.awt.Point;

import java.awt.*;

public class FlowFreeNivel1 extends FlowFreeBase {
    private long tiempoInicio;

    public FlowFreeNivel1(MapaNivelesBonito mapa) {
        super(mapa, 3, 100, new Color[]{Color.GREEN, Color.BLUE, Color.RED});
    }

    @Override
    protected void cargarPuntosDeLaImagen() {
        startPoints.put(new Point(0, 0), 1);
        startPoints.put(new Point(1, 1), 1);
        startPoints.put(new Point(0, 1), 2);
        startPoints.put(new Point(1, 2), 2);
        startPoints.put(new Point(2, 0), 3);
        startPoints.put(new Point(2, 2), 3);
    }

    @Override
    protected boolean nivelCompletado() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (grid[i][j] == 0) return false;
            }
        }
        return true;
    }

    @Override
    protected int numeroNivel() {
        return 1;
    }
}
