package no.hvl.dat107.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


//JPA-annotations 

@Entity
@Table(name = "prosjekt", schema = "oblig3")
@IdClass(ProsjektdeltagelsePK.class)
public class Prosjektdeltakelse {

	private int timer;

	@ManyToOne
	@JoinColumn(name = "ansattId")
	private Ansatt ansatt;

    @Id
    @ManyToOne
    @JoinColumn(name="ProsjektId")
    private Prosjekt prosjekt;

    private String Rolle; 

	public Prosjektdeltakelse() {
	}

	public Prosjektdeltakelse(Ansatt ansatt, Prosjekt prosjekt, int timer,String rolle) {
		this.ansatt = ansatt;
		this.prosjekt = prosjekt;
		this.timer = timer;
		this.Rolle=rolle;

		// Hvis vi gjør dette her slipper vi å gjøre det i EAO
		ansatt.leggTilProsjektdeltakelse(this);
		prosjekt.leggTilProsjektdeltakelse(this);
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public Ansatt getAnsatt() {
		return ansatt;
	}

	public void setAnsatt(Ansatt ansatt) {
		this.ansatt = ansatt;
	}

	public Prosjekt getProsjekt() {
		return prosjekt;
	}

	public void setProsjekt(Prosjekt prosjekt) {
		this.prosjekt = prosjekt;
	}
	

	public String getRolle() {
		return Rolle;
	}

	public void setRolle(String rolle) {
		Rolle = rolle;
	}

	public void skrivUt(String innrykk) {
		System.out.printf("%sDeltagelse: %s %s, %s, %d timer", innrykk, ansatt.getFornavn(), ansatt.getEtternavn(),
				prosjekt.getProsjektNavn(), timer);
	}

}
