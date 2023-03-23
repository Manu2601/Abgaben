import java.util.ArrayList;

public class SchiffFactory {

    public static Playground getPlayground(){
        Playground pg = new Playground();
        for (int i = 0; i < pg.getFelder().length; i++) {
            for (int j = 0; j < pg.getFelder()[i].length; j++) {
                pg.getFelder()[i][j] = new Feld();
            }
        }
        schiffSetzen(5, 0, pg);
        schiffSetzen(4, 1, pg);
        schiffSetzen(4, 2, pg);
        schiffSetzen(3, 3, pg);
        schiffSetzen(3, 4, pg);
        schiffSetzen(3, 5, pg);
        schiffSetzen(2, 6, pg);
        schiffSetzen(2, 7, pg);
        schiffSetzen(2, 8, pg);
        schiffSetzen(2, 9, pg);
        return pg;
    }

    static void schiffSetzen(int länge, int id, Playground pg) {
        int folge = 0;
        ArrayList<Position> positionen = new ArrayList<>();
        boolean nochmal = true;
        while (nochmal) {
            for (int i = 0; i < pg.getFelder().length; i++) {
                for (int j = 0; j < pg.getFelder()[i].length; j++) {
                    if (schiffMöglich(new Position(i, j, true), pg)) {
                        folge++;
                    } else {
                        folge = 0;
                    }
                    boolean möglich = true;
                    for (Position position : positionen) {
                        if (position.equals(new Position(i, j - folge + 1, true))) {
                            möglich = false;
                            folge = 0;
                        }
                    }
                    if (folge == länge && möglich) {
                        positionen.add(new Position(i, j - folge + 1, true));
                        break;
                    }

                    if (i == pg.getFelder().length - 1 && j == pg.getFelder()[i].length - 1) {
                        nochmal = false;
                    }

                }
                if (folge == länge) {
                    folge = 0;
                    break;
                }
                folge = 0;
            }
        }

        nochmal = true;
        folge = 0;
        while (nochmal) {
            for (int i = 0; i < pg.getFelder().length; i++) {
                for (int j = 0; j < pg.getFelder()[i].length; j++) {
                    if (schiffMöglich(new Position(j, i, false), pg)) {
                        folge++;
                    } else {
                        folge = 0;
                    }

                    boolean geht = true;
                    for (Position position : positionen) {
                        if (position.equals(new Position(j - folge + 1, i, false))) {
                            geht = false;
                            folge = 0;
                        }
                    }

                    if (folge == länge && geht) {
                        positionen.add(new Position(j - folge + 1, i, false));
                        break;
                    }

                    if (i == pg.getFelder().length - 1 && j == pg.getFelder()[i].length - 1) {
                        nochmal = false;
                    }
                }

                if (folge == länge) {
                    folge = 0;
                    break;
                }
                folge = 0;
            }

        }

        Position p = positionen.get((int) (Math.random() * positionen.size()));
        for (int i = 0; i < länge; i++) {
            if (p.getRichtung()) {
                pg.getFelder()[p.getX()][p.getY() + i] = new Schiff(id);
            } else {
                pg.getFelder()[p.getX() + i][p.getY()] = new Schiff(id);
            }
        }
    }

 
    private static boolean schiffMöglich(Position p, Playground pg) {
        boolean[] b = new boolean[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = false;
        }

        if (p.getX() + 1 < pg.getFelder().length) {
            if (!(pg.getFelder()[p.getX() + 1][p.getY()] instanceof Schiff)) {
                b[0] = true;
            }
        } else {
            b[0] = true;
        }

        if (p.getX() - 1 >= 0) {
            if (!(pg.getFelder()[p.getX() - 1][p.getY()] instanceof Schiff)) {
                b[1] = true;
            }
        } else {
            b[1] = true;
        }

        if (p.getY() + 1 < pg.getFelder().length) {
            if (!(pg.getFelder()[p.getX()][p.getY() + 1] instanceof Schiff)) {
                b[2] = true;
            }
        } else {
            b[2] = true;
        }

        if (p.getY() - 1 >= 0) {
            if (!(pg.getFelder()[p.getX()][p.getY() - 1] instanceof Schiff)) {
                b[3] = true;
            }
        } else {
            b[3] = true;
        }

        if (p.getX() + 1 < pg.getFelder().length && p.getY() + 1 < pg.getFelder().length) {
            if (!(pg.getFelder()[p.getX() + 1][p.getY() + 1] instanceof Schiff)) {
                b[4] = true;
            }
        } else {
            b[4] = true;
        }

        if (p.getX() + 1 < pg.getFelder().length && p.getY() - 1 >= 0) {
            if (!(pg.getFelder()[p.getX() + 1][p.getY() - 1] instanceof Schiff)) {
                b[5] = true;
            }
        } else {
            b[5] = true;
        }

        if (p.getX() - 1 >= 0 && p.getY() - 1 >= 0) {
            if (!(pg.getFelder()[p.getX() - 1][p.getY() - 1] instanceof Schiff)) {
                b[6] = true;
            }
        } else {
            b[6] = true;
        }

        if (p.getX() - 1 >= 0 && p.getY() + 1 < pg.getFelder().length) {
            if (!(pg.getFelder()[p.getX() - 1][p.getY() + 1] instanceof Schiff)) {
                b[7] = true;
            }
        } else {
            b[7] = true;
        }

        return b[0] && b[1] && b[2] && b[3] && b[4] && b[5] && b[6] && b[7];
    }


}
