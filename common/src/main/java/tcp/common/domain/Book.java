package tcp.common.domain;

import java.util.Objects;

public class Book extends BaseEntity<Integer> {
    private String name;
    private String author;
    public String publisher;
    private String yearOfPublication;
    private double price;

    public Book(Integer id, String name, String author, String publisher, String yearOfPublication, double price) {
        super( id );
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.yearOfPublication = yearOfPublication;
        this.price = price;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!( o instanceof Book )) return false;
        Book book = (Book) o;
        return Objects.equals( getName(), book.getName() ) &&
                Objects.equals( getAuthor(), book.getAuthor() ) &&
                Objects.equals( getPublisher(), book.getPublisher() ) &&
                Objects.equals( getYearOfPublication(), book.getYearOfPublication() );
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", yearOfPublication='" + yearOfPublication + '\'' +
                ", price=" + price +
                '}';
    }

}
