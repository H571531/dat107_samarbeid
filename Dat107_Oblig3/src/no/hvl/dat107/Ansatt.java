package no.hvl.dat107;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ansatt", schema = "Oblig3")
public class Ansatt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int AnsattId; //Lages av JPA
	
	
	private String Brukernavn;
	private String fornavn;
	private String etternavn;
	private LocalDate AnsettelsesDato; 
	private String Stilling;
	private BigDecimal manedslonn; 
	private Avdeling tilhortAvdeling;
	private Prosjekt[] tilhorteProsjekter;
	
	
	
	
	
}
