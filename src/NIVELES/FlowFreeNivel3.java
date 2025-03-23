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

public class FlowFreeNivel3 extends FlowFreeBase {

    public FlowFreeNivel3(MapaNivelesBonito mapa) {
        super(mapa, 7, 90, new Color[]{
            Color.BLUE, Color.GREEN, new Color(255, 102, 0),
            new Color(160, 32, 240), Color.PINK, Color.YELLOW
        });
    }

    @Override
    protected void cargarPuntosDeLaImagen() {
        startPoints.put(new Point(0, 0), 1);
        startPoints.put(new Point(6, 6), 1);
        startPoints.put(new Point(3, 1), 2);
        startPoints.put(new Point(4, 3), 2);
        startPoints.put(new Point(1, 6), 3);
        startPoints.put(new Point(5, 1), 3);
        startPoints.put(new Point(0, 1), 4);
        startPoints.put(new Point(0, 6), 4);
        startPoints.put(new Point(2, 1), 5);
        startPoints.put(new Point(2, 4), 5);
        startPoints.put(new Point(1, 3), 6);
        startPoints.put(new Point(4, 5), 6);
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
        return 3;
    }
}
