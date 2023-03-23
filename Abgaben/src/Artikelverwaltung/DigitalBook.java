public class DigitalBook extends Book {
    private int size;

    public DigitalBook(double articleNumber, double price, String author, String title, int year, int size) {
        super(articleNumber, price, author, title, year);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        String Art = "Digital Book";
        return String.format("%15s %40s %40s", Art, getTitle(), getPrice());
    }
}
