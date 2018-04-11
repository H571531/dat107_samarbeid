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
@Table(name = "prosjektdeltakelse", schema = "oblig3")
@IdClass(ProsjektdeltakelsePK.class)
public class Prosjektdeltakelse {

	

	@Id
	@ManyToOne
	@JoinColumn(name = "ansattId")
	private Ansatt ansatt;

    @Id
    @ManyToOne
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

		// Hvis vi gj�r dette her slipper vi � gj�re det i EAO
		//ansatt.leggTilProsjektdeltakelse(this);
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

}
