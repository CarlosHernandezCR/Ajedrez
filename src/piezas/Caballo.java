package piezas;

import ajedrez.*;

/**
 * Clase Caballo, heredada de la clase Pieza, pieza que se mueve en L.
 */
public class Caballo extends Pieza {
    // CONSTRUCTOR
    /**
     * Constructor del caballo que le asigna un nombre, un color y una posición inicial.
     * @param color Color del caballo.
     * @param i Fila en la que se encuentra el caballo.
     * @param j Columna en la que se encuentra el caballo.
     */
    public Caballo(boolean color, int i, int j) {
        super(color, i, j);
    }

    // METODOS
    /**
     * Devuelve como se imprimirá en el tablero.
     */
    @Override
    public String toString() {
        return color ? "C" : "c";
    }

    @Override
    public Posicion[] posiblesMovimientos(Tablero tablero) {
        Posicion[] mov = new Posicion[8];
        int[][] movimientos = {
            {-2, 1}, {-1, 2}, {1, 2}, {2, 1},
            {2, -1}, {1, -2}, {-1, -2}, {-2, -1}
        };
        int x = getPos().getFila();
        int y = getPos().getCol();
        int i = 0;

        for (int[] movimiento : movimientos) {
            int nuevoX = x + movimiento[0];
            int nuevoY = y + movimiento[1];
            if (esMovimientoValido(nuevoX, nuevoY, tablero)) {
                mov[i++] = new Posicion(nuevoX, nuevoY);
            }
        }
        return mov;
    }

    private boolean esMovimientoValido(int x, int y, Tablero tablero) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7 &&
               (tablero.getPieza(x, y) == null || tablero.getPieza(x, y).getColor() != color);
    }

    @Override
    public void mover(int x, int y, Tablero tablero) {
        pos.setColumna(y);
        pos.setFila(x);
    }
    @Override
    public Caballo clone() {
        return (Caballo) super.clone();
    }
}