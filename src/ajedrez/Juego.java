package ajedrez;

import java.util.Scanner;
import piezas.*;

public class Juego {
    private static final int MAX_TURNOS = 200;
    private static final int MIN_CASILLA = 65;
    private static final int MAX_CASILLA = 72;
    private static final int MIN_NUM = 0;
    private static final int MAX_NUM = 7;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Tablero tablero = new Tablero();
        int cuentaTurnos = 0;
        String casilla = "";
        String casilla2;
        boolean color = true;
        boolean mov;
        boolean mate = false;
        while (cuentaTurnos < MAX_TURNOS && !mate) {
            System.out.println(color ? "Las negras han ganado por jaque mate" : "Las blancas han ganado por jaque mate");
            tablero.imprimirTablero();
            if (esJaqueMate(tablero, color)) {
                System.out.println(color ? "Las negras han ganado por jaque mate" : "Las blancas han ganado por jaque mate");
                mate = true;
                break;
            }
            if (!tablero.jaque(!color)) {
                System.out.print(color ? "Turno de las blancas(mayusculas), " : "Turno de las negras(minusculas), ");
                mov = false;
                while (!mov) {
                    casilla = obtenerCasilla(sc, tablero, color);
                    if (!tablero.imprimirCasillasFiltradas(casilla).isBlank()) {
                        mov = true;
                    } else {
                        System.out.print("No hay movimientos posibles para esta ficha, ");
                    }
                }
                casilla2 = obtenerMovimiento(sc, tablero, casilla);
                tablero.moverPieza(casilla, casilla2);
                manejarPromocion(sc, tablero, casilla2);
            } else {
                manejarJaque(sc, tablero, color);
            }
            color = !color;
            cuentaTurnos++;
        }
        sc.close();
    }

    private static String obtenerCasilla(Scanner sc, Tablero tablero, boolean color) {
        String casilla = "";
        boolean valido = false;
        while (!valido) {
            System.out.print("escriba la casilla de la pieza que desea mover: ");
            casilla = sc.nextLine().toUpperCase();
            if (casilla.isEmpty() || casilla.length() != 2) {
                System.out.print("Entrada vacía o inválida, por favor escriba una casilla válida.\n");
            } else if (esCasillaValida(casilla, tablero, color)) {
                valido = true;
            }
        }
        return casilla;
    }

    private static boolean esCasillaValida(String casilla, Tablero tablero, boolean color) {
        if (casilla.charAt(0) < MIN_CASILLA || casilla.charAt(0) > MAX_CASILLA || tablero.getNum(casilla) > MAX_NUM || tablero.getNum(casilla) < MIN_NUM) {
            System.out.print("Casilla inexistente, escriba otra \n");
            return false;
        } else if (tablero.casillaVacia(casilla)) {
            System.out.print("No hay ninguna pieza en esta casilla, escriba otra casilla \n");
            return false;
        } else if (!tablero.piezaDeSuColor(color, casilla)) {
            System.out.print("La pieza no corresponde a su equipo, escriba otra casilla \n");
            return false;
        }
        return true;
    }

    private static String obtenerMovimiento(Scanner sc, Tablero tablero, String casilla) {
        String casilla2;
        System.out.println("Escriba una casilla de las posibles siguientes para mover la pieza: ");
        System.out.println(tablero.imprimirCasillasFiltradas(casilla));
        System.out.print("Escriba una opcion: ");
        casilla2 = sc.nextLine().toUpperCase();
        while (casilla2.isEmpty() || casilla2.length() != 2 || !tablero.comprobarCasilla(casilla, casilla2)) {
            System.out.print("Esta casilla no se encuentra entre uno de los posibles movimientos, escriba una válida: ");
            casilla2 = sc.nextLine().toUpperCase();
        }
        return casilla2;
    }

    private static void manejarPromocion(Scanner sc, Tablero tablero, String casilla2) {
        if (tablero.getPieza(casilla2) instanceof Peon && (tablero.getNum(casilla2) == 7 || tablero.getNum(casilla2) == 0)) {
            System.out.print("A que pieza la quieres promocionar"
                    + "\n 1.Torre \n 2.Caballo \n 3.Alfil \n 4.Dama"
                    + "\nEscriba una opcion: ");
            while (!sc.hasNextInt()) {
                System.out.print("Opción incorrecta, elija una correcta: ");
                sc.next();
            }
            int opcion = sc.nextInt();
            sc.nextLine();
            while (opcion > 4 || opcion < 1) {
                System.out.print("Opción incorrecta, elija una correcta: ");
                while (!sc.hasNextInt()) {
                    System.out.print("Opción incorrecta, elija una correcta: ");
                    sc.next();
                }
                opcion = sc.nextInt();
                sc.nextLine();
            }
            tablero.promocion(opcion, casilla2);
        }
    }

    private static void manejarJaque(Scanner sc, Tablero tablero, boolean color) {
        System.out.println(color ? "Blancas estais jaque" : "Negras estais en jaque");
        System.out.print("Haz un movimiento para deshacer el jaque, ");
        while (tablero.jaque(!color)) {
            String casilla = obtenerCasilla(sc, tablero, color);
            String casilla2 = obtenerMovimiento(sc, tablero, casilla);
            tablero.moverPieza(casilla, casilla2);
            manejarPromocion(sc, tablero, casilla2);
            if (tablero.jaque(!color)) {
                System.out.print("Con este movimiento no deshaces el jaque, pruebe con otro,");
                tablero.moverPieza(casilla2, casilla);
                tablero.getPieza(casilla).setMovido(false);
            }
        }
    }

    private static boolean esJaqueMate(Tablero tablero, boolean color) {
        if (!tablero.jaque(!color)) {
            return false;
        }
        for (String casilla : tablero.obtenerCasillasConPiezas(color)) {
            for (String movimiento : tablero.obtenerMovimientosLegales(casilla)) {
                Tablero tableroSimulado = new Tablero(tablero);
                tableroSimulado.moverPieza(casilla, movimiento);
                if (!tableroSimulado.jaque(!color)) {
                    return false;
                }
            }
        }
        return true;
    }
}