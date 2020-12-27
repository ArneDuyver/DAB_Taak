package be.kuleuven.csa.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
public class Bevat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "optimized-sequence")
    @GenericGenerator(
            name = "optimized-sequence",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(name="prefer_sequence_per_entity", value="true"),
                    @org.hibernate.annotations.Parameter(name="optimizer", value="hilo"),
                    @org.hibernate.annotations.Parameter(name="increment_size", value="1")})
    private int behoortTotId;
    @Column
    private String eenheid;
    @Column
    private int hoeveelheid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PakketInhoudId")
    private PakketInhoud pakketInhoud;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId")
    private Product product;

    public Bevat(String eenheid, int hoeveelheid, PakketInhoud pakketInhoud, Product product) {
        this.eenheid = eenheid;
        this.hoeveelheid = hoeveelheid;
        this.pakketInhoud = pakketInhoud;
        this.product = product;
        pakketInhoud.getBevatList().add(this);
        product.getBevatList().add(this);
    }
}
