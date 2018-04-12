package no.hvl.dat107.entity;

import javax.persistence.CascadeType;
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
@Table(name = "prosjektdeltakelse", schema = "oblig3")
@IdClass(ProsjektdeltakelsePK.class)
public class Prosjektdeltakelse {

	

	@Id
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "ansattId")
	private Ansatt ansatt;

    @Id
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="prosjektId")
    private Prosjekt prosjekt;

    private int timer;
    private String rolle; 

	public Prosjektdeltakelse() {
	}

	public Prosjektdeltakelse(Ansatt ansatt, Prosjekt prosjekt, String rolle,int timer) {
		this.ansatt = ansatt;
		this.prosjekt = prosjekt;
		this.timer = timer;
		this.rolle=rolle;

		//Oppdaterer prosjekt og ansatt i constructor som kalles fra EAO
		ansatt.getProsjektDeltakelser().add(this);
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
		return rolle;
	}

	public void setRolle(String rolle) {
		this.rolle = rolle;
	}

	public void skrivUt(String innrykk) {
		System.out.printf("%sDeltagelse: %s %s, %s, %d timer", innrykk, ansatt.getFornavn(), ansatt.getEtternavn(),
				prosjekt.getProsjektNavn(), timer);
	}
	
	@Override
	public String toString() {
		String ut = "Prosjekt: " + prosjekt.getProsjektNavn() + " - Ansatt: " + ansatt.getAnsattid() + "(" + ansatt.getFornavn() + " " + ansatt.getEtternavn() + ") - " + rolle
				+ " - Antall timer: " + timer;
		
		return ut;
	}

}
