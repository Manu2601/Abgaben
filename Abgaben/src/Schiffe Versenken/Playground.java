public class Playground {
    private Feld[][] felder = new Feld[10][10];
    private int wasserGetroffen;
    private int schiffeGetroffen;
    private int schiffeInsgesamt = 10;

    public Playground() {

    }

    public Feld[][] getFelder() {
        return felder;
    }

    public int getWasserBeschossen() {
        return wasserGetroffen;
    }

    public int getSchiffBeschossen() {
        return schiffeGetroffen;
    }

    public void schuss(Position p) {
        getAnzahlSchiffe();
        felder[p.getX()][p.getY()].setGetroffen(true);

        if (felder[p.getX()][p.getY()] instanceof Schiff) {
            schiffeGetroffen++;
        } else {
            wasserGetroffen++;
        }
    }


    public int getAnzahlSchiffe() {
        int anzahl = 0;
        for (int i = 0; i < schiffeInsgesamt; i++) {
            for (int j = 0; j < felder.length; j++) {
                boolean b = false;
                for (int j2 = 0; j2 < felder[i].length; j2++) {
                    if (felder[j][j2] instanceof Schiff) {
                        Schiff s = (Schiff) felder[j][j2];
                        if (s.getId() == i && !(s.isGetroffen())) {
                            b = true;
                            anzahl++;
                        }
                    }
                    if (b) {
                        break;
                    }
                }
                if (b) {
                    break;
                }
            }
        }
        return anzahl;
    }


    public boolean fertig() {
        for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
                if (felder[i][j] instanceof Schiff && !felder[i][j].isGetroffen()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        System.out.println("\n   0   1   2   3   4   5   6   7   8   9");
        String str = "";
        for (int i = 0; i < felder.length; i++) {
            str += i + " ";
            for (int j = 0; j < felder[i].length; j++) {
                str += felder[i][j].toString() + " ";
            }
            str += "\n";
        }
        return str;
    }
}
