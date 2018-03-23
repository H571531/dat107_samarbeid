package no.hvl.dat107;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "", schema = "")

public class Ansatt {
	private int AnsattId; //Bruker JPA for denne
	private String Brukernavn;
	private String fornavn;
	private String etternavn;
	private String AnsettelsesDato; //Ma endre type!!
	private String Stilling;
	private double manedslonn; //OBS! OBS Endre datatype
	private Avdeling tilhortAvdeling;
	private Prosjekt[] tilhorteProsjekter;
	
	
	
	
	
}
