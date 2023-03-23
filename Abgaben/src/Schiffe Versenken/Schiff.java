public class Schiff extends Feld {
    int id;

    public Schiff(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        if (super.isGetroffen()) {
            return " X ";
        } else {
            return " - ";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Schiff other = (Schiff) obj;
        if (id != other.id)
            return false;
        return true;
    }

    

}
