package no.hvl.dat107.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//Lombok-annotations - Disse lager automatisk gettere og settere
//NB! Dere fjerner denne annoteringen og lager gettere osv. selv!

//JPA-annotations
@Entity
@Table(name = "prosjekt", schema = "oblig3")
public class Prosjektdeltakelse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prosjektdeltagelse_Id;

	private int timer;

	@ManyToOne
	@JoinColumn(name = "ansattId")
	private Ansatt ansatt;

	@ManyToOne
	@JoinColumn(name = "prosjektId")
	private Prosjekt prosjekt;

	public Prosjektdeltakelse() {
	}

	public Prosjektdeltakelse(Ansatt ansatt, Prosjekt prosjekt, int timer) {
		this.ansatt = ansatt;
		this.prosjekt = prosjekt;
		this.timer = timer;

		// Hvis vi gj�r dette her slipper vi � gj�re det i EAO
		ansatt.leggTilProsjektdeltagelse(this);
		prosjekt.leggTilProsjektdeltagelse(this);
	}

	public void skrivUt(String innrykk) {
		System.out.printf("%sDeltagelse: %s %s, %s, %d timer", innrykk, ansatt.getFornavn(), ansatt.getEtternavn(),
				prosjekt.getProsjektNavn(), timer);
	}

}
