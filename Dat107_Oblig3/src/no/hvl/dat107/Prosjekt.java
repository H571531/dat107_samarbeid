package no.hvl.dat107;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "", schema = "")


public class Prosjekt {
	private int prosjektId; //Bruker JPA for denne
	private String ProsjektNavn;
	private String Beskrivelse;
	private Ansatt[] Deltakere;
	
	
	
}
