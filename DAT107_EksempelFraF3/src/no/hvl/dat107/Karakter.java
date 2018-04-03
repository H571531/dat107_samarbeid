package no.hvl.dat107;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name= "Karakter", schema="forelesning3")
public class Karakter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int karNr;
	
	

	private LocalDate eksDato;
	private char karakter;
	
	//Må ha en default constructor som JPA bruker
	public Karakter() {
		
	}
	
	public Karakter(String kurskode, LocalDate eksDato, char karakter, Vitnemal vitnemal) {
		this.kursKode = kurskode;
		this.eksDato = eksDato;
		this.karakter = karakter;
		this.vitnemal = vitnemal;
	}
	//Karakter(kurskode, eksamensDato, kar, studnr, vitnemal);
	
	//I stedet for å referere til foreign key i vitnemål
	@ManyToOne
	@JoinColumn(name = "StudNr", referencedColumnName = "StudNr")
	private Vitnemal vitnemal;
	//mange karakterer på ett vitnemål => many to one i karakter-klassen
	
	private String kursKode;
	@Override
	public String toString() {
		return "Karakter [karNr=" + karNr + ", kursKode=" + kursKode + ", eksDato=" + eksDato + ", karakter=" + karakter
				 + "]";
	}

}
