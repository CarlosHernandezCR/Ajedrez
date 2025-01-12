package ajedrez;

public class Posicion implements Cloneable {
    private int fila;
    private int col;

    public Posicion() {}

    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.col = columna;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.col = columna;
    }

    public int getCol() {
        return col;
    }

    public int getFila() {
        return fila;
    }

    public String nombrarCasilla() {
        String nombre = Integer.toString(fila + 1);
        switch (col) {
            case 0: return "A" + nombre;
            case 1: return "B" + nombre;
            case 2: return "C" + nombre;
            case 3: return "D" + nombre;
            case 4: return "E" + nombre;
            case 5: return "F" + nombre;
            case 6: return "G" + nombre;
            case 7: return "H" + nombre;
            default: return null;
        }
    }

    public boolean mismaPosicion(Posicion pos2) {
        return this.fila == pos2.getFila() && this.col == pos2.getCol();
    }

    @Override
    public Posicion clone() {
        try {
            return (Posicion) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}