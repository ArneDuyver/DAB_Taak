package be.kuleuven.csa.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class HaaltAf {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "optimized-sequence")
    @GenericGenerator(
            name = "optimized-sequence",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(name="prefer_sequence_per_entity", value="true"),
                    @org.hibernate.annotations.Parameter(name="optimizer", value="hilo"),
                    @org.hibernate.annotations.Parameter(name="increment_size", value="1")})
    @Id
    private int haaltAfId;
    @Column
    private boolean afgehaald;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BehoortTotId")
    private BehoortTot behoortTot;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KlantId")
    private Klant klant;

    public HaaltAf(BehoortTot behoortTot, Klant klant) {
        this.behoortTot = behoortTot;
        behoortTot.getHaaltAfList().add(this);
        this.klant = klant;
        klant.getHaaltAfList().add(this);
        this.afgehaald = false;
    }

    public HaaltAf() {
    }

    public void setAfgehaald(boolean afgehaald) {
        this.afgehaald = afgehaald;
    }

    public void setBehoortTot(BehoortTot behoortTot) {
        this.behoortTot = behoortTot;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    public boolean isAfgehaald() {
        return afgehaald;
    }

    public BehoortTot getBehoortTot() {
        return behoortTot;
    }

    public Klant getKlant() {
        return klant;
    }

    public int getHaaltAfId() {
        return haaltAfId;
    }


    public void setHaaltAfId(int haaltAfId) {
        this.haaltAfId = haaltAfId;
    }

    @Override
    public String toString() {
        return      "haaltAfId=" + haaltAfId +
                ", afgehaald=" + afgehaald +
                ", behoortTot=(" + behoortTot +
                "), klant=(" + klant +")"
                ;
    }
}
