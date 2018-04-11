package no.hvl.dat107.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ansatt", schema = "oblig3")
public class Ansatt {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ansattid;
    
    private String brukernavn;
    private String fornavn;
    private String etternavn;
    private LocalDate ansettelsesdato;
    private String stilling;
    private BigDecimal maanedsloenn;
    
    @OneToOne(mappedBy = "sjef")
    private Avdeling sjefFor;
    
    @ManyToOne
    @JoinColumn(name = "avdelingsID", referencedColumnName = "avdelingsID")
    private Avdeling ansattVed;
    
    /*
    //JoinTable - må ha med OBLIG3.prosjektdeltakelse når prosjektdeltakelse ikke er en klasse
    @ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name="oblig3.prosjektdeltakelse", joinColumns = @JoinColumn(name="ansattid"), inverseJoinColumns = @JoinColumn(name = "prosjektid"))
    private List<Prosjekt> prosjekter;
    
    */
    
    @OneToMany(mappedBy = "ansatt", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Prosjektdeltakelse> prosjektDeltakelser;
    
    //Må ha default constructor for JPA
    public Ansatt() {
    }
    
	
    /**
     * 
     * @param brukernavn Brukernavn
     * @param fornavn Fornavn
     * @param etternavn Etternavn
     * @param ansettelsesdato Dato for ansettelse, YYYY-MM-DD
     * @param stilling, Stilling
     * @param maanedsloenn Månedslønn
     * @param ansattVed Avdeling den ansatte jobber
     */
	public Ansatt(String brukernavn, String fornavn, String etternavn, LocalDate ansettelsesdato,
			String stilling, BigDecimal maanedsloenn, Avdeling ansattVed) {
		this.brukernavn = brukernavn;
		this.fornavn = fornavn;
		this.etternavn = etternavn;
		this.ansettelsesdato = ansettelsesdato;
		this.stilling = stilling;
		this.maanedsloenn = maanedsloenn;
		this.ansattVed = ansattVed;
	}



	/**
	 * Lager String med alle relevante overskrifter for attributter
	 * @return String som kan brukes som tabelloverskrift for oversikt over ansatte
	 */
	public static String lagTabellOverskrift() {
		return String.format("%3s | %15s | %15s | %15s | %15s | %20s | %15s | %15s | %15s |", "ID", "Brukernavn", "Fornavn", "Etternavn", "Ansatt", "Stilling", "Månedslønn", "Avdeling", "Prosjekter") + "\n";
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String stilling = this.stilling;
		if(sjefFor != null) {
			stilling += "(S: " + sjefFor.getAvdelingsID() + ")";
		}
		
		sb.append(String.format("%3d | %15s | %15s | %15s | %15s | %20s | %15s | %15s | ", ansattid, brukernavn, fornavn, etternavn, ansettelsesdato.toString(), stilling, maanedsloenn.toString(), ansattVed.getNavn()));
		
		if(prosjektDeltakelser.isEmpty()) {
			sb.append("INGEN");
		} else {
			for(Prosjektdeltakelse pd: prosjektDeltakelser) {
				sb.append(pd.getProsjekt().getProsjektId() + "  ");
			}
		}
		
		
		//Hvis ansatt er sjef for en avdeling, legg til info om det
		return sb.toString();
	}
	
	/**
	 * String med tabell-overskrift, alle den ansattes attributter, og alle prosjekter den ansatte jobber på
	 * @return String med fullstendig oversikt over den ansatte
	 */
	public String toStringMedProsjektTimer() {
		StringBuilder sb = new StringBuilder();
		sb.append(lagTabellOverskrift());
		sb.append(this.toString() + "\n\n");
		sb.append("PROSJEKTER\n");
		if(prosjektDeltakelser.size() > 0) {
			sb.append(prosjekterString());
		} else {
			sb.append("\tINGEN");
		}
		
		return sb.toString();
	}
	
	/**
	 * String med alle prosjekter den ansatte jobber på
	 * @return String med alle prosjekt
	 */
	public String prosjekterString() {
		StringBuilder sb = new StringBuilder();
		for(Prosjektdeltakelse pd: prosjektDeltakelser) {
			sb.append("ID: " + pd.getProsjekt().getProsjektId() + " - " + pd.getProsjekt().getProsjektNavn() + " - Rolle: " + pd.getRolle() + " - " + pd.getTimer() + " timer\n");
		}
		return sb.toString();
	}
	
	public int totaleTimer() {
		int antall = 0;
		if(!prosjektDeltakelser.isEmpty()) {
			for(Prosjektdeltakelse pd: prosjektDeltakelser) {
				antall += pd.getTimer();
			}
		}
		return antall;
	}



	public int getAnsattid() {
		return ansattid;
	}

	public void setAnsattid(int ansattid) {
		this.ansattid = ansattid;
	}

	public String getBrukernavn() {
		return brukernavn;
	}

	public void setBrukernavn(String brukernavn) {
		this.brukernavn = brukernavn;
	}

	public String getFornavn() {
		return fornavn;
	}
	public void setFornavn(String fornavn) {
		this.fornavn = fornavn;
	}
	public String getEtternavn() {
		return etternavn;
	}
	public void setEtternavn(String etternavn) {
		this.etternavn = etternavn;
	}
	public LocalDate getAnsettelsesdato() {
		return ansettelsesdato;
	}
	public void setAnsettelsesdato(LocalDate ansettelsesdato) {
		this.ansettelsesdato = ansettelsesdato;
	}
	public String getStilling() {
		return stilling;
	}
	public void setStilling(String stilling) {
		this.stilling = stilling;
	}
	public BigDecimal getMaanedsloenn() {
		return maanedsloenn;
	}
	public void setMaanedsloenn(BigDecimal maanedsloenn) {
		this.maanedsloenn = maanedsloenn;
	}
	public Avdeling getAnsattVed() {
		return ansattVed;
	}
	public void setAnsattVed(Avdeling ansattVed) {
		this.ansattVed = ansattVed;
	}


	public Avdeling getSjefFor() {
		return sjefFor;
	}


	public void setSjefFor(Avdeling sjefFor) {
		this.sjefFor = sjefFor;
		
	}


	public List<Prosjektdeltakelse> getProsjektDeltakelser() {
		return prosjektDeltakelser;
	}


	public void setProsjektDeltakelser(List<Prosjektdeltakelse> prosjektDeltakelser) {
		this.prosjektDeltakelser = prosjektDeltakelser;
	}
	
	

}
