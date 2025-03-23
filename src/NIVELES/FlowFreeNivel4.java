/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NIVELES;

/**
 *
 * @author 50494
 */

import java.awt.*;

public class FlowFreeNivel4 extends FlowFreeBase {

    public FlowFreeNivel4(MapaNivelesBonito mapa) {
        super(mapa, 8, 90, new Color[]{
            Color.BLUE, Color.GREEN, new Color(255, 102, 0),
            new Color(160, 32, 240), Color.PINK, Color.YELLOW,
            new Color(51, 204, 255), Color.RED
        });
    }

    @Override
    protected void cargarPuntosDeLaImagen() {
        startPoints.put(new Point(6, 0), 1);
        startPoints.put(new Point(7, 7), 1);
        startPoints.put(new Point(3, 0), 2);
        startPoints.put(new Point(5, 1), 2);
        startPoints.put(new Point(7, 0), 3);
        startPoints.put(new Point(7, 3), 3);
        startPoints.put(new Point(0, 0), 4);
        startPoints.put(new Point(4, 4), 4);
        startPoints.put(new Point(0, 5), 5);
        startPoints.put(new Point(1, 6), 5);
        startPoints.put(new Point(0, 1), 6);
        startPoints.put(new Point(1, 3), 6);
        startPoints.put(new Point(5, 6), 7);
        startPoints.put(new Point(6, 7), 7);
        startPoints.put(new Point(1, 1), 8);
        startPoints.put(new Point(5, 4), 8);
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
        return 4;
    }
}
