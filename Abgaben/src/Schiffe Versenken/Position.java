public class Position {
    int x;
    int y;
    boolean richtung;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y, boolean richtung) {
        this.x = x;
        this.y = y;
        this.richtung = richtung;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getRichtung() {
        return richtung;
    }

    @Override
    public boolean equals(Object obj) {
        Position p = (Position) obj;
        return p.getX() == this.getX() && p.getY() == this.getY() && p.getRichtung() == this.getRichtung();
    }

}
