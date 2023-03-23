public class DVD extends Article {
    private final double VAT = 0.19;
    private String name;
    private int duration;
    private COUNTRYCODE countryCode;
    
    public DVD(double articleNumber, double price, String name, int duration, COUNTRYCODE countrycode) {
        super(articleNumber, price);
        this.name = name;
        this.duration = duration;
        this.countryCode = countrycode;
    }

    public double getVAT() {
        return VAT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public COUNTRYCODE getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(COUNTRYCODE countryCode) {
        this.countryCode = countryCode;
    }
    public double getPrice(){
        return super.getPrice() + super.getPrice() * VAT;
    }

    @Override
    public String toString() {
        String Art = "DVD";
        return String.format("%15s %40s %40.2f", Art, name, getPrice());
    }

    


}
