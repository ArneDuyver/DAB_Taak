package be.kuleuven.csa.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BehoortTot {
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
    private int weekNummer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VerkooptId")
    private Verkoopt verkoopt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PakketInhoudId")
    private PakketInhoud pakketInhoud;
    @OneToMany(mappedBy = "behoortTot")
    private List<HaaltAf> haaltAfList;

    public BehoortTot(int weekNummer, Verkoopt verkoopt, PakketInhoud pakketInhoud) {
        this.weekNummer = weekNummer;
        this.verkoopt = verkoopt;
        verkoopt.getBehoortTotList().add(this);
        this.pakketInhoud = pakketInhoud;
        pakketInhoud.getBehoortTotList().add(this);
        this.haaltAfList= new ArrayList<>();
    }

    public BehoortTot() {
    }

    public List<HaaltAf> getHaaltAfList() {
        return haaltAfList;
    }

    public int getBehoortTotId() {
        return behoortTotId;
    }

    public void setBehoortTotId(int behoortTotId) {
        this.behoortTotId = behoortTotId;
    }

    public int getWeekNummer() {
        return weekNummer;
    }

    public void setWeekNummer(int weekNummer) {
        this.weekNummer = weekNummer;
    }

    public Verkoopt getVerkoopt() {
        return verkoopt;
    }

    public void setVerkoopt(Verkoopt verkoopt) {
        this.verkoopt = verkoopt;
    }

    public PakketInhoud getPakketInhoud() {
        return pakketInhoud;
    }

    public void setPakketInhoud(PakketInhoud pakketInhoud) {
        this.pakketInhoud = pakketInhoud;
    }

    public void setHaaltAfList(List<HaaltAf> haaltAfList) {
        this.haaltAfList = haaltAfList;
    }

    @Override
    public String toString() {
        return   "behoortTotId=" + behoortTotId +
                ", weekNummer=" + weekNummer;
    }
}
