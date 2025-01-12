package piezas;

import ajedrez.*;

/**
 * Clase Rey, heredada de la clase Pieza, se mueve de uno en uno en todos los sentidos y no puede estar en jaque.
 */
public class Rey extends Pieza {
    // CONSTRUCTOR
    /**
     * Constructor del rey que le asigna un nombre, un color y una posición inicial.
     * @param color Color del rey.
     * @param i Fila en la que se encuentra el rey.
     * @param j Columna en la que se encuentra el rey.
     */
    public Rey(boolean color, int i, int j) {
        super(color, i, j);
    }

    // METODOS
    /**
     * Devuelve como se imprimirá en el tablero.
     */
    @Override
    public String toString() {
        return color ? "R" : "r";
    }

    /**
     * Método sobreescrito que devuelve un array con las posibles posiciones a las que se puede mover el rey.
     */
    @Override
    public Posicion[] posiblesMovimientos(Tablero tablero) {
        Posicion[] mov = new Posicion[11];
        int i = 0;

        int[][] movimientos = {
            {-1, 0}, {-1, 1}, {0, 1}, {1, 1},
            {1, 0}, {1, -1}, {0, -1}, {-1, -1}
        };

        for (int[] movimiento : movimientos) {
            int nuevoX = pos.getFila() + movimiento[0];
            int nuevoY = pos.getCol() + movimiento[1];
            if (esMovimientoValido(nuevoX, nuevoY, tablero)) {
                mov[i++] = new Posicion(nuevoX, nuevoY);
            }
        }

        if (!movido) {
            i = agregarEnroque(mov, i, tablero, 3, 2); // Enroque corto
            i = agregarEnroque(mov, i, tablero, -4, -3); // Enroque largo
        }

        return mov;
    }

    private boolean esMovimientoValido(int x, int y, Tablero tablero) {
        return x >= 0 && x < 8 && y >= 0 && y < 8 &&
               (tablero.getPieza(x, y) == null || tablero.getPieza(x, y).getColor() != color) &&
               !tablero.jaque(x, y, !color);
    }

    private int agregarEnroque(Posicion[] mov, int i, Tablero tablero, int torreOffset, int reyOffset) {
        if (tablero.getPieza(pos.getFila(), pos.getCol() + torreOffset) instanceof Torre) {
            Torre torre = (Torre) tablero.getPieza(pos.getFila(), pos.getCol() + torreOffset);
            if (!torre.movida()) {
                boolean noEnroque = false;
                for (int j = 1; j < Math.abs(torreOffset); j++) {
                    if (tablero.getPieza(pos.getFila(), pos.getCol() + Integer.signum(torreOffset) * j) != null ||
                        tablero.jaque(pos.getFila(), pos.getCol() + Integer.signum(torreOffset) * j, !color)) {
                        noEnroque = true;
                        break;
                    }
                }
                if (!noEnroque) {
                    mov[i++] = new Posicion(pos.getFila(), pos.getCol() + reyOffset);
                }
            }
        }
        return i;
    }

    @Override
    public void mover(int x, int y, Tablero tablero) {
        if (color) {
            if (x == 7 && y == 0) {
                tablero.getPieza(x, y).mover(pos.getFila(), pos.getCol(), tablero);
            } else if (x == 7 && y == 7) {
                // Additional logic for castling can be added here
            }
        }
        movido = true;
        pos.setColumna(y);
        pos.setFila(x);
    }

    @Override
    public Rey clone() {
        return (Rey) super.clone();
    }
}