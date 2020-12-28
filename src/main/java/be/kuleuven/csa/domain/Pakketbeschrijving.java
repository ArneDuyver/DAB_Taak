package be.kuleuven.csa.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pakketbeschrijving {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "optimized-sequence")
    @GenericGenerator(
            name = "optimized-sequence",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(name="prefer_sequence_per_entity", value="true"),
                    @org.hibernate.annotations.Parameter(name="optimizer", value="hilo"),
                    @org.hibernate.annotations.Parameter(name="increment_size", value="1")})
    private int pakketbeschrijvingId;

    @Column
    private String naam;
    @Column
    private int volwassenen;
    @Column
    private int kinderen;
    @OneToMany(mappedBy = "boerderij")
    private List<Verkoopt> verkooptList;


    public Pakketbeschrijving(String naam, int volwassenen, int kinderen) {
        this.naam = naam;
        this.volwassenen = volwassenen;
        this.kinderen = kinderen;
        this.verkooptList = new ArrayList<Verkoopt>();
    }

    public Pakketbeschrijving() {
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setVolwassenen(int volwassenen) {
        this.volwassenen = volwassenen;
    }

    public void setKinderen(int kinderen) {
        this.kinderen = kinderen;
    }

    public int getPakketbeschrijvingId() {
        return pakketbeschrijvingId;
    }

    public String getNaam() {
        return naam;
    }

    public int getVolwassenen() {
        return volwassenen;
    }

    public int getKinderen() {
        return kinderen;
    }

    public List<Verkoopt> getVerkooptList() {
        return verkooptList;
    }

    @Override
    public String toString() {
        return "Pakketbeschrijving{" +
                "pakketBeschrijvingId=" + pakketbeschrijvingId +
                ",naam='" + naam + '\'' +
                ", volwassenen=" + volwassenen +
                ", kinderen=" + kinderen +
                '}';
    }
}
