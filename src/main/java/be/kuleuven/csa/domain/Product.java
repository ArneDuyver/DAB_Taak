package be.kuleuven.csa.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "optimized-sequence")
    @GenericGenerator(
            name = "optimized-sequence",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(name="prefer_sequence_per_entity", value="true"),
                    @org.hibernate.annotations.Parameter(name="optimizer", value="hilo"),
                    @org.hibernate.annotations.Parameter(name="increment_size", value="1")})
    private int productId;
    @Column
    private String naam;
    @Column
    private String soort;
    @OneToMany(mappedBy = "product")
    private List<Bevat> bevatList;


    public Product(String naam, String soort) {
        this.naam = naam;
        this.soort = soort;
        this.bevatList = new ArrayList<>();
    }

    public Product() {
    }

    public List<Bevat> getBevatList() {
        return bevatList;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    public int getProductId() {
        return productId;
    }

    public String getNaam() {
        return naam;
    }

    public String getSoort() {
        return soort;
    }
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ",naam='" + naam + '\'' +
                ", soort='" + soort + '\'' +
                '}';
    }
}
