public class Book extends Article {
    private final double VAT = 0.07;
    private String author;
    private String title;
    private int year;
    public Book(double articleNumber, double price, String author, String title, int year) {
        super(articleNumber, price);
        this.author = author;
        this.title = title;
        this.year = year;
    }
    public double getVAT() {
        return VAT;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public double getPrice(){
        return super.getPrice() + super.getPrice() * VAT;
    }
    @Override
    public String toString() {
        String Art = "Buch";
        return String.format("%15s %40s %40.2f", Art, title, getPrice());
    } 

    
}
