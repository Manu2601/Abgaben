public class Article {
    public static Object getPrice;
    private double articleNumber;
    private double price;

    public Article(double articleNumber, double price) {
        this.articleNumber = articleNumber;
        this.price = price;
    }

    public double getArticleNumber() {
        return articleNumber;
    }
    public void setArticleNumber(double articleNumber) {
        this.articleNumber = articleNumber;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Article [articleNumber=" + articleNumber + ", price=" + price + "]";
    }
    
    public boolean equals(Object obj){
        Article artikel = (Article)obj;
        return this.articleNumber==artikel.articleNumber;
    }
}
