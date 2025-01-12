package ajedrez;

import piezas.*;
import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private Pieza[][] tablero = new Pieza[8][8];

    public Tablero() {
        inicializarTablero();
    }

    public Tablero(Tablero otroTablero) {
        this.tablero = new Pieza[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (otroTablero.tablero[i][j] != null) {
                    this.tablero[i][j] = otroTablero.tablero[i][j].clone();
                }
            }
        }
    }

    private void inicializarTablero() {
        inicializarFila(0, false);
        inicializarPeones(1, false);
        inicializarPeones(6, true);
        inicializarFila(7, true);
    }

    private void inicializarFila(int fila, boolean color) {
        tablero[fila][0] = new Torre(color, fila, 0);
        tablero[fila][1] = new Caballo(color, fila, 1);
        tablero[fila][2] = new Alfil(color, fila, 2);
        tablero[fila][3] = new Dama(color, fila, 3);
        tablero[fila][4] = new Rey(color, fila, 4);
        tablero[fila][5] = new Alfil(color, fila, 5);
        tablero[fila][6] = new Caballo(color, fila, 6);
        tablero[fila][7] = new Torre(color, fila, 7);
    }

    private void inicializarPeones(int fila, boolean color) {
        for (int i = 0; i < tablero.length; i++) {
            tablero[fila][i] = new Peon(color, fila, i);
        }
    }

    public void imprimirTablero() {
        System.out.println(" | A  B  C  D  E  F  G  H |");
        System.out.println(" --------------------------");
        for (int i = 0; i < tablero.length; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < tablero.length; j++) {
                imprimirCasilla(i, j);
            }
            System.out.println("|");
        }
        System.out.println(" --------------------------");
    }

    private void imprimirCasilla(int i, int j) {
        if ((i + j) % 2 == 0) {
            System.out.print(" ");
        } else {
            System.out.print("[");
        }
        if (tablero[i][j] != null) {
            System.out.print(tablero[i][j].toString());
        } else {
            System.out.print(" ");
        }
        if ((i + j) % 2 == 0) {
            System.out.print(" ");
        } else {
            System.out.print("]");
        }
    }

    public Posicion[] movimientosFiltrados(String casilla) {
        return getPieza(casilla).posiblesMovimientos(this);
    }

    public String imprimirCasillasFiltradas(String casilla) {
        StringBuilder mov = new StringBuilder();
        for (Posicion posicion : movimientosFiltrados(casilla)) {
            if (posicion != null) {
                mov.append(posicion.nombrarCasilla()).append(" ");
            }
        }
        return mov.toString();
    }

    public int numerarColumna(char col) {
        return col - 'A';
    }

    public void moverPieza(String casilla1, String casilla2) {
        int y = numerarColumna(casilla1.charAt(0));
        int x = getNum(casilla1);
        int j = numerarColumna(casilla2.charAt(0));
        int i = getNum(casilla2);
        if (tablero[x][y] instanceof Rey && (j - 2 == y || j + 3 == y)) {
            enroque(x, y, j);
        }
        tablero[i][j] = getPieza(casilla1);
        tablero[x][y].mover(i, j, this);
        tablero[x][y] = null;
    }

    public Pieza getPieza(String casilla) {
        int y = numerarColumna(casilla.charAt(0));
        int x = getNum(casilla);
        return tablero[x][y];
    }

    public boolean casillaVacia(String casilla) {
        return getPieza(casilla) == null;
    }

    public boolean piezaDeSuColor(boolean color, String casilla) {
        Pieza pieza = getPieza(casilla);
        return pieza != null && pieza.getColor() == color;
    }

    public boolean comprobarCasilla(String casillaOriginal, String casillaFinal) {
        for (Posicion posicion : movimientosFiltrados(casillaOriginal)) {
            if (posicion != null && casillaFinal.equals(posicion.nombrarCasilla())) {
                return true;
            }
        }
        return false;
    }

    public int getNum(String casilla) {
        int num;
        try {
            num = Character.getNumericValue(casilla.charAt(1)) - 1;
        } catch (Exception e) {
            return -1;
        }
        return num;
    }

    public Pieza getPieza(int x, int y) {
        return tablero[x][y];
    }

    public boolean jaque(int x, int y, boolean color) {
        Posicion casillaAtacada = new Posicion(x, y);
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (tablero[i][j] != null && !(tablero[i][j] instanceof Rey) && tablero[i][j].getColor() == color) {
                    for (Posicion mov : tablero[i][j].posiblesMovimientos(this)) {
                        if (mov != null && mov.mismaPosicion(casillaAtacada)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void enroque(int x, int y, int j) {
        if (j - y > 0) {
            tablero[x][y + 1] = tablero[x][y + 3];
            tablero[x][y + 3] = null;
            tablero[x][y + 1].setPos(x, y + 1);
        } else {
            tablero[x][y - 2] = tablero[x][y - 4];
            tablero[x][y - 4] = null;
            tablero[x][y - 2].setPos(x, y - 2);
        }
    }

    private Posicion buscarRey(boolean color) {
        for (int x = 0; x < tablero.length; x++) {
            for (int y = 0; y < tablero.length; y++) {
                if (tablero[x][y] instanceof Rey && tablero[x][y].getColor() == color) {
                    return tablero[x][y].getPos();
                }
            }
        }
        return null;
    }

    public boolean jaque(boolean color) {
        Posicion reyPos = buscarRey(!color);
        if (reyPos == null) {
            return false;
        }
        for (int x = 0; x < tablero.length; x++) {
            for (int y = 0; y < tablero.length; y++) {
                if (tablero[x][y] != null && tablero[x][y].getColor() == color) {
                    for (Posicion mov : tablero[x][y].posiblesMovimientos(this)) {
                        if (mov != null && mov.mismaPosicion(reyPos)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void promocion(int n, String casilla) {
        int y = numerarColumna(casilla.charAt(0));
        int x = getNum(casilla);
        switch (n) {
            case 1:
                tablero[x][y] = new Torre(getPieza(casilla).getColor(), x, y);
                break;
            case 2:
                tablero[x][y] = new Caballo(getPieza(casilla).getColor(), x, y);
                break;
            case 3:
                tablero[x][y] = new Alfil(getPieza(casilla).getColor(), x, y);
                break;
            case 4:
                tablero[x][y] = new Dama(getPieza(casilla).getColor(), x, y);
                break;
        }
    }

    public List<String> obtenerCasillasConPiezas(boolean color) {
        List<String> casillas = new ArrayList<>();
        for (int x = 0; x < tablero.length; x++) {
            for (int y = 0; y < tablero.length; y++) {
                if (tablero[x][y] != null && tablero[x][y].getColor() == color) {
                    casillas.add("" + (char) ('A' + y) + (x + 1));
                }
            }
        }
        return casillas;
    }

    public List<String> obtenerMovimientosLegales(String casilla) {
        List<String> movimientos = new ArrayList<>();
        for (Posicion posicion : movimientosFiltrados(casilla)) {
            if (posicion != null) {
                movimientos.add(posicion.nombrarCasilla());
            }
        }
        return movimientos;
    }
}