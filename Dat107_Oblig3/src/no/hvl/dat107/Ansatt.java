package no.hvl.dat107;

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
	private int AnsattId; // Lages av JPA

	private String Brukernavn;
	private String fornavn;
	private String etternavn;
	private LocalDate AnsettelsesDato;
	private String Stilling;
	private BigDecimal manedslonn;
	// private Avdeling tilhortAvdeling;
	private List<prosjektdeltagelse> deltagelser;

	public Ansatt() {

	}

	public Ansatt(String Brukernavn, String fornavn, String etternavn, LocalDate AnsettelsesDato, String Stilling,
			BigDecimal manedslonn, Avdeling tilhortAvdeling) {
		this.Brukernavn = Brukernavn;
		this.fornavn = fornavn;
		this.etternavn = etternavn;
		this.AnsettelsesDato = AnsettelsesDato;
		this.Stilling = Stilling;
		this.manedslonn = manedslonn;
		// this.tilhortAvdeling=tilhortAvdeling;
	}

	public void leggTilProsjektdeltagelse(prosjektdeltagelse prosjektdeltagelse) {
		deltagelser.add(prosjektdeltagelse);
	}

	public void fjernProsjektdeltagelse(prosjektdeltagelse prosjektdeltagelse) {
		deltagelser.remove(prosjektdeltagelse);
	}

	public int getAnsattId() {
		return AnsattId;
	}

	public void setAnsattId(int ansattId) {
		AnsattId = ansattId;
	}

	public String getBrukernavn() {
		return Brukernavn;
	}

	public void setBrukernavn(String brukernavn) {
		Brukernavn = brukernavn;
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
		return AnsettelsesDato;
	}

	public void setAnsettelsesDato(LocalDate ansettelsesDato) {
		AnsettelsesDato = ansettelsesDato;
	}

	public String getStilling() {
		return Stilling;
	}

	public void setStilling(String stilling) {
		Stilling = stilling;
	}

	public BigDecimal getManedslonn() {
		return manedslonn;
	}

	public void setManedslonn(BigDecimal manedslonn) {
		this.manedslonn = manedslonn;
	}

	/*
	 * public Avdeling getTilhortAvdeling() { return tilhortAvdeling; }
	 * 
	 * public void setTilhortAvdeling(Avdeling tilhortAvdeling) {
	 * this.tilhortAvdeling = tilhortAvdeling; }
	 */

	@Override
	public String toString() {
		return "Ansatt [AnsattId= " + AnsattId + ", Brukernavn= " + Brukernavn + ", fornavn= " + fornavn
				+ ", etternavn=" + etternavn + ", AnsettelsesDato= " + AnsettelsesDato + ", Stilling= " + Stilling
				+ ", manedslonn= " + manedslonn + "]";
	}

	public void skrivUt() {
		System.out.println(toString());
	}

}
