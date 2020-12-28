package be.kuleuven.csa.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PakketInhoud {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "optimized-sequence")
    @GenericGenerator(
            name = "optimized-sequence",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(name="prefer_sequence_per_entity", value="true"),
                    @org.hibernate.annotations.Parameter(name="optimizer", value="hilo"),
                    @org.hibernate.annotations.Parameter(name="increment_size", value="1")})
    @Id
    private int pakketInhoudId;
    @Column
    private String naam;
    @OneToMany(mappedBy = "pakketInhoud")
    private List<BehoortTot> behoortTotList;
    @OneToMany(mappedBy = "pakketInhoud")
    private List<Bevat> bevatList;


    public PakketInhoud(String naam) {
        this.naam = naam;
        this.behoortTotList = new ArrayList<>();
        this.bevatList = new ArrayList<>();
    }

    public PakketInhoud() {
    }

    public List<Bevat> getBevatList() {
        return bevatList;
    }

    public List<BehoortTot> getBehoortTotList() {
        return behoortTotList;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getPakketInhoudId() {
        return pakketInhoudId;
    }

    @Override
    public String toString() {
        return "PakketInhoud{" +
                "pakketInhoudId=" + pakketInhoudId +
                ", naam='" + naam + '\'' +
                '}';
    }
}
