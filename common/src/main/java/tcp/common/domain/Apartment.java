package tcp.common.domain;

public class Apartment  extends BaseEntity<Integer> {

    private String address;
    public String category;
    private String description;
    private String yearOfConstruction;
    private double price;


    @Override
    public String toString() {
        return "Apartment{" +
                ", address='" + address + '\'' +
                ", category='" + category + '\'' +
                "  description='" + description + '\'' +
                ", yearOfConstruction='" + yearOfConstruction + '\'' +
                ", price=" + price +
                '}';
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(String yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public Apartment(Integer id, String description, String address, String category, String yearOfConstruction, double price) {
        super(id);
        this.address = address;
        this.category = category;
        this.description = description;
        this.yearOfConstruction = yearOfConstruction;
        this.price = price;
    }


}

