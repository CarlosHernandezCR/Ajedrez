package piezas;

import ajedrez.*;

/**
 * Clase Alfil, heredada de la clase Pieza, se mueve en diagonal en cualquier dirección tod lo que se pueda.
 */
public class Alfil extends Pieza {
    // CONSTRUCTOR
    /**
     * Constructor del alfil que le asigna un nombre, un color y una posición inicial.
     * @param color Color del Alfil.
     * @param i Fila en la que se encuentra el Alfil.
     * @param j Columna en la que se encuentra el Alfil.
     */
    public Alfil(boolean color, int i, int j) {
        super(color, i, j);
    }

    // METODOS
    /**
     * Devuelve como se imprimirá por pantalla según el equipo al que pertenezca.
     */
    @Override
    public String toString() {
        return color ? "A" : "a";
    }

    @Override
    public Posicion[] posiblesMovimientos(Tablero tablero) {
        Posicion[] mov = new Posicion[13];
        int i = 0;

        i = agregarMovimientos(tablero, mov, i, -1, 1); // Diagonal superior derecha
        i = agregarMovimientos(tablero, mov, i, 1, 1);  // Diagonal inferior derecha
        i = agregarMovimientos(tablero, mov, i, 1, -1); // Diagonal inferior izquierda
        agregarMovimientos(tablero, mov, i, -1, -1);    // Diagonal superior izquierda

        return mov;
    }

    private int agregarMovimientos(Tablero tablero, Posicion[] mov, int i, int dx, int dy) {
        int x = getPos().getFila();
        int y = getPos().getCol();

        while (esMovimientoValido(x + dx, y + dy, tablero)) {
            x += dx;
            y += dy;
            mov[i++] = new Posicion(x, y);
            if (tablero.getPieza(x, y) != null && tablero.getPieza(x, y).getColor() != color) {
                break;
            }
        }
        return i;
    }

    private boolean esMovimientoValido(int x, int y, Tablero tablero) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7 && (tablero.getPieza(x, y) == null || tablero.getPieza(x, y).getColor() != color);
    }

    @Override
    public void mover(int x, int y, Tablero tablero) {
        pos.setColumna(y);
        pos.setFila(x);
    }

    @Override
    public Alfil clone() {
        return (Alfil) super.clone();
    }
}