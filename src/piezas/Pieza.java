package piezas;

import ajedrez.*;

/**
 * Clase Pieza: clase padre de las piezas que habrán en el tablero.
 */
public abstract class Pieza implements Cloneable{
    // ATRIBUTOS
    /**
     * Atributo boolean protegido: color de la pieza.
     * Atributo de clase Posicion protegido: posicion de la pieza.
     */
    protected boolean color;
    protected boolean movido = false;
    protected Posicion pos;

    // CONSTRUCTORES
    /**
     * Constructor que asigna el color y la posicion inicial en la que se encuentra la pieza.
     * @param color Color de la pieza.
     * @param i Fila en la que se encuentra la pieza.
     * @param j Columna en la que se encuentra la pieza.
     */
    protected Pieza(boolean color, int i, int j) {
        this.color = color;
        this.pos = new Posicion(i, j);
    }

    // METODOS
    /**
     * Devuelve el color de la pieza.
     * @return boolean True si es blanca, False si es negra.
     */
    public boolean getColor() {
        return color;
    }

    /**
     * Metodo abstracto que devolverá un array de Posiciones a las que se podrá mover la pieza.
     * @param tablero Tablero de ajedrez.
     * @return Posicion[] Array de posiciones donde se pueda mover la pieza.
     */
    public abstract Posicion[] posiblesMovimientos(Tablero tablero);

    /**
     * Cambia las coordenadas de la clase posicion.
     * @param x Fila.
     * @param y Columna.
     */
    public void setPos(int x, int y) {
        pos.setColumna(y);
        pos.setFila(x);
    }

    /**
     * Metodo sobrecargado a la que le pasas una posicion y se le asignan sus atributos.
     * @param pos Posicion nueva a asignar.
     */
    public void setPos(Posicion pos) {
        this.pos = pos;
    }

    /**
     * Devuelve la posicion en la que se encuentra la pieza.
     * @return Posicion.
     */
    public Posicion getPos() {
        return pos;
    }

    /**
     * Metodo que cambia el atributo posicion de cada pieza.
     * @param x Coordenada fila a moverse.
     * @param y Coordenada columna a moverse.
     * @param tablero Tablero donde se hara el movimiento.
     */
    public void mover(int x, int y, Tablero tablero) {
        this.pos.setFila(x);
        this.pos.setColumna(y);
    }

    /**
     * Metodo setMovido, cambia el valor del boolean movido.
     * @param movido Booleano que dice si se ha movido la pieza.
     */
    public void setMovido(boolean movido) {
        this.movido = movido;
    }

    /**
     * Metodo clone permite copiar una pieza devolviendo una completamente nueva y con los mismos valores en sus atributos.
     * @return
     */
    @Override
    public Pieza clone() {
        try {
            return (Pieza) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}