package no.hvl.dat107.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ansatt", schema = "oblig3")
public class Ansatt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ansattId; // Lages av JPA

	private String brukernavn;
	private String fornavn;
	private String etternavn;
	private LocalDate ansettelsesDato;
	private String stilling;
	private BigDecimal maanedsloenn;
	// private Avdeling tilhortAvdeling;
	private List<Prosjektdeltakelse> prosjektDeltakelser;

	public Ansatt() {

	}

	public Ansatt(String Brukernavn, String fornavn, String etternavn, LocalDate AnsettelsesDato, String Stilling,
			BigDecimal manedslonn, Avdeling tilhortAvdeling) {
		this.brukernavn = Brukernavn;
		this.fornavn = fornavn;
		this.etternavn = etternavn;
		this.ansettelsesDato = AnsettelsesDato;
		this.stilling = Stilling;
		this.maanedsloenn = manedslonn;
		// this.tilhortAvdeling=tilhortAvdeling;
	}

	public void leggTilProsjektdeltakelse(Prosjektdeltakelse prosjektdeltakelse) {
		prosjektDeltakelser.add(prosjektdeltakelse);
	}

	public void fjernProsjektdeltagelse(Prosjektdeltakelse prosjektdeltakelse) {
		prosjektDeltakelser.remove(prosjektdeltakelse);
	}

	public int getAnsattId() {
		return ansattId;
	}

	public void setAnsattId(int ansattId) {
		this.ansattId = ansattId;
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

	public LocalDate getAnsettelsesDato() {
		return ansettelsesDato;
	}

	public void setAnsettelsesDato(LocalDate ansettelsesDato) {
		this.ansettelsesDato = ansettelsesDato;
	}

	public String getStilling() {
		return stilling;
	}

	public void setStilling(String stilling) {
		this.stilling = stilling;
	}

	public BigDecimal getManedslonn() {
		return maanedsloenn;
	}

	public void setManedslonn(BigDecimal manedslonn) {
		this.maanedsloenn = manedslonn;
	}

	/*
	 * public Avdeling getTilhortAvdeling() { return tilhortAvdeling; }
	 * 
	 * public void setTilhortAvdeling(Avdeling tilhortAvdeling) {
	 * this.tilhortAvdeling = tilhortAvdeling; }
	 */

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
		
		sb.append(String.format("%3d | %15s | %15s | %15s | %15s | %20s | %15d | %15s | ", ansattId, brukernavn, fornavn, etternavn, ansettelsesDato.toString(), stilling, maanedsloenn.toString(), ansattVed.getNavn()));
		
		if(prosjektDeltakelser.isEmpty()) {
			sb.append("INGEN");
		} else {
			for(Prosjektdeltakelse pd: prosjektDeltakelser) {
				sb.append(pd.getProsjekt().getProsjektid() + "  ");
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
			sb.append("ID: " + pd.getProsjekt().getProsjektid() + " - " + pd.getProsjekt().getProsjektnavn() + " - Rolle: " + pd.getRolle() + " - " + pd.getAntallTimer() + " timer\n");
		}
		return sb.toString();
	}
	
	public int totaleTimer() {
		int antall = 0;
		if(!prosjektDeltakelser.isEmpty()) {
			for(Prosjektdeltakelse pd: prosjektDeltakelser) {
				antall += pd.getAntallTimer();
			}
		}
		return antall;
	}
}
