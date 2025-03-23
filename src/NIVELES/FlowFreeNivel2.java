/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NIVELES;
import java.awt.*;
import proyectoflowfree.Login;


public class FlowFreeNivel2 extends FlowFreeBase {

    public FlowFreeNivel2(MapaNivelesBonito mapa) {
        super(mapa, 5, 100, new Color[]{
            Color.RED, Color.BLUE, Color.GREEN,
            new Color(255, 102, 0), new Color(160, 32, 240)
        });
    }

    @Override
    protected void cargarPuntosDeLaImagen() {
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
        return 2;
    }
}
