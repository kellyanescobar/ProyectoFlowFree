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

public class FlowFreeNivel5 extends FlowFreeBase {

    public FlowFreeNivel5(MapaNivelesBonito mapa) {
        super(mapa, 10, 70, new Color[]{
            Color.BLUE, Color.GREEN, new Color(255, 102, 0),
            new Color(160, 32, 240), Color.PINK, Color.YELLOW,
            new Color(51, 204, 255), new Color(102, 255, 102)
        });
    }

    @Override
    protected void cargarPuntosDeLaImagen() {
        startPoints.put(new Point(1, 1), 1);
        startPoints.put(new Point(3, 0), 1);
        startPoints.put(new Point(0, 1), 2);
        startPoints.put(new Point(9, 0), 2);
        startPoints.put(new Point(4, 8), 3);
        startPoints.put(new Point(8, 8), 3);
        startPoints.put(new Point(0, 0), 4);
        startPoints.put(new Point(7, 4), 4);
        startPoints.put(new Point(1, 5), 5);
        startPoints.put(new Point(1, 8), 5);
        startPoints.put(new Point(2, 6), 6);
        startPoints.put(new Point(2, 7), 6);
        startPoints.put(new Point(6, 7), 7);
        startPoints.put(new Point(8, 6), 7);
        startPoints.put(new Point(4, 1), 8);
        startPoints.put(new Point(8, 2), 8);
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
        return 5;
    }
}
