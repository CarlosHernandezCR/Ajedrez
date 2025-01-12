package piezas;

import ajedrez.*;

/**
 * Clase Peon, heredada de la clase Pieza que se mueve de uno en uno hacia delante, 2 si comienza, come en diagonal y puede promocionar.
 */
public class Peon extends Pieza {
    // CONSTRUCTOR
    /**
     * Constructor que asigna el color y la posicion inicial en la que se encuentra el peon.
     * @param color Color del peon.
     * @param i Fila en la que se encuentra el peon.
     * @param j Columna en la que se encuentra el peon.
     */
    public Peon(boolean color, int i, int j) {
        super(color, i, j);
    }

    /**
     * Metodo sobreescrito que devuelve el nombre característico del peon.
     */
    @Override
    public String toString() {
        return color ? "P" : "p";
    }

    /**
     * Metodo sobreescrito que devuelve un array con las posibles posiciones a las que se puede mover el peon.
     */
    @Override
    public Posicion[] posiblesMovimientos(Tablero tablero) {
        Posicion[] mov = new Posicion[4];
        int i = 0;

        if (color) {
            i = agregarMovimiento(mov, i, pos.getFila() - 1, pos.getCol(), tablero, false);
            i = agregarMovimiento(mov, i, pos.getFila() - 1, pos.getCol() - 1, tablero, true);
            i = agregarMovimiento(mov, i, pos.getFila() - 1, pos.getCol() + 1, tablero, true);
            if (!movido && tablero.getPieza(pos.getFila() - 1, pos.getCol()) == null && tablero.getPieza(pos.getFila() - 2, pos.getCol()) == null) {
                mov[i] = new Posicion(pos.getFila() - 2, pos.getCol());
            }
        } else {
            i = agregarMovimiento(mov, i, pos.getFila() + 1, pos.getCol(), tablero, false);
            i = agregarMovimiento(mov, i, pos.getFila() + 1, pos.getCol() - 1, tablero, true);
            i = agregarMovimiento(mov, i, pos.getFila() + 1, pos.getCol() + 1, tablero, true);
            if (!movido && tablero.getPieza(pos.getFila() + 1, pos.getCol()) == null && tablero.getPieza(pos.getFila() + 2, pos.getCol()) == null) {
                mov[i] = new Posicion(pos.getFila() + 2, pos.getCol());
            }
        }

        return mov;
    }

    private int agregarMovimiento(Posicion[] mov, int i, int fila, int col, Tablero tablero, boolean esComer) {
        if (fila >= 0 && fila <= 7 && col >= 0 && col <= 7) {
            if (esComer) {
                if (tablero.getPieza(fila, col) != null && tablero.getPieza(fila, col).getColor() != color) {
                    mov[i++] = new Posicion(fila, col);
                }
            } else {
                if (tablero.getPieza(fila, col) == null) {
                    mov[i++] = new Posicion(fila, col);
                }
            }
        }
        return i;
    }

    @Override
    public void mover(int x, int y, Tablero tablero) {
        setPos(x, y);
        movido = true;
    }
    @Override
    public Peon clone() {
        return (Peon) super.clone();
    }
}