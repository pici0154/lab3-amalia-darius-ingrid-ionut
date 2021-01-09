package tcp.common.domain;

import java.util.Objects;

public class Client extends BaseEntity<Integer>{
    private String name;
    private String dateOfRegistration;

    public Client(Integer id, String name, String dateOfRegistration) {
        super(id);
        this.name = name;
        this.dateOfRegistration = dateOfRegistration;
    }

    public Client() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(String dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Objects.equals(getName(), client.getName()) &&
                Objects.equals(getDateOfRegistration(), client.getDateOfRegistration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDateOfRegistration());
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", dateOfRegistration='" + dateOfRegistration + '\'' +
                '}';
    }
}
