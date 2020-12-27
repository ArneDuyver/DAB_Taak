package be.kuleuven.csa.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Koopt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "optimized-sequence")
    @GenericGenerator(
            name = "optimized-sequence",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(name="prefer_sequence_per_entity", value="true"),
                    @org.hibernate.annotations.Parameter(name="optimizer", value="hilo"),
                    @org.hibernate.annotations.Parameter(name="increment_size", value="1")})
    private int kooptId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VerkooptId")
    private Verkoopt verkoopt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KlantId")
    private Klant klant;

    public Koopt(Verkoopt verkoopt, Klant klant) {
        this.verkoopt = verkoopt;
        verkoopt.getKooptList().add(this);
        this.klant = klant;
        klant.getKooptList().add(this);
    }
}
