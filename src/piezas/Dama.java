package piezas;

import ajedrez.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Dama, heredada de la clase Pieza, sus movimientos son una combinación de los movimientos de la torre y del alfil.
 */
public class Dama extends Pieza {
    // CONSTRUCTOR
    /**
     * Constructor de la dama que le asigna un nombre, un color y una posición inicial.
     * @param color Color de la dama.
     * @param i Fila en la que se situará la dama.
     * @param j Columna en la que se situará la dama.
     */
    public Dama(boolean color, int i, int j) {
        super(color, i, j);
    }

    // METODOS
    /**
     * Devuelve como se imprimirá en el tablero.
     */
    @Override
    public String toString() {
        return color ? "D" : "d";
    }

    /**
     * Método sobreescrito que devuelve un array con las posibles posiciones a las que se puede mover la dama.
     */
    @Override
    public Posicion[] posiblesMovimientos(Tablero tablero) {
        List<Posicion> movimientos = new ArrayList<>();
        int[] direcciones = {-1, 0, 1};

        for (int dx : direcciones) {
            for (int dy : direcciones) {
                if (dx != 0 || dy != 0) {
                    int x = pos.getFila();
                    int y = pos.getCol();
                    while (true) {
                        x += dx;
                        y += dy;
                        if (x < 0 || x >= 8 || y < 0 || y >= 8) break;
                        if (tablero.getPieza(x, y) == null) {
                            movimientos.add(new Posicion(x, y));
                        } else {
                            if (tablero.getPieza(x, y).getColor() != this.color) {
                                movimientos.add(new Posicion(x, y));
                            }
                            break;
                        }
                    }
                }
            }
        }
        return movimientos.toArray(new Posicion[0]);
    }
    
    @Override
    public Dama clone() {
        return (Dama) super.clone();
    }
}