package piezas;

import ajedrez.*;

/**
 * Clase Torre, heredada de la clase Pieza, se mueve en línea.
 */
public class Torre extends Pieza {
	// CONSTRUCTOR
	/**
	 * Constructor que asigna el color y la posición inicial en la que se encuentra la torre.
	 * @param color Color de la torre.
	 * @param i Fila en la que se encuentra la torre.
	 * @param j Columna en la que se encuentra la torre.
	 */
	public Torre(boolean color, int i, int j) {
		super(color, i, j);
	}

	// METODOS
	/**
	 * Devuelve como se imprimirá en el tablero.
	 */
	@Override
	public String toString() {
		return color ? "T" : "t";
	}

	/**
	 * Método sobreescrito que devuelve un array con las posibles posiciones a las que se puede mover la torre.
	 */
	@Override
	public Posicion[] posiblesMovimientos(Tablero tablero) {
		Posicion[] mov = new Posicion[14];
		int i = 0;

		i = agregarMovimientos(tablero, mov, i, -1, 0); // Movimiento hacia arriba
		i = agregarMovimientos(tablero, mov, i, 1, 0);  // Movimiento hacia abajo
		i = agregarMovimientos(tablero, mov, i, 0, 1);  // Movimiento hacia la derecha
		agregarMovimientos(tablero, mov, i, 0, -1);     // Movimiento hacia la izquierda

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
		return x >= 0 && x < 8 && y >= 0 && y < 8 &&
				(tablero.getPieza(x, y) == null || tablero.getPieza(x, y).getColor() != color);
	}

	@Override
	public void mover(int x, int y, Tablero tablero) {
		movido = true;
		pos.setColumna(y);
		pos.setFila(x);
	}

	/**
	 * Obtiene el valor de si ha sido movida la pieza.
	 * @return true si ha sido movida la torre, false en caso contrario.
	 */
	public boolean movida() {
		return movido;
	}
	@Override
	public Torre clone() {
		return (Torre) super.clone();
	}
}